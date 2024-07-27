package org.cwy.cloud.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.cwy.cloud.common.api.CommonResult;
import org.cwy.cloud.model.UserAuto;
import org.cwy.cloud.model.userMsg;
import org.cwy.cloud.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/oauth")
public class testController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JWTUtil jwtUtil;
    @GetMapping("/getUserAuto")
    public CommonResult<UserAuto> getUserAuto() {
//        Principal principal = request.getUserPrincipal();
        System.out.println(123);
        UserAuto user = new UserAuto();
        userMsg UserMsg = new userMsg();
//        UserMsg.setUserName(authentication.getName());
//        user.setAuthorities((Map<Objects, Objects>) authentication.getAuthorities());
        user.setUserMsg(UserMsg);
        return CommonResult.success(user);
    }

    @PostMapping("/user/login")
    public String login(@RequestBody Map<String,String> data) {
        System.out.println(123123);
        String userName = data.get("username");
        String password = data.get("password");
        System.out.println(userName);
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userName, password);
        Authentication authentication = authenticationManager.authenticate(auth);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        System.out.println(userDetails.getUsername());
        String jwtToken = jwtUtil.createToken(userDetails);
        System.out.println(jwtToken);
        return jwtToken;
    }

    @GetMapping("/sign")
    public String sign(String password) {
        String pass = "admin";

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        final String passHash = encoder.encode(pass);
        System.out.println(passHash);

        final boolean matches = encoder.matches(pass, passHash);
        System.out.println(matches);
        return passHash;
    }
}
