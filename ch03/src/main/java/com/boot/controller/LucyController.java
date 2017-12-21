package com.boot.controller;

import com.boot.config.ConfigBean;
import com.boot.config.User;
import com.google.gson.Gson;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 将配置文件的属性赋给实体类
 */
@RestController
@EnableConfigurationProperties({ConfigBean.class,User.class})
@Log4j
public class LucyController {
    @Autowired
    private ConfigBean configBean;
    @RequestMapping(value = "/lucy")
    public String miya(){
        log.info("configBean:"+new Gson().toJson(configBean));
        return new Gson().toJson(configBean).toString();
    }
    @Autowired
    private User user;
    @RequestMapping(value = "/user")
    public String user(){
        log.info("user:"+user.getName());
        return user.getName()+">>>>"+user.getAge();
    }
}
