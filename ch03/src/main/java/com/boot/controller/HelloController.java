package com.boot.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Administrator
 * @date 2017/11/6
 */
@RestController
public class HelloController {
    @RequestMapping("/")
    public String hello(){
        return "Greetings from Spring Boot!";
    }
}
