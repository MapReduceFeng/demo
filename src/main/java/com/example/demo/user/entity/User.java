package com.example.demo.user.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description
 * @Author
 */
@Data
public class User implements Serializable {
    private static final long serialVersionUID = 2851375730097433630L;
    private long id;

    private String userName;

    private String userPass;

    private String userRole;

    private String tableName;


}
