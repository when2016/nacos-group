package com.lmcat.nacos.service;

import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import org.springframework.stereotype.Service;

@Service
public class ConfigServiceDemo {

    @NacosInjected
    private ConfigService configService;

    public void demoGetConfig() {
        try {
            String dataId = "nacos-springboot";
            String group = "test";
            String content = configService.getConfig(dataId, group, 5000);
            System.out.println(content);
        } catch (NacosException e) {
            e.printStackTrace();
        }
    }
}
