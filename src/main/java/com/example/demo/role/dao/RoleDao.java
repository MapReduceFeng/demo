package com.example.demo.role.dao;

import com.example.demo.role.entity.Role;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface RoleDao {
    @Select("select * from role")
    List<Role> getData();
}
