package com.gome.cn.sharding.mybatis.model;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author liucm
 * @date 2017/12/20
 */
@Table(name = "t_order")
@Data
public class Order {
    @Id
    private Long orderId;

    private Long userId;
}
