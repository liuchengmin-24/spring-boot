package com.boot.model;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
public class Account {
    @Id
    @GeneratedValue
    private int id ;
    private String name ;
    private double money;
}
