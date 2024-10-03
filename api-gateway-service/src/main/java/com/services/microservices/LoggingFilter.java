package com.services.microservices;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


@Component
public class LoggingFilter implements GlobalFilter, Ordered {

    private static final Logger logger = LoggerFactory.getLogger(LoggingFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, org.springframework.cloud.gateway.filter.GatewayFilterChain chain) {
        // Log the request details
        logger.info("Request Path: {}", exchange.getRequest().getPath());
        logger.info("Request Method: {}", exchange.getRequest().getMethod());

        // Continue processing the request and log the response
        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            logger.info("Response Status Code: {}", exchange.getResponse().getStatusCode());

        }));
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}

