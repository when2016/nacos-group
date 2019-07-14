package com.lmcat.nacos.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * 启动配置管理
 */
@SpringBootApplication
@EnableDiscoveryClient
public class NacosConsumerApplication {


    public static void main(String[] args) {
        SpringApplication.run(NacosConsumerApplication.class, args);
    }

    @Autowired
    LoadBalancerClient loadBalancerClient;

    @RestController
    public class TestController {


        @RequestMapping(value = "/echo/{str}", method = RequestMethod.GET)
        public String echo(@PathVariable String str) {

            // 通过spring cloud common中的负载均衡接口选取服务提供节点实现接口调用
            ServiceInstance serviceInstance = loadBalancerClient.choose("service-provider");

            //String url = "http://192.168.43.212:8070/echo/" + str;
            String url = serviceInstance.getUri() + "/echo/" + str;

            RestTemplate restTemplate = new RestTemplate();
            return restTemplate.getForObject(url, String.class);
        }
    }

}
