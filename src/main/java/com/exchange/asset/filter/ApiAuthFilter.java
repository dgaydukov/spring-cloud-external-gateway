package com.exchange.asset.filter;

import com.exchange.asset.service.AuthService;
import java.util.function.Consumer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
@RequiredArgsConstructor
public class ApiAuthFilter extends AbstractGatewayFilterFactory {
    private final AuthService authService;

    @Override
    public GatewayFilter apply(Object config) {
        return new InnerApiAuthFilter();
    }

    private class InnerApiAuthFilter implements GatewayFilter, Ordered {
        InnerApiAuthFilter() {

        }

        @Override
        public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
            String apiKey = null;
            String sign = null;
            try {
                ServerHttpRequest request = exchange.getRequest();
                apiKey = request.getHeaders().getFirst("X-API-KEY");
                sign = request.getHeaders().getFirst("X-API-SIGNATURE");
                if (authService.authByApiKey(apiKey, sign)){
                    final int userId = authService.getUserIdByApiKey(apiKey);
                    return handle(exchange, chain, true, userId, apiKey, sign);
                }
            } catch (Exception ex) {
                log.error("Failed to process request", ex);
            }
            return handle(exchange, chain, false, 0, apiKey, sign);
        }

        @Override
        public int getOrder() {
            return 0;
        }

        private Mono<Void> handle(ServerWebExchange exchange, GatewayFilterChain chain,
            boolean success, int userId, String apiKey, String sign) {
            if (success) {
                Consumer<HttpHeaders> headers = header -> header.set("X-USER-ID", Integer.toString(userId));
                ServerHttpRequest serverHttpRequest = exchange.getRequest().mutate().headers(headers).build();
                exchange.mutate().request(serverHttpRequest).build();
                return chain.filter(exchange);
            }
            log.warn("Failed to auth request: apiKey={}, sign={}", apiKey, sign);
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
    }

    @Override
    public String name() {
        return "ApiAuthFilter";
    }
}
