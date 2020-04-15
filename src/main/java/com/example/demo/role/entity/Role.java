package com.example.demo.role.entity;

import lombok.Data;

import java.io.Serializable;

import org.springframework.data.annotation.Id;

/**
 * @Description
 * @Author
 */
@Data
public class Role implements Serializable {
    private static final long serialVersionUID = 6704843292858836309L;

    @Id
    private Integer id;

    private String roleName;

    private String roleMenu;


}
