package org.cwy.cloud.oauth2.extension.storePassword;

import jakarta.annotation.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AuthorizationGrantAuthenticationToken;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author yao
 * @version 1.0
 * @description: TODO
 * @date 2024/9/23 14:41
 */
public class StorePasswordAuthenticationToken  extends OAuth2AuthorizationGrantAuthenticationToken {
    public static final AuthorizationGrantType STORE_PASSWORD = new AuthorizationGrantType("store_password");

    /**
     * 令牌申请访问范围
     */
    private final Set<String> scopes;

    /**
     * 密码模式身份验证令牌
     *
     * @param clientPrincipal      客户端信息
     * @param scopes               令牌申请访问范围
     * @param additionalParameters 自定义额外参数(用户名和密码)
     */
    public StorePasswordAuthenticationToken(
            Authentication clientPrincipal,
            Set<String> scopes,
            @Nullable Map<String, Object> additionalParameters
    ) {
        super(STORE_PASSWORD, clientPrincipal, additionalParameters);
        this.scopes = Collections.unmodifiableSet(scopes != null ? new HashSet<>(scopes) : Collections.emptySet());

    }

    /**
     * 用户凭证(密码)
     */
    @Override
    public Object getCredentials() {
        return this.getAdditionalParameters().get("password");
    }

    public Set<String> getScopes() {
        return scopes;
    }
}
