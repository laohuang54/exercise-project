package com.fth;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@MapperScan("com.fth.mapper")
public class FthApplication {

    public static void main(String[] args) {
        SpringApplication.run(FthApplication.class, args);
    }
}
