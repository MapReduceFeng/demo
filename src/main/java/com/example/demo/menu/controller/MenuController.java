package com.example.demo.menu.controller;

import com.example.demo.menu.entity.MenuTreeNew;
import com.example.demo.menu.service.MenuService;
import com.example.demo.system.entity.Result;
import com.example.demo.system.security.WebFluxSecurityConfig;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@RestController
@RequestMapping("menu")
public class MenuController {
    @Resource
    private MenuService menuService;

    @PostMapping("getData")
    public Result getData() {
        MenuTreeNew menuTreeNew = new MenuTreeNew();
        menuTreeNew.menuList(menuService.getData());
        return Result.success(menuTreeNew.list);
    }

    @PostConstruct
    public void init() {
        WebFluxSecurityConfig.urlSkipList.add("/menu/**");//设置token检证}
    }
}
