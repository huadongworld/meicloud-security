package com.meicloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author HuaDong
 * @date 2020/4/11 17:33
 */
@SpringBootApplication
@RestController
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @GetMapping("/hello")
    public String hello() {
        return "hello spring security";
    }

    @GetMapping("/hello222")
    public String hello222() {
        return "hello222 spring security";
    }
}
