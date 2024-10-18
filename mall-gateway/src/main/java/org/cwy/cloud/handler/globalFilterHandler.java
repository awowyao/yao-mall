package org.cwy.cloud.handler;

import jakarta.annotation.Resource;

import org.cwy.cloud.feign.AuthcentFeign;
import org.cwy.cloud.feign.uniqidFeign;
import org.cwy.cloud.model.UserAuto;
import org.cwy.cloud.service.authService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.Ordered;
//import org.springframework.security.oauth2.common.OAuth2AccessToken;
//import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


@Component
public class globalFilterHandler implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String currentUrl = exchange.getRequest().getURI().getPath();

//        tokenStore.readAccessToken("eyJhbGciOiJIUzUxMiJ9.eyJzdWJqZWN0IjoidGVzdCIsImNyZWF0ZWQiOjE3MjA1OTY2NDM0NzEsImV4cCI6MTcyNTE2NDQ2NX0.pRuwDF85p3UtXzuVZ1M3U9OSg7vRCsxZZCZX_lL58EFsArNKDVITuccw-4HlceAoPMMIeLpc6HUwskf4lFLdXA");
//        System.out.println(authService.getUserMsg());
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
