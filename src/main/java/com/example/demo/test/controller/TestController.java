package com.example.demo.test.controller;
import com.example.demo.system.entity.Result;
import com.example.demo.system.security.WebFluxSecurityConfig;
import com.example.demo.test.entity.TestEntity;
import com.example.demo.test.service.TestService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
@RestController
@RequestMapping("test")////
public class TestController {

	@Resource
	private TestService testService;

	@PostMapping("add")
	public Result add(@RequestBody TestEntity test) {
		return Result.success(testService.add(test));
	}

	@PostMapping("del")
	public Result del(@RequestBody TestEntity test) {
		return Result.success(testService.del(test));
	}

	@PostMapping("update")
	public Result update(@RequestBody TestEntity test) {
		return Result.success(testService.update(test));
	}

	@PostMapping("select")
	public Result select(@RequestBody TestEntity test) {
		return Result.success(testService.select(test));
	}

	@PostConstruct 
	public void init() {
		WebFluxSecurityConfig.urlSkipList.add("/test/**");
}
}
