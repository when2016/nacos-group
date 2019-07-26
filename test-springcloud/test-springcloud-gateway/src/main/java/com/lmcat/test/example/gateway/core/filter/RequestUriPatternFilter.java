package com.lmcat.test.example.gateway.core.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.RouteToRequestUrlFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Map;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.*;

@Slf4j
@Configuration
public class RequestUriPatternFilter implements GlobalFilter, Ordered {

    public RequestUriPatternFilter() {
        log.info("LongMaoLoading: {}", RequestUriPatternFilter.class.getName());
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        URI uri = exchange.getAttribute(GATEWAY_REQUEST_URL_ATTR);
        if(!uri.getHost().equals("group-service-system")) {
            return chain.filter(exchange);
        }

        Map<String, String> uriVariables = getUriTemplateVariables(exchange);
        boolean encoded = containsEncodedParts(uri);
        URI mergedUrl = UriComponentsBuilder.fromUri(uri)
                .scheme(uri.getScheme())
                .host(String.format("%s-service-%s", uriVariables.get("group"), uriVariables.get("system")).toUpperCase())
                .port(uri.getPort())
                .build(encoded)
                .toUri();

        exchange.getAttributes().put(GATEWAY_REQUEST_URL_ATTR, mergedUrl);
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return RouteToRequestUrlFilter.ROUTE_TO_URL_FILTER_ORDER + 1;
    }

}
