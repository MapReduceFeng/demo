package com.example.demo.rabbit.dao;


import com.example.demo.rabbit.entity.RabbitEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface RabbitDao {
    @Select({"select * from sys_rabbit"})
    List<RabbitEntity> getData(RabbitEntity rabbitEntity);

    @Select({"select * from sys_rabbit where status=#{status}"})
    List<RabbitEntity> getDataByStatus(RabbitEntity rabbitEntity);

    @Select({"select * from sys_rabbit where status=#{status} and id=#{id}"})
    RabbitEntity getDataByIdAndStatus(RabbitEntity rabbitEntity);

    @Select({"select * from sys_rabbit where id=#{id}"})
    RabbitEntity getDataById(RabbitEntity rabbitEntity);

    @Insert({"INSERT INTO sys_rabbit(queue_name,class_package,cron) VALUES (#{queueName},#{classPackage},#{cron})"})
    int addData(RabbitEntity rabbitEntity);

    @Update({"update sys_rabbit set queue_name=#{queueName},class_package=#{classPackage},cron=#{cron} where id=#{id}"})
    int updateData(RabbitEntity rabbitEntity);

    @Update({"update sys_rabbit set status=#{status} where id=#{id}"})
    int updateDataStatus(RabbitEntity rabbitEntity);

    @Select({"select * from sys_rabbit where queue_name=#{queueName}"})
    List<RabbitEntity> getDataByName(RabbitEntity rabbitEntity);

    List<RabbitEntity> selectData();

}
