package com.example.demo.system.scheduling.dao;


import com.example.demo.system.scheduling.entity.Schedule;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface SchedulingDao {
    @Select({"select * from schedule where id<#{id} and startOver=1"})
    List<Schedule> getData(int id);

    @Select({"select * from schedule where id<#{id}"})
    List<Schedule> getDatas(int id);
}
