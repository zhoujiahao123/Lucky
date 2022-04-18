package com.uestc.luckyuser;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.uestc.luckyuser"})
@MapperScan("com.uestc.luckyuser.dao")
public class LuckyUserApplication {
    public static void main(String[] args) {
        SpringApplication.run(LuckyUserApplication.class, args);
    }
}
