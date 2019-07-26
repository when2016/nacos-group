package com.lmcat.test.example.gateway.core.filter;

import com.lmcat.test.example.gateway.core.utils.IpUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

//import com.longmaodata.longmao.spring.autoconfigure.dict.DictCache;

@Slf4j
@Configuration
@ConditionalOnExpression("'${spring.cloud.config.label}'.equals('master')")
public class GatewayEndpointFilter {

//    @Autowired
//    private DictCache dictCache;

    public GatewayEndpointFilter() {
        log.info("LongMaoLoading: {}", GatewayEndpointFilter.class.getName());
    }

    @Bean
    public WebFilter gatewayEndpointWebFilter() {
        return (ServerWebExchange exchange, WebFilterChain chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            String api = request.getPath().pathWithinApplication().value();
            if (!api.startsWith("/actuator")) {
                return chain.filter(exchange);
            }

//            Map<String, String> endpoints = dictCache.getDictNodeList("V2.SECURITY.ENDPOINTS");
            Map<String, String> endpoints = new HashMap<>();
            String domain = request.getURI().toString().replaceAll("https?://([^/:]+)(:\\d+)?/.+", "$1");
            if (endpoints.containsKey(domain)) {
                return chain.filter(exchange);
            }

            String ip = IpUtils.getRemoteAddr(request);
            log.warn("bad actuator request ip: {}, demain: {}", ip, domain);

            exchange.getResponse().setStatusCode(HttpStatus.NOT_FOUND);
            return Mono.empty();
        };
    }

}
