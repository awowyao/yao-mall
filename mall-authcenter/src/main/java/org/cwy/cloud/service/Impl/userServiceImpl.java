package org.cwy.cloud.service.Impl;

import jakarta.annotation.Resource;
import org.cwy.cloud.feignClient.uniqidFeign;
import org.cwy.cloud.mapper.StoreUserMapper;
import org.cwy.cloud.mapper.userMapper;
import org.cwy.cloud.model.StoreUserVo;
import org.cwy.cloud.model.UserPO;
import org.cwy.cloud.model.storeUserDTO;
import org.cwy.cloud.model.userSignDTO;
import org.cwy.cloud.result.MyAssert;
import org.cwy.cloud.service.userService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author yao
 * @version 1.0
 * @description: TODO
 * @date 2024/9/24 14:51
 */
@Service
public class userServiceImpl implements userService {
    @Resource
    private userMapper userMapper;
    @Resource
    private StoreUserMapper storeUserMapper;
    @Resource
    private uniqidFeign uniqidFeign;
    @Override
    public boolean userSign(userSignDTO userData) {
        UserPO userPO = new UserPO();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        final String passHash = encoder.encode(userData.getPassword());
        userPO.setId(uniqidFeign.GetId(1));
        userPO.setName(userData.getUserName());
        userPO.setNickName(userData.getNickName());
        userPO.setPassword(passHash);
        userPO.setEmail(userData.getEmail());
        userPO.setPhone(userData.getPhone());
        MyAssert.isZero(userMapper.insert(userPO));

        return true;
    }

    @Override
    public boolean storeUserSign(storeUserDTO userData) {
        StoreUserVo user = new StoreUserVo();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        final String passHash = encoder.encode(userData.getPassword());
        user.setId((long) uniqidFeign.GetId(1));
        user.setUserId(userData.getUserId());
        user.setUserName(userData.getUserName());
        user.setPassword(passHash);
        user.setStoreLv(1);
        user.setStoreType(0);
        MyAssert.isZero(storeUserMapper.insert(user));
        return true;
    }
}
