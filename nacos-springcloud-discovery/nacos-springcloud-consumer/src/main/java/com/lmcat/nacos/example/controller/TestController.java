package com.lmcat.nacos.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("discovery")
public class TestController {

    private final RestTemplate restTemplate;

    @Autowired
    public TestController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @RequestMapping(value = "echo/{str}", method = RequestMethod.GET)
    public String echo(@PathVariable String str) {
        String url = String.format("http://service-provider/discovery/echo/%s", str);
        System.out.println("url=" + url);
        String resultStr = restTemplate.getForObject(url, String.class);
        System.out.println("resultStr=" + resultStr);
        return resultStr;
    }
}
