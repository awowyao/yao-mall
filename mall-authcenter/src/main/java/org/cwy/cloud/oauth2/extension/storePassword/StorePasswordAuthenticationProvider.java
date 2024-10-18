package org.cwy.cloud.oauth2.extension.storePassword;

import cn.hutool.core.lang.Assert;
import jakarta.annotation.Resource;

import org.cwy.cloud.service.storeUserDetailsService;
import org.cwy.cloud.util.OAuth2AuthenticationProviderUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.*;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.core.oidc.endpoint.OidcParameterNames;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AccessTokenAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2ClientAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.context.AuthorizationServerContextHolder;
import org.springframework.security.oauth2.server.authorization.token.DefaultOAuth2TokenContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;
import org.springframework.util.CollectionUtils;

import java.security.Principal;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author yao
 * @version 1.0
 * @description: TODO
 * @date 2024/9/23 13:52
 */
public class StorePasswordAuthenticationProvider implements AuthenticationProvider {
    @Resource
    storeUserDetailsService storeUserDetailsService;
    private static final String ERROR_URI = "https://datatracker.ietf.org/doc/html/rfc6749#section-5.2";
    private static final OAuth2TokenType ID_TOKEN_TOKEN_TYPE = new OAuth2TokenType(OidcParameterNames.ID_TOKEN);
    private final AuthenticationManager authenticationManager;
    private final OAuth2AuthorizationService authorizationService;
    private final OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator;

    public StorePasswordAuthenticationProvider(AuthenticationManager authenticationManager, OAuth2AuthorizationService authorizationService, OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator) {
        Assert.notNull(authorizationService, "authorizationService cannot be null");
        this.authenticationManager = authenticationManager;
        this.authorizationService = authorizationService;
        this.tokenGenerator = tokenGenerator;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        StorePasswordAuthenticationToken passwordAuthenticationToken = (StorePasswordAuthenticationToken) authentication;
        OAuth2ClientAuthenticationToken oAuth2ClientAuthenticationToken = OAuth2AuthenticationProviderUtils.getAuthenticatedClientElseThrowInvalidClient(passwordAuthenticationToken);
        RegisteredClient registeredClient = oAuth2ClientAuthenticationToken.getRegisteredClient();
        if (!registeredClient.getAuthorizationGrantTypes().contains(StorePasswordAuthenticationToken.STORE_PASSWORD)) {
            throw new OAuth2AuthenticationException(OAuth2ErrorCodes.UNAUTHORIZED_CLIENT);
        }
        Map<String, Object> additionalParameters = passwordAuthenticationToken.getAdditionalParameters();


        String username = (String) additionalParameters.get(OAuth2ParameterNames.USERNAME);
        String password = (String) additionalParameters.get(OAuth2ParameterNames.PASSWORD);
        UserDetails userDetails = storeUserDetailsService.loadUserByStoreUsername(username, password);
        Authentication usernamePasswordAuthentication = new UsernamePasswordAuthenticationToken(userDetails ,null);

        // 验证申请访问范围(Scope)
        Set<String> authorizedScopes = registeredClient.getScopes();
        Set<String> requestedScopes = passwordAuthenticationToken.getScopes();
        if (!CollectionUtils.isEmpty(requestedScopes)) {
            Set<String> unauthorizedScopes = requestedScopes.stream()
                    .filter(requestedScope -> !registeredClient.getScopes().contains(requestedScope))
                    .collect(Collectors.toSet());
            if (!CollectionUtils.isEmpty(unauthorizedScopes)) {
                throw new OAuth2AuthenticationException(OAuth2ErrorCodes.INVALID_SCOPE);
            }
            authorizedScopes = new LinkedHashSet<>(requestedScopes);
        }

        // 访问令牌(Access Token) 构造器
        DefaultOAuth2TokenContext.Builder tokenContextBuilder = DefaultOAuth2TokenContext.builder()
                .registeredClient(registeredClient)
                .principal(usernamePasswordAuthentication) // 身份验证成功的认证信息(用户名、权限等信息)
                .authorizationServerContext(AuthorizationServerContextHolder.getContext())
                .authorizedScopes(authorizedScopes)
                .authorizationGrantType(StorePasswordAuthenticationToken.STORE_PASSWORD) // 授权方式
                .authorizationGrant(passwordAuthenticationToken) // 授权具体对象
                ;

        // 生成访问令牌(Access Token)
        OAuth2TokenContext tokenContext = tokenContextBuilder.tokenType((OAuth2TokenType.ACCESS_TOKEN)).build();

        OAuth2Token generatedAccessToken = tokenGenerator.generate(tokenContext);
        System.out.println(generatedAccessToken.getTokenValue());
//                this.tokenGenerator.generate(tokenContext);
        if (generatedAccessToken == null) {
            OAuth2Error error = new OAuth2Error(OAuth2ErrorCodes.SERVER_ERROR,
                    "The token generator failed to generate the access token.", ERROR_URI);
            throw new OAuth2AuthenticationException(error);
        }


        OAuth2AccessToken accessToken = new OAuth2AccessToken(OAuth2AccessToken.TokenType.BEARER,
                generatedAccessToken.getTokenValue(), generatedAccessToken.getIssuedAt(),
                generatedAccessToken.getExpiresAt(), tokenContext.getAuthorizedScopes());

        OAuth2Authorization.Builder authorizationBuilder = OAuth2Authorization.withRegisteredClient(registeredClient)
                .principalName(usernamePasswordAuthentication.getName())
                .authorizationGrantType(StorePasswordAuthenticationToken.STORE_PASSWORD)
                .authorizedScopes(authorizedScopes)
                .attribute(Principal.class.getName(), usernamePasswordAuthentication); // attribute 字段
        if (generatedAccessToken instanceof ClaimAccessor) {
            authorizationBuilder.token(accessToken, (metadata) ->
                    metadata.put(OAuth2Authorization.Token.CLAIMS_METADATA_NAME, ((ClaimAccessor) generatedAccessToken).getClaims()));
        } else {
            authorizationBuilder.accessToken(accessToken);
        }

        // 生成刷新令牌(Refresh Token)
        OAuth2RefreshToken refreshToken = null;
        if (registeredClient.getAuthorizationGrantTypes().contains(AuthorizationGrantType.REFRESH_TOKEN) &&
                // Do not issue refresh token to public client
                !oAuth2ClientAuthenticationToken.getClientAuthenticationMethod().equals(ClientAuthenticationMethod.NONE)) {

            tokenContext = tokenContextBuilder.tokenType(OAuth2TokenType.REFRESH_TOKEN).build();
            OAuth2Token generatedRefreshToken = tokenGenerator.generate(tokenContext);
            if (!(generatedRefreshToken instanceof OAuth2RefreshToken)) {
                OAuth2Error error = new OAuth2Error(OAuth2ErrorCodes.SERVER_ERROR,
                        "The token generator failed to generate the refresh token.", ERROR_URI);
                throw new OAuth2AuthenticationException(error);
            }

            refreshToken = (OAuth2RefreshToken) generatedRefreshToken;
            authorizationBuilder.refreshToken(refreshToken);
        }


        return new OAuth2AccessTokenAuthenticationToken(registeredClient, oAuth2ClientAuthenticationToken, accessToken, refreshToken, additionalParameters);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return StorePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
