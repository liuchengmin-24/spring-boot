package com.gome.cn.sharding.mybatis.controller;

import com.gome.cn.sharding.mybatis.mapper.OrderMapper;
import com.gome.cn.sharding.mybatis.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author liucm
 * @date 2017/12/22
 */
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderMapper orderMapper;
    @RequestMapping("/add")
    public Object add() {
        for (int i = 0; i < 10; i++) {
            Order order = new Order();
            order.setUserId((long) i);
            order.setOrderId((long) i);
            orderMapper.insert(order);
        }
        for (int i = 10; i < 20; i++) {
            Order order = new Order();
            order.setUserId((long) i + 1);
            order.setOrderId((long) i);
            orderMapper.insert(order);
        }
        return "success";
    }

    @RequestMapping("query")
    private Object queryAll() {
        return orderMapper.selectAll();
    }
}
