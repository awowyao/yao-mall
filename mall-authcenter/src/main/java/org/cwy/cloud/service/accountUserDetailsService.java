package org.cwy.cloud.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.cwy.cloud.mapper.userMapper;
import org.cwy.cloud.model.UserPO;
import org.cwy.cloud.model.mallUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class accountUserDetailsService implements UserDetailsService {
    @Autowired
    private userMapper userService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        System.out.println(123123123);
//        System.out.println(username);
        LambdaQueryWrapper<UserPO> userWrapper = new LambdaQueryWrapper<>();
        userWrapper.eq(UserPO::getName,username);
        UserPO user = userService.selectOne(userWrapper);
        return new mallUserDetails(user, AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
    }

}
