package com.lmcat.service.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
@RequestMapping("config")
public class ConfigController {

    @Value(value = "${useLocalCache:false}")
    private boolean useLocalCache;
    @Value(value = "${name}")
    private String name;

    @RequestMapping(value = "/get", method = GET)
    @ResponseBody
    public boolean get() {
        System.out.println("name==" + name);
        return useLocalCache;
    }
}