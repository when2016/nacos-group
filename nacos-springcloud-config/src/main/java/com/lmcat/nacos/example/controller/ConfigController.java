package com.lmcat.nacos.example.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/config")
@RefreshScope
public class ConfigController {

    @Value("${useLocalCache:false}")
    private boolean useLocalCache;
    @Value("${name}")
    private String name;

    @RequestMapping("/get")
    public String get() {
        return "useLocalCache=" + useLocalCache + ",name=" + name;
    }
}