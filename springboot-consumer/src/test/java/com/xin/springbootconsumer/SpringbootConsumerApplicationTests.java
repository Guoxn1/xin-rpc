package com.xin.springbootconsumer;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class SpringbootConsumerApplicationTests {

    @Resource
    private Main main;

    @Test
    void contextLoads() {
        main.main1();
    }

}
