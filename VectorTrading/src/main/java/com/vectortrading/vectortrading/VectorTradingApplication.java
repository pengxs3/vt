package com.vectortrading.vectortrading;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;

@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class})
@Import(cn.hutool.extra.spring.SpringUtil.class)
@EnableScheduling
public class VectorTradingApplication {
    public static void main(String[] args) {
        SpringApplication.run(VectorTradingApplication.class, args);
    }

    @Scheduled(fixedRate = 10 * 1000)
    public void testMethod() {
        System.out.println("每10秒执行一次: " + new Date());
    }
}
