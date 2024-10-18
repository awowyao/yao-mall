package org.cwy.cloud.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import jakarta.annotation.Resource;
import org.cwy.cloud.mapper.StoreUserMapper;
import org.cwy.cloud.model.StoreUserDetails;
import org.cwy.cloud.model.StoreUserVo;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 * @author yao
 * @version 1.0
 * @description: TODO
 * @date 2024/9/23 13:57
 */
@Service
public class storeUserDetailsService {
    @Resource
    StoreUserMapper storeUserMapper;
    public UserDetails loadUserByStoreUsername(String username, String userPassword){
        LambdaQueryWrapper<StoreUserVo> lambdaQueryWrapper = new LambdaQueryWrapper<StoreUserVo>();
        lambdaQueryWrapper.eq(StoreUserVo::getUserId, username);
        lambdaQueryWrapper.eq(StoreUserVo::getPassword, userPassword);
        StoreUserVo storeUserVo = storeUserMapper.selectOne(lambdaQueryWrapper);
        if (storeUserVo!=null) {
            return new StoreUserDetails(storeUserVo, AuthorityUtils.commaSeparatedStringToAuthorityList("store"));
        }else {
            throw new DisabledException("该账户已被禁用!");
        }

    }
}
