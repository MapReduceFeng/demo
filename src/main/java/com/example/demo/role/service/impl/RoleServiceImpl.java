package com.example.demo.role.service.impl;

import com.example.demo.role.dao.RoleDao;
import com.example.demo.role.entity.Role;
import com.example.demo.role.service.RoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Resource
    private RoleDao roleDao;

    @Override
    public List<Role> getData() {
        return roleDao.getData();
    }

}
