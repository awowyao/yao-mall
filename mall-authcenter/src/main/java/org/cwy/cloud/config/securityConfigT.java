//package org.cwy.cloud.config;
//
//import jakarta.annotation.Resource;
//import org.cwy.cloud.handler.LoginFailureHandler;
//import org.cwy.cloud.handler.RestAuthenticationEntryPoint;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//import java.io.IOException;
///*
//    securityJWT认证
//
//*/
//@EnableWebSecurity
//@Configuration
//public class securityConfigT {
//    @Autowired
//    private JwtAythenticationTokenFilter jwtAythenticationTokenFilt;
//    @Resource
//    private LoginFailureHandler accessDeniedHandler;
//    @Resource
//    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;
//    @Bean
//    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
////        http.formLogin((formLogin)->
////                formLogin.loginProcessingUrl("/login")
////                        .successHandler(new LoginSuccessHandler())
////                        .failureHandler(new LoginFailureHandler())
////        );
////        http.authorizeHttpRequests((auth)-> auth.requestMatchers("/user/login").permitAll()
////                .anyRequest().authenticated());
//        http.authorizeHttpRequests((auth)-> auth.requestMatchers("/oauth/sign").permitAll().requestMatchers("/oauth/user/login").permitAll()
//                .anyRequest().authenticated());
//        http.addFilterBefore(jwtAythenticationTokenFilt, UsernamePasswordAuthenticationFilter.class);
//        http.exceptionHandling((ex) -> ex.authenticationEntryPoint(restAuthenticationEntryPoint));
//        http.exceptionHandling((ex)->
//                ex.accessDeniedHandler(accessDeniedHandler));
//        http.csrf(CSRF->CSRF.disable());
//
//        return http.build();
//    }
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
//        return configuration.getAuthenticationManager();
//    }
//
//    @Bean
//    PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
////    @Bean
////    public AuthenticationProvider authProvider() {
////    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
////    authProvider.setUserDetailsService(new accountUserDetailsService());
////    return authProvider;
////    }
//}
