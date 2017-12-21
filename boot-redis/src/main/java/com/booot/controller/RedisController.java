package com.booot.controller;

import com.booot.dao.RedisDao;
import com.booot.model.Message;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Log4j
public class RedisController {
    @Autowired
    private RedisDao redisDao;

    @RequestMapping(value = "/set", method = RequestMethod.POST)
    public String set(Message message) {
        redisDao.setKey(message.getKey(),message.toString());
        log.info("message:"+message.toString());
        return message.toString();
    }
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public String get(@RequestParam(value = "key") String key) {
        String str=redisDao.getValue(key);
        return str;
    }
}
