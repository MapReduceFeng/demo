package com.example.demo.system.security;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
@RequestMapping("security")
public class SecurityController {
    @PostConstruct
    public void init() {
        WebFluxSecurityConfig.urlSkipList.add("/security/**");//跳过检证}
    }

    @PostMapping("login")
    public String login() {
        return "login";
    }

    @PostMapping("error")
    public String error() {
        return "error";
    }

    @PostMapping("test")
    public String test() {
        return "test";
    }

}
