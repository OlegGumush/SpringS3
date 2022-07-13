package com.spring;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class SpringS3Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringS3Application.class, args);
        log.info("Up ....");
    }
}
