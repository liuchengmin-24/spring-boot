package com.boot.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 自定义属性
 */
@RestController
public class MiyaController {
    @Value("${my.name}")
    private String name;
    @Value("${my.age}")
    private int age;
    @RequestMapping(value = "/miya")
    public String miya(){
        return name+":"+age;
    }
}
