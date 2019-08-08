package com.lmcat.cloud.project.eureka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class ServiceRegistryApplication {
    /**
     * logger instance
     */
    static Logger logger = LoggerFactory.getLogger(ServiceRegistryApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(ServiceRegistryApplication.class, args);
        logger.info("【【【Spring Cloud Eureka Server 启动完成.】】】");
    }
}
