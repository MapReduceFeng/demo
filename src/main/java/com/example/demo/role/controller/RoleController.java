package com.example.demo.role.controller;

import com.example.demo.role.service.RoleService;
import com.example.demo.system.entity.Result;
import com.example.demo.system.security.WebFluxSecurityConfig;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@RestController
@RequestMapping("role")
public class RoleController {

    @Resource
    private RoleService roleService;

    @PostMapping("getData")
    public Result getData() {
        return Result.success(roleService.getData());
    }

    @PostConstruct
    public void init() {
        WebFluxSecurityConfig.urlSkipList.add("/role/**");//设置token检证}
    }
}
