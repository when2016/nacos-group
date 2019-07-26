package com.lmcat.test.example.gateway.core.filter;

//import com.longmaodata.longmao.spring.autoconfigure.dict.DictCache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.cors.reactive.CorsUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.web.cors.CorsConfiguration.ALL;

@Slf4j
@Configuration
public class AjaxCorsFilter {

    //@Autowired
    //private DictCache dictCache;

    public AjaxCorsFilter() {
        log.info("LongMaoLoading: {}", AjaxCorsFilter.class.getName());
    }

    @Bean
    public WebFilter corsFilter() {
        return (ServerWebExchange exchange, WebFilterChain chain) -> {
            if (!CorsUtils.isCorsRequest(exchange.getRequest())) {
                return chain.filter(exchange);
            }

            //Map<String,String> cors = dictCache.getDictNodeList("V2.SECURITY.CORS");
            Map<String, String> cors = new HashMap<>();
            ServerHttpRequest request = exchange.getRequest();
            HttpHeaders requestHeaders = request.getHeaders();
            String origin = requestHeaders.getOrigin();
            if (!cors.containsKey(origin)) {
                if (origin != null && origin.matches("^https?://(manager|login)\\.longmaosoft\\.com$")) {
                    //TODO
                } else {
                    log.warn("cors: {}", origin);
                    exchange.getResponse().setStatusCode(HttpStatus.OK);
                    return Mono.empty();
                }
            }

            HttpHeaders headers = exchange.getResponse().getHeaders();
            if (!headers.containsKey(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN)) {
                headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, origin);
                headers.addAll(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, requestHeaders.getAccessControlAllowHeaders());
                headers.addAll(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, requestHeaders.getAccessControlRequestHeaders());
                headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "*");
                headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
                headers.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, ALL);
            }

            if (request.getMethod() == HttpMethod.OPTIONS) {
                exchange.getResponse().setStatusCode(HttpStatus.OK);
                return Mono.empty();
            } else {
                return chain.filter(exchange);
            }
        };
    }

}
