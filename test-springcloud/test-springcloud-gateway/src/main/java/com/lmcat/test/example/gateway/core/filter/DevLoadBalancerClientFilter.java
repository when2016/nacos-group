package com.lmcat.test.example.gateway.core.filter;

import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.gateway.config.LoadBalancerProperties;
import org.springframework.cloud.gateway.filter.LoadBalancerClientFilter;
import org.springframework.cloud.netflix.ribbon.RibbonLoadBalancerClient;
import org.springframework.cloud.netflix.ribbon.SpringClientFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.server.ServerWebExchange;

import java.net.URI;
import java.util.List;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR;

@Slf4j
@Configuration
@ConditionalOnExpression("'${spring.cloud.config.label}'.equals('dev')")
public class DevLoadBalancerClientFilter extends LoadBalancerClientFilter {

    @Autowired
    private SpringClientFactory clientFactory;

    public DevLoadBalancerClientFilter(LoadBalancerClient loadBalancer, LoadBalancerProperties properties) {
        super(loadBalancer, properties);
        log.info("LongMaoLoading: {}", DevLoadBalancerClientFilter.class.getName());
    }

    protected ServiceInstance choose(ServerWebExchange exchange) {
        String ip = exchange.getRequest().getQueryParams().getFirst("load-balancer");
        if(ip == null) {
            ip = exchange.getRequest().getHeaders().getFirst("Load-Balancer");
        }

        if(ip != null) {
            String name = ((URI) exchange.getAttribute(GATEWAY_REQUEST_URL_ATTR)).getHost();
            ILoadBalancer loadBalancer = clientFactory.getLoadBalancer(name);
            List<Server> serverList = loadBalancer.getAllServers();
            log.info("dev.load.balancer {}, {}, {}", ip, name, serverList);
            for(Server server : serverList) {
                if(ip.equals(server.getHost())) {
                    return new RibbonLoadBalancerClient.RibbonServer(name, server, true, null);
                }
            }
        }
        return loadBalancer.choose(((URI) exchange.getAttribute(GATEWAY_REQUEST_URL_ATTR)).getHost());
    }

    @Override
    public int getOrder() {
        return LOAD_BALANCER_CLIENT_FILTER_ORDER - 1;
    }

}
