package org.cwy.cloud.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.Map;

/**
 * @author yao
 * @version 1.0
 * @description: TODO
 * @date 2024/8/23 15:56
 */
public class AuthTokenUtil {
        public static Map<String, Object> getTokenAttributes() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof JwtAuthenticationToken jwtAuthenticationToken) {
            return jwtAuthenticationToken.getTokenAttributes();
        }
        return null;
    }
    public static Integer getUserId() {
        Map<String, Object> tokenAttributes = getTokenAttributes();
        if (tokenAttributes!=null) {
            return Integer.valueOf(tokenAttributes.get("id").toString());
        }
        return 0;
    }


}
