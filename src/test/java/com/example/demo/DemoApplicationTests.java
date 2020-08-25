package com.example.demo;

import com.example.demo.service.TestService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class DemoApplicationTests extends SpringBootServletInitializer {
@Autowired
private TestService testService;
    @Test
    public void contextLoads() {
        String t=testService.jiemiStr();

        System.out.println("===========解密后的字符串："+t);
    }

}
