package com.lmcat.test.example.gateway.core.utils;

import org.springframework.http.HttpCookie;
import org.springframework.http.server.reactive.ServerHttpRequest;

public class CookieUtils {

    public static String getCookie(ServerHttpRequest request, String name) {
        HttpCookie cookie = request.getCookies().getFirst("Token");
        if(cookie != null) {
            return cookie.getValue();
        }
        return null;
    }

}
