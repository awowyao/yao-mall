//package org.cwy.cloud.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.oauth2.provider.token.TokenStore;
//import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
//import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
//
//
//@Configuration
//public class TokenConfig {
//    String SIGNING_KEY = "test_key";
//
//    @Bean
//    public TokenStore tokenStore() {
//        return new JwtTokenStore(new JwtAccessTokenConverter());
//    }
//
////    @Bean
////    public JwtAccessTokenConverter accessTokenConverter() {
////        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
////        converter.setSigningKey(SIGNING_KEY);
////        return converter;
////    }
//
//}
//