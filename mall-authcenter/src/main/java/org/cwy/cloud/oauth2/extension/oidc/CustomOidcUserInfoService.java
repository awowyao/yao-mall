package org.cwy.cloud.oauth2.extension.oidc;

//import com.youlai.system.api.UserFeignClient;
//import com.youlai.system.dto.UserAuthInfo;
import lombok.extern.slf4j.Slf4j;
import org.cwy.cloud.model.UserM;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 自定义 OIDC 用户信息服务
 *
 * @author Ray Hao
 * @since 3.1.0
 */
@Service
@Slf4j
public class CustomOidcUserInfoService {

//    private final UserFeignClient userFeignClient;
//
//    public CustomOidcUserInfoService(UserFeignClient userFeignClient) {
//        this.userFeignClient = userFeignClient;
//    }

    public CustomOidcUserInfo loadUserByUsername(String username) {
        UserM userAuthInfo = null;
        userAuthInfo.setName("test");
        userAuthInfo.setId(2);
        try {
            if (userAuthInfo == null) {
                return null;
            }
            return new CustomOidcUserInfo(createUser(userAuthInfo));
        } catch (Exception e) {
            log.error("获取用户信息失败", e);
            return null;
        }
    }

    private Map<String, Object> createUser(UserM userAuthInfo) {
        return CustomOidcUserInfo.customBuilder()
                .username(userAuthInfo.getName())
                .nickname("123")
                .status(1)
                .build()
                .getClaims();
    }

}
