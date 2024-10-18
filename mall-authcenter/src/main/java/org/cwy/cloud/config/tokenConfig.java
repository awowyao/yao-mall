package org.cwy.cloud.config;

import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.cwy.cloud.model.mallUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.token.*;

import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

@Configuration
public class tokenConfig {
//    @Autowired
//    private OAuth2TokenCustomizer<JwtEncodingContext> jwtCustomizer;

    @Bean
    public OAuth2TokenCustomizer<JwtEncodingContext> jwtTokenCustomizer() {
        return context -> {
            if (OAuth2TokenType.ACCESS_TOKEN.equals(context.getTokenType()) && context.getPrincipal() instanceof UsernamePasswordAuthenticationToken) {
                // Customize headers/claims for access_token
                Optional.ofNullable(context.getPrincipal().getPrincipal()).ifPresent(principal -> {
                    JwtClaimsSet.Builder claims = context.getClaims();
                    if (principal instanceof mallUserDetails userDetails) { // 系统用户添加自定义字段

//                        claims.claim(JwtClaimConstants.USER_ID, userDetails.getUserId());
                        claims.claim("name", userDetails.getUsername());
                        claims.claim("id", userDetails.getId());
//                        claims.claim(JwtClaimConstants.DEPT_ID, userDetails.getDeptId());
//                        claims.claim(JwtClaimConstants.DATA_SCOPE, userDetails.getDataScope());

                        // 这里存入角色至JWT，解析JWT的角色用于鉴权的位置: ResourceServerConfig#jwtAuthenticationConverter
                        var authorities = AuthorityUtils.authorityListToSet(context.getPrincipal().getAuthorities())
                                .stream()
                                .collect(Collectors.collectingAndThen(Collectors.toSet(), Collections::unmodifiableSet));
                        claims.claim("authorities", authorities);

                    }
                });
            }
        };
    }

    @Bean
    OAuth2TokenGenerator<?> tokenGenerator(JWKSource<SecurityContext> jwkSource) {
        JwtGenerator jwtGenerator = new JwtGenerator(new NimbusJwtEncoder(jwkSource));
        jwtGenerator.setJwtCustomizer(jwtTokenCustomizer());

        OAuth2AccessTokenGenerator accessTokenGenerator = new OAuth2AccessTokenGenerator();
        OAuth2RefreshTokenGenerator refreshTokenGenerator = new OAuth2RefreshTokenGenerator();
        return new DelegatingOAuth2TokenGenerator(
                jwtGenerator, accessTokenGenerator, refreshTokenGenerator);
    }
}
