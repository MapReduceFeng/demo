package com.example.demo.test.service;

import com.example.demo.test.entity.TestEntity;
import java.util.List;
public interface TestService  {

	Integer add(TestEntity test);

	Integer del(TestEntity test); 

	Integer update(TestEntity test);

	List<TestEntity> select(TestEntity test);

}
