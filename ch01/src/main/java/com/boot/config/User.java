package com.boot.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Data
@PropertySource(value = "classpath:test.yml")
@ConfigurationProperties(prefix = "com.forezp")
public class User {
    private String name;
    private int age;
}
