package com.example.demo.system.scheduling.controller;

import com.example.demo.system.entity.ReqPage;
import com.example.demo.system.entity.Result;
import com.example.demo.system.scheduling.config.DefaultSchedulingConfigurer;
import com.example.demo.system.scheduling.dao.SchedulingDao;
import com.example.demo.system.scheduling.entity.Schedule;
import com.example.demo.system.security.WebFluxSecurityConfig;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.scheduling.config.TriggerTask;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("scheduling")
public class SchedulingController {

    @Resource
    private DefaultSchedulingConfigurer defaultSchedulingConfigurer;
    @Resource
    private SchedulingDao schedulingDao;

    @PostMapping("add")
    public void add(@RequestBody Schedule schedule) throws InterruptedException {
        defaultSchedulingConfigurer.addTriggerTask(String.valueOf(schedule.getId()),
                new TriggerTask(
                        () -> {
                            System.out.println(Thread.currentThread().getName() + " add");
                        },
                        new CronTrigger("0/5 * * * * ? ")));
    }

    @PostMapping("reset")
    public void reset(@RequestBody Schedule schedule) throws InterruptedException {
        defaultSchedulingConfigurer.resetTriggerTask(String.valueOf(schedule.getId()),
                new TriggerTask(
                        () -> {
                            System.out.println(Thread.currentThread().getName() + " reset");
                        },
                        new CronTrigger("0/5 * * * * ? ")));
    }

    @PostMapping("delete")
    public void delete(@RequestBody Schedule schedule) {
        defaultSchedulingConfigurer.cancelTriggerTask(String.valueOf(schedule.getId()));
    }

    @PostMapping("getData")
    public Result getData(@RequestBody ReqPage page) {
        PageHelper.startPage(page.getPageCurrent(), page.getPageSize());
        List<Schedule> scheduleList = schedulingDao.getDatas(5);
        PageInfo<Schedule> pageInfo = new PageInfo<>(scheduleList);
        return Result.success(pageInfo);
    }

    @PostMapping("init")
    public void schedulingInit() {
        defaultSchedulingConfigurer.inited();
    }

    @PostConstruct
    public void init() {
        WebFluxSecurityConfig.urlSkipList.add("/scheduling/**");//设置token检证}
    }
}

