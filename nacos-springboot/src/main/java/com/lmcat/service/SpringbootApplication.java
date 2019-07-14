package com.lmcat.service;

import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动配置管理
 */
@SpringBootApplication
//@NacosPropertySource(dataId = "nacos-springboot", autoRefreshed = true)
@NacosPropertySource(dataId = "example", autoRefreshed = true)
public class SpringbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootApplication.class, args);
    }
}
