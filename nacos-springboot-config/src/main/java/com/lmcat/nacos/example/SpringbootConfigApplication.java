package com.lmcat.nacos.example;

import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动配置管理
 */
@SpringBootApplication
//@NacosPropertySource(dataId = "nacos-springboot", autoRefreshed = true)
@NacosPropertySource(dataId = "example", autoRefreshed = true)
public class SpringbootConfigApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootConfigApplication.class, args);
    }
}
