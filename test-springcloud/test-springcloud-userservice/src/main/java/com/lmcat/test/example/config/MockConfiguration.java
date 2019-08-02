package com.lmcat.test.example.config;

import com.lmcat.test.example.modules.mock.DependencyService;
import com.lmcat.test.example.modules.mock.DependencyServiceImpl;
import com.lmcat.test.example.modules.mock.MockService;
import com.lmcat.test.example.modules.mock.MockServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MockConfiguration {
    @Bean
    public MockService mockService() {
        return new MockServiceImpl(dependencyService());
    }

    private DependencyService dependencyService() {
        return new DependencyServiceImpl();
    }
}
