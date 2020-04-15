package com.example.demo.test.dao;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Select;
import java.util.List;
import com.example.demo.test.entity.TestEntity;

public interface TestDao {

	Integer add(TestEntity test);

	Integer del(TestEntity test); 

	Integer update(TestEntity test);

	List<TestEntity> select(TestEntity test);

}
