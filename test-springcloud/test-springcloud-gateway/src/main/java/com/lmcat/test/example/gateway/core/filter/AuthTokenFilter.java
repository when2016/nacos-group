package com.lmcat.test.example.gateway.core.filter;

import com.alibaba.fastjson.JSONObject;
import com.lmcat.test.example.gateway.core.utils.IpUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.DigestUtils;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Map;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.getUriTemplateVariables;

@Slf4j
@Configuration
public class AuthTokenFilter implements GlobalFilter, Ordered {

//    @Autowired
//    private AuthCache authCache;
//
//    @Autowired
//    private TokenCache tokenCache;

    public AuthTokenFilter() {
        log.info("LongMaoLoading: {}", AuthTokenFilter.class.getName());
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        Map<String, String> uriVariables = getUriTemplateVariables(exchange);
        String ip = IpUtils.getRemoteAddr(request);

        if ("loc".equals(uriVariables.get("type"))) {
            log.warn("外网网关没有权限访问loc类型的接口, {}, {}", request.getURI(), ip);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        String token = request.getHeaders().getFirst("Token");
        if (request.getHeaders().containsKey("LongMao-User")) {
            request.getHeaders().remove("LongMao-User");
        }
        JSONObject user = getUser(token);
        safe(uriVariables, user, request, token);

        ServerHttpRequest.Builder builder = request.mutate();
        builder.header("User-Ip", ip);
        if (user != null && "200".equals(user.getString("code"))) {
            builder.header("LongMao-User", user.getString("uid"));
            builder.header("LongMao-User-Type", user.getString("type"));
        } else {
            builder.header("LongMao-User-Type", "0");
        }
        return chain.filter(exchange.mutate().request(builder.build()).build());
    }

    private JSONObject getUser(String token) {
        if (token == null) return null;
        return null;
        //return tokenCache.getSession(token);
    }

    private void safe(Map<String, String> uriVariables, JSONObject user, ServerHttpRequest request, String token) {
        if (request.getMethodValue().equals("OPTIONS")) return;
        if (!"safe".equals(uriVariables.get("safe"))) return;

        if (user == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "登录超时"); //登录超时
        }
        if (!"200".equals(user.getString("code"))) {
            log.warn("Invalid User-Session: {}", user);
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, user.getString("message")); //登录超时
        }

        HttpHeaders headers = request.getHeaders();
        String userAgent = headers.getFirst(HttpHeaders.USER_AGENT);
        if (!user.getString("agent").equals(userAgent)) {
            log.warn("Invalid User-Agent [{}] {}", userAgent, user);
            //tokenCache.deleteSession(token);
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "无效的Session"); //登录超时
        } //TODO 如果浏览器版本升级，会导致此验证失败

        if (!"mgr".equals(uriVariables.get("type"))) return;
        Integer userType = user.getInteger("type");
        if (userType == null) userType = 1;
        if ((userType & 2) > 0) return; //超级管理员不验证
        String eid = DigestUtils.md5DigestAsHex(String.format(
                "%s-%s-%s-SERVICE-%s",
                request.getPath().toString(),
                request.getMethodValue().toUpperCase(),
                uriVariables.get("group").toUpperCase(),
                uriVariables.get("system").toUpperCase()
        ).getBytes());
        //if(authCache.exist(eid, user.getString("uid"))) return;

        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "没有访问权限"); //登录超时
    }

    @Override
    public int getOrder() {
        return 0;
    }

}
