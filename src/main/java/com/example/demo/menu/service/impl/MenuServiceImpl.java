package com.example.demo.menu.service.impl;

import com.example.demo.menu.dao.MenuDao;
import com.example.demo.menu.entity.Menu;
import com.example.demo.menu.service.MenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {
    @Resource
    private MenuDao menuDao;

    @Override
    public List<Menu> getData() {
        return menuDao.getData();
    }
}
