package com.lmcat.nacos.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("discovery")
public class EchoController {

    @RequestMapping(value = "echo/{string}", method = RequestMethod.GET)
    public String echo(@PathVariable String string) {
        System.out.println("string====" + string);
        System.out.println("string====" + string);
        System.out.println("string====" + string);
        System.out.println("string====" + string);
        return "Hello Nacos Discovery " + string;
    }
}

