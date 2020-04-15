package com.example.demo.user.controller;

import com.example.demo.system.entity.Result;
import com.example.demo.system.security.WebFluxSecurityConfig;
import com.example.demo.system.utils.ExcelUtils;
import com.example.demo.user.entity.User;
import com.example.demo.user.service.UserService;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

@RestController
@RequestMapping("user")
public class UserController {
    @Resource
    private UserService userService;

    @PostMapping("getData")
    public Result getData() {
        return Result.success(userService.getData());
    }

    @PostMapping("updateData")
    public Result updateData(@RequestBody User user) {
        return Result.success(userService.updateData(user));
    }

    @PostMapping("addData")
    public Result addData(@RequestBody User user) {
        //  user.setId(KeyGeneratorUtils.generateKey());
        return Result.success(userService.addData(user));
    }

    @PostConstruct
    public void init() {
        WebFluxSecurityConfig.urlSkipList.add("/user/**");//设置token检证}
    }
    @GetMapping("expr")
    public ResponseEntity<byte[]> exportReport() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();//将Excel写到ByteArrayOutputStream中;
        try {
            String title = "";
            String httpHeaders = "";
            ArrayList<String> list = new ArrayList<>();
            Workbook workbook = ExcelUtils.createExcel(title,httpHeaders,list);
            workbook.write(baos);
            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Disposition", "attachment;filename=test.xls");
        return new ResponseEntity<byte[]>(baos.toByteArray(), httpHeaders, HttpStatus.CREATED);
    }

}
