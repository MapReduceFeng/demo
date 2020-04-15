package com.example.demo.test.service.impl;
import javax.annotation.Resource;
import java.util.List;
import org.springframework.stereotype.Service;
import com.example.demo.test.service.TestService;
import com.example.demo.test.entity.TestEntity;
import com.example.demo.test.dao.TestDao;
@Service
public class TestServiceImpl implements TestService {

	@Resource
	private TestDao testDao;

	public Integer add(TestEntity test) {
		return testDao.add(test);
	}

	public Integer del(TestEntity test) {
		return testDao.del(test);
	}

	public Integer update(TestEntity test) {
		return testDao.update(test);
	}

	public List<TestEntity> select(TestEntity test) {
		return testDao.select(test);
	}

}
