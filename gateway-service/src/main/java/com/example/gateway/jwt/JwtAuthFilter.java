package com.example.gateway.jwt;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class JwtAuthFilter extends AbstractGatewayFilterFactory<Object> {

    private final JwtUtil jwtUtil = new JwtUtil();

    @Override
    public GatewayFilter apply(Object config) {

        return (exchange, chain) -> {

            ServerHttpRequest request = exchange.getRequest();

            // allow auth endpoints
            if (request.getURI().getPath().contains("/auth/")) {
                return chain.filter(exchange);
            }

            // check header
            if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            String authHeader = request.getHeaders()
                    .getFirst(HttpHeaders.AUTHORIZATION);

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            String token = authHeader.substring(7);

            // validate token
            if (!jwtUtil.validateToken(token)) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }
            
            String emailFromToken = jwtUtil.extractUsername(token);

            request.mutate()
                    .header("X-User-Email", emailFromToken)
                    .build();

            return chain.filter(exchange);
        };
    }
}