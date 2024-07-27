//package org.cwy.cloud.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
//import org.springframework.security.config.web.server.ServerHttpSecurity;
//import org.springframework.security.web.server.SecurityWebFilterChain;
//
///**
// * @description 安全配置类
// * @author 早起的年轻人
// */
//@EnableWebFluxSecurity
//@Configuration
//public class SecurityConfig {
//    //安全拦截配置
//    @Bean
//    public SecurityWebFilterChain webFluxSecurityFilterChain(ServerHttpSecurity http)  {
//        http.authorizeExchange((auth)-> auth.pathMatchers("/**").permitAll().anyExchange()
//                .authenticated());
//        http.csrf((ex)->ex.disable());
//        return http.build();
//    }
//}
