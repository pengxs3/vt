package com.vectortrading.vectortrading;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(cn.hutool.extra.spring.SpringUtil.class)
public class VectorTradingApplication {
    public static void main(String[] args) {
        SpringApplication.run(VectorTradingApplication.class, args);
    }

}
