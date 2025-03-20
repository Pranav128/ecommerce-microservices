package com.app.ecommerce.api_gateway.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.security.Key;
import java.util.Objects;

@Configuration
public class GlobalConfig {

    private static final Logger log = LoggerFactory.getLogger(GlobalConfig.class);
    //same secret as user service
    @Value("${jwt.secret}")
    private String jwtSecret;

    private Key getKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()

        //User and authentication routes
                //1. Auth Route - No JWT Required
                .route("Ecommerce-App-User-service", r ->
                        r.path("/api/auth/**","/v3/api-docs/**")
                        .uri("lb://Ecommerce-App-User-service"))

                //2.  User Route authenticated and also with headers like email and role
                .route("Ecommerce-App-User-service", r -> r.path("/api/users/**")
                        .filters(f -> f.filter(jwtAuthenticationFilter()))
                        .uri("lb://Ecommerce-App-User-service")) // Load balanced routing

        //Product routes
                //1. Product Route authenticated and also with headers like email and role
                .route("Ecommerce-App-Product-service", r -> r.path("/api/products/**")
                        .filters(f -> f.filter(jwtAuthenticationFilter()))
                        .uri("lb://Ecommerce-App-Product-service"))
                //2. Category Route authenticated and also with headers like email and role
                .route("Ecommerce-App-Product-service", r -> r.path("/api/categories/**")
                        .filters(f -> f.filter(jwtAuthenticationFilter()))
                        .uri("lb://Ecommerce-App-Product-service"))

        //Cart routes
                //1. Cart Route authenticated and also with headers like email and role
                .route("Ecommerce-App-Cart-service", r -> r.path("/api/carts/**")
                        .filters(f -> f.filter(jwtAuthenticationFilter()))
                        .uri("lb://Ecommerce-App-Cart-service"))

        //Order routes
                //1. Order Route authenticated and also with headers like email and role
                .route("Ecommerce-App-Order-service", r -> r.path("/api/orders/**")
                        .filters(f -> f.filter(jwtAuthenticationFilter()))
                        .uri("lb://Ecommerce-App-Order-service"))

        //Payment routes
                //1. Payment Route authenticated and also with headers like email and role
                .route("Ecommerce-App-Payment-service", r -> r.path("/api/payments/**")
                        .filters(f -> f.filter(jwtAuthenticationFilter()))
                        .uri("lb://Ecommerce-App-Payment-service"))

        //Review routes
                //1. Review Route authenticated and also with headers like email and role
                .route("Ecommerce-App-Review-service", r -> r.path("/api/reviews/**")
                        .filters(f -> f.filter(jwtAuthenticationFilter()))
                        .uri("lb://Ecommerce-App-Review-service"))

        //Notification routes
                //1. Notification Route authenticated and also with headers like email and role
                .route("Ecommerce-App-Notification-service", r -> r.path("/api/notifications/**")
                        .filters(f -> f.filter(jwtAuthenticationFilter()))
                        .uri("lb://Ecommerce-App-Notification-service"))

                // Delete Product Route (Admin Only)
                /*
                        .route("Ecommerce-App-Product-service-delete", r -> r.path("/api/products/{id}")
                                .and().method("DELETE")
                        .filters(f -> f.filter(adminAuthorizationFilter()))
                        .uri("lb://Ecommerce-App-Product-service"))
                */

                // Add routes for other services
                .build();
    }

    @Bean
    public GatewayFilter jwtAuthenticationFilter() {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            if (!request.getHeaders().containsKey("Authorization")) {
                return onError(exchange, "No Authorization header", HttpStatus.UNAUTHORIZED);
            }

            String authHeader = Objects.requireNonNull(request.getHeaders().get("Authorization")).get(0);
            if (!authHeader.startsWith("Bearer ")) {
                return onError(exchange, "Invalid Authorization header", HttpStatus.UNAUTHORIZED);
            }

            // Remove "Bearer "
            String token = authHeader.substring(7);
            try {
                Claims claims = Jwts.parserBuilder()
                        .setSigningKey(getKey())
                        .build()
                        .parseClaimsJws(token)
                        .getBody();

                // Forward user information (claims) to downstream services
                ServerHttpRequest modifiedRequest = request.mutate()
                        .header("X-User-Email", claims.getSubject())
                        .header("X-User-Role", claims.get("role").toString())
                        .build();
                return chain.filter(exchange.mutate().request(modifiedRequest).build());
            } catch (Exception e) {
                log.error(e.toString());
                return onError(exchange, "Invalid JWT", HttpStatus.UNAUTHORIZED);
            }
        };
    }

//    @Bean
    @SuppressWarnings("unused")
    public GatewayFilter adminAuthorizationFilter() {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            String userRole = Objects.requireNonNull(request.getHeaders().get("X-User-Role")).get(0);

            if (userRole == null || !userRole.equals("ROLE_ADMIN")) {
                return onError(exchange, "Unauthorized: Admin role required", HttpStatus.FORBIDDEN);
            }

            System.out.println("all done");
            return chain.filter(exchange);
        };
    }

    // Helper method to handle errors
    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        log.error(err);
        return response.setComplete();
    }
}