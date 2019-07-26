package com.lmcat.test.example.gateway.core.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;

public class IpUtils {

    /**
     * 获取客户端IP地址
     * @param request
     * @return
     */
    public static String getRemoteAddr(ServerHttpRequest request) {
        HttpHeaders headers = request.getHeaders();
        String ip = headers.getFirst("X-Forwarded-For");
        if(StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = headers.getFirst("Proxy-Client-IP");
            if(StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
                ip = headers.getFirst("WL-Proxy-Client-IP");
                if(StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
                    ip = request.getRemoteAddress().getHostName();
                }
            }
        }
        if(StringUtils.isEmpty(ip)) {
            return null;
        }else{
            return ip.split(",")[0];
        }
    }

}
