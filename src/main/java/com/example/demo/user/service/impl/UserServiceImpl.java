package com.example.demo.user.service.impl;

import com.example.demo.system.annotation.CreateTable;
import com.example.demo.user.dao.UserDao;
import com.example.demo.user.entity.User;
import com.example.demo.user.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserDao userDao;

    @Override
    public List<User> getData() {
        User user = new User();
        user.setUserName("编程特别");
        System.out.println(">> " + userDao.getDataByName(user));
        return userDao.getData();
    }

    @Override
    @CreateTable
    public int addData(User user) {
        return userDao.addData(user);
    }

    @Override
    public int updateData(User user) {
        return userDao.updateData(user);
    }

    @Override
    public void createTables(String newTable, String oldTable) {
        userDao.createTables(newTable, oldTable);
    }
}
