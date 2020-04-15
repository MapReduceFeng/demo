package com.example.demo.menu.dao;

import com.example.demo.menu.entity.Menu;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface MenuDao {
    @Select({"select * from menu"})
    List<Menu> getData();
}
