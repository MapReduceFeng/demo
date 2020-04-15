package com.example.demo.user.dao;

import com.example.demo.user.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface UserDao {
    @Select({"select * from users"})
    List<User> getData();

    @Insert({"INSERT INTO users(id,userName,userRole) VALUES (#{id},#{userName},#{userRole})"})
    int addData(User user);

    @Update({"update usesr set userName=#{userName} where id=#{id}"})
    int updateData(User user);

    @Select({"select * from users where userName=#{userName}"})
    List<User> getDataByName(User user);

    @Select({"CREATE TABLE ${newTable} SELECT * FROM ${oldTable} where 1!=1"})
    void createTables(String newTable, String oldTable);

}
