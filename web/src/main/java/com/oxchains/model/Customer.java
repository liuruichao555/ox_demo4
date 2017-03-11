package com.oxchains.model;

import com.oxchains.enums.CustomerType;
import lombok.Data;

import java.io.Serializable;

/**
 * Customer
 *
 * @author liuruichao
 * Created on 2017/3/11 11:25
 */
@Data
public class Customer implements Serializable {
    private Integer id;

    private String cusName;

    private String realName;

    private String password;

    private CustomerType cusType;

    private Integer integral; // TODO 积分，以后用
}
