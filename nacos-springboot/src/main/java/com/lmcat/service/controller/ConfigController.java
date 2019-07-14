package com.lmcat.service.controller;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
@RequestMapping("config")
public class ConfigController {

    @NacosValue(value = "${useLocalCache:false}")
    private boolean useLocalCache;
    @NacosValue(value = "${name}", autoRefreshed = true)
    private String name;

    @RequestMapping(value = "/get", method = GET)
    @ResponseBody
    public String get() {
        System.out.println("name==" + name + ",useLocalCache==" + useLocalCache);
        return "name==" + name + ",useLocalCache==" + useLocalCache;
    }
}