//package org.cwy.cloud.config;
//
//
//import com.nimbusds.jose.jwk.JWKSet;
//import com.nimbusds.jose.jwk.RSAKey;
//import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
//import com.nimbusds.jose.jwk.source.JWKSource;
//import com.nimbusds.jose.proc.SecurityContext;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.annotation.Order;
//import org.springframework.http.MediaType;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.oauth2.core.AuthorizationGrantType;
//import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
//import org.springframework.security.oauth2.core.oidc.OidcScopes;
//import org.springframework.security.oauth2.jwt.JwtDecoder;
//import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationConsentService;
//import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationService;
//import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
//import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
//import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
//import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
//import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
//import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
//import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
//import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
//import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
//import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
//import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;
//
//import java.security.KeyPair;
//import java.security.KeyPairGenerator;
//import java.security.interfaces.RSAPrivateKey;
//import java.security.interfaces.RSAPublicKey;
//import java.util.UUID;
//
//
///**
// * https://docs.spring.io/spring-authorization-server/docs/current/reference/html/getting-started.html
// */
//@Configuration
//@EnableWebSecurity
//public class MSecurityConfig {
//
//	/**
//	 *  Spring Authorization Server 相关配置
//	 *  主要配置OAuth 2.1和OpenID Connect 1.0
//	 * @param http
//	 * @return
//	 * @throws Exception
//	 */
//	@Bean
//	@Order(1)
//	public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http)
//			throws Exception {
//		OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
//		http.getConfigurer(OAuth2AuthorizationServerConfigurer.class)
//			//开启OpenID Connect 1.0（其中oidc为OpenID Connect的缩写）
//			.oidc(Customizer.withDefaults());	// Enable OpenID Connect 1.0
//
//		http
//			// Redirect to the login page when not authenticated from the
//			// authorization endpoint
//			//将需要认证的请求，重定向到login进行登录认证。
//			.exceptionHandling((exceptions) -> exceptions
//				.defaultAuthenticationEntryPointFor(
//					new LoginUrlAuthenticationEntryPoint("/login"),
//					new MediaTypeRequestMatcher(MediaType.TEXT_HTML)
//				)
//			)
//			// Accept access tokens for User Info and/or Client Registration
//			// 使用jwt处理接收到的access token
//			.oauth2ResourceServer((resourceServer) -> resourceServer
//				.jwt(Customizer.withDefaults()));
//
//		return http.build();
//	}
//
//	/**
//	 *  Spring Security 过滤链配置（此处是纯Spring Security相关配置）
//	 */
//	@Bean
//	@Order(2)
//	public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http)
//			throws Exception {
//		http
//			.authorizeHttpRequests((authorize) -> authorize
//				//设置所有请求都需要认证，未认证的请求都被重定向到login页面进行登录
//				.anyRequest().authenticated()
//			)
//			// Form login handles the redirect to the login page from the
//			// authorization server filter chain
//			// 由Spring Security过滤链中UsernamePasswordAuthenticationFilter过滤器拦截处理“login”页面提交的登录信息。
//			.formLogin(Customizer.withDefaults());
//
//		return http.build();
//	}
//
//	/**
//	 * 设置用户信息，校验用户名、密码
//	 * 此处相当于京东对接微信平台，此处用户名密码用于微信平台自身用户校验
//	 * @return
//	 */
//	@Bean
//	public UserDetailsService userDetailsService() {
//		UserDetails userDetails = User.withDefaultPasswordEncoder()
//				.username("fox")
//				.password("123")
//				.roles("admin")
//				.build();
//		//基于内存的用户数据校验
//		return new InMemoryUserDetailsManager(userDetails);
//	}
//
////	/**
////	 * 注册客户端信息
////	 *
////	 * 查询认证服务器信息
////	 * http://127.0.0.1:9000/.well-known/openid-configuration
////	 *
////	 * 获取授权码
////	 * http://localhost:9000/oauth2/authorize?response_type=code&client_id=oidc-client&scope=profile openid&redirect_uri=http://www.baidu.com
////	 *
////	 */
////	@Bean
////	public RegisteredClientRepository registeredClientRepository() {
////		RegisteredClient oidcClient = RegisteredClient.withId(UUID.randomUUID().toString())
////				.clientId("oidc-client")
////				//{noop}开头，表示“secret”以明文存储
////				.clientSecret("{noop}secret").clientName("web平台")
////				.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
////				// 配置授权码模式,刷新令牌，客户端模式
////				.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
////				.authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
////				.authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
//////				.redirectUri("http://mall-authconter:9001/login/oauth2/code/messaging-client-oidc")
////				//我们暂时还没有客户端服务，以免重定向跳转错误导致接收不到授权码
////				.redirectUri("http://localhost:8080/login/oauth2/code/messaging-client-oidc")
//////				.postLogoutRedirectUri("http://127.0.0.1:9001/")
////				//设置客户端权限范围
////				.scope(OidcScopes.OPENID)
////				.scope(OidcScopes.PROFILE)
////				//客户端设置用户需要确认授权
////				.clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
////				.build();
////
////		//配置基于内存的客户端信息
////		return new InMemoryRegisteredClientRepository(oidcClient);
////	}
//
//	/**
//	 * 配置 JWK，为JWT(id_token)提供加密密钥，用于加密/解密或签名/验签
//	 * JWK详细见：https://datatracker.ietf.org/doc/html/draft-ietf-jose-json-web-key-41
//	 */
//	@Bean
//	public JWKSource<SecurityContext> jwkSource() {
//		KeyPair keyPair = generateRsaKey();
//		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
//		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
//		RSAKey rsaKey = new RSAKey.Builder(publicKey)
//				.privateKey(privateKey)
//				.keyID(UUID.randomUUID().toString())
//				.build();
//		JWKSet jwkSet = new JWKSet(rsaKey);
//		return new ImmutableJWKSet<>(jwkSet);
//	}
//
//	/**
//	 *  生成RSA密钥对，给上面jwkSource() 方法的提供密钥对
//	 */
//	private static KeyPair generateRsaKey() {
//		KeyPair keyPair;
//		try {
//			KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
//			keyPairGenerator.initialize(2048);
//			keyPair = keyPairGenerator.generateKeyPair();
//		}
//		catch (Exception ex) {
//			throw new IllegalStateException(ex);
//		}
//		return keyPair;
//	}
//
//	/**
//	 * 配置jwt解析器
//	 */
//	@Bean
//	public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
//		return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
//	}
//
//	/**
//	 * 配置授权服务器请求地址
//	 */
//	@Bean
//	public AuthorizationServerSettings authorizationServerSettings() {
//		//什么都不配置，则使用默认地址
//		return AuthorizationServerSettings.builder().build();
//	}
//	@Bean
//	public RegisteredClientRepository registeredClientRepository(JdbcTemplate jdbcTemplate) {
//		return new JdbcRegisteredClientRepository(jdbcTemplate);
//	}
//
//
//	/**
//	 * 授权信息
//	 * 对应表：oauth2_authorization
//	 */
//	@Bean
//	public OAuth2AuthorizationService authorizationService(JdbcTemplate jdbcTemplate, RegisteredClientRepository registeredClientRepository) {
//		return new JdbcOAuth2AuthorizationService(jdbcTemplate, registeredClientRepository);
//	}
//
//	/**
//	 * 授权确认
//	 *对应表：oauth2_authorization_consent
//	 */
//	@Bean
//	public OAuth2AuthorizationConsentService authorizationConsentService(JdbcTemplate jdbcTemplate, RegisteredClientRepository registeredClientRepository) {
//		return new JdbcOAuth2AuthorizationConsentService(jdbcTemplate, registeredClientRepository);
//	}
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
//        return configuration.getAuthenticationManager();
//    }
//
//	@Bean
//	public PasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder();
//	}
//}