package com.flightbooking.APIGatewayService.filter;

import com.flightbooking.APIGatewayService.util.JwtUtil;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthFilter extends AbstractGatewayFilterFactory<JwtAuthFilter.Config> {

    public JwtAuthFilter() {
        super(Config.class);
    }

    public static class Config {}

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {

            if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                throw new RuntimeException("Missing Authorization Header");
            }

            String authHeader = exchange.getRequest().getHeaders()
                    .get(HttpHeaders.AUTHORIZATION).get(0);

            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                authHeader = authHeader.substring(7);
            }

            try {
                JwtUtil.validateToken(authHeader);
            } catch (Exception e) {
                throw new RuntimeException("Invalid Token");
            }

            return chain.filter(exchange);
        };
    }
}