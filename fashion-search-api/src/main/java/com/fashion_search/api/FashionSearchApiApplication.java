package com.fashion_search.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.fashion_search")
public class FashionSearchApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(FashionSearchApiApplication.class, args);
    }
}
