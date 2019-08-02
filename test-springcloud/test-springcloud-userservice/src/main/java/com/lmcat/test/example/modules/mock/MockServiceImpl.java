package com.lmcat.test.example.modules.mock;

public class MockServiceImpl implements MockService {

    public MockServiceImpl(DependencyService dependencyService){

    }

    @Override
    public void getEcho() {
        System.out.println("method=getEcho()");
    }
}
