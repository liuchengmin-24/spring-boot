package com.boot.controller;

import com.boot.dto.ReqVo;
import com.boot.model.Message;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SwaggerControlller {
    //http://localhost:8084//boot-swagger/swagger-ui.html
    @ApiOperation(value = "Swagger查询接口")
    @RequestMapping(value = "/query",method = RequestMethod.POST)
    public List<Message> query(ReqVo reqVo){
        return null;
    }
}
