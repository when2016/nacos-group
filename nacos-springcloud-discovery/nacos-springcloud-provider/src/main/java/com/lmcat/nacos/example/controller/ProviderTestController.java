package com.lmcat.nacos.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
@Slf4j
public class ProviderTestController {

    @GetMapping("hello")
    public String hello(@RequestParam String name) {
        log.info("invoked hello name = {}", name);
        return "Hello Nacos Discovery " + name;
    }
}

