package com.example.demo.user.service;

import com.example.demo.user.entity.User;

import java.util.List;

public interface UserService {
    List<User> getData();

    int addData(User user);

    int updateData(User user);

    void createTables(String newTable, String oldTable);
}
