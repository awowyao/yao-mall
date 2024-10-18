//package org.cwy.cloud.config;
//
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.cwy.cloud.service.accountUserDetailsService;
//import org.cwy.cloud.util.JWTUtil;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//import java.util.Enumeration;
//
///*
//    securityJWT过滤器
//
//*/
//@Component
//public class JwtAythenticationTokenFilter extends OncePerRequestFilter  {
//    @Autowired
//    private accountUserDetailsService accountuserDetailsService;
//    @Autowired
//    private JWTUtil jwtUtil;
//    @Value("${jwt.tokenHeader}")
//    private String tokenHeader;
//    @Value("${jwt.tokenHead}")
//    private String tokenHead;
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
////        String authHeader = request;
//        System.out.println(123);
//        Enumeration<String> enumeration = request.getHeaderNames();
//        while (enumeration.hasMoreElements()) {
//            String name	= enumeration.nextElement();
//            String value = request.getHeader(name);
//            System.out.println(name);
//            System.out.println(value);
//        }
////        filterChain.doFilter(request,response);
////        System.out.println(authHeader);
////        if(authHeader !=null && authHeader.startsWith(this.tokenHead)){
////            String token = authHeader.substring(this.tokenHead.length());
////            String userName = jwtUtil.getUsernameFromToken(token);
////            UserDetails userDetails = this.accountuserDetailsService.loadUserByUsername(userName);
////            UsernamePasswordAuthenticationToken authe = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
////            authe.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
////            SecurityContextHolder.getContext().setAuthentication(authe);
////        }
////        filterChain.doFilter(request,response);
//    }
//}
