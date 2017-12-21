package com.boot.entity;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Administrator
 * @date 2017/11/9
 */
@Data
public class PersonForm {
    @NotNull
    @Size(min = 2,max = 30)
    private String name;
    @NotNull
    @Min(18)
    private Integer age;
}
