package com.example.demo.system.scheduling.config;

import com.example.demo.system.scheduling.dao.SchedulingDao;
import com.example.demo.system.scheduling.entity.Schedule;
import com.example.demo.system.scheduling.task.Task;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.SchedulingException;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.config.TriggerTask;
import org.springframework.scheduling.support.CronTrigger;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

@Configuration
@EnableScheduling
public class DefaultSchedulingConfigurer implements SchedulingConfigurer {
    private ScheduledTaskRegistrar taskRegistrar;
    private Set<ScheduledFuture<?>> scheduledFutures = new HashSet<>();
    private Map<String, ScheduledFuture<?>> taskFutures = new ConcurrentHashMap<>();
    @Lazy
    @Resource
    private SchedulingDao schedulingDao;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler(); //创建一个线程池调度器
        scheduler.initialize();
        scheduler.setPoolSize(3); //设置线程池容量
        scheduler.setThreadNamePrefix("scheduling-task-"); //线程名前缀
        scheduler.setAwaitTerminationSeconds(60);//等待时常
        scheduler.setWaitForTasksToCompleteOnShutdown(true); //当调度器shutdown被调用时等待当前被调度的任务完成
        scheduler.setRemoveOnCancelPolicy(true);//设置当任务被取消的同时从当前调度器移除的策略
        taskRegistrar.setTaskScheduler(scheduler); //设置任务注册器的调度器
        this.taskRegistrar = taskRegistrar;
    }

    /**
     * 添加任务
     */
    public void addTriggerTask(String taskId, TriggerTask triggerTask) {
        if (taskFutures.containsKey(taskId)) {
            throw new SchedulingException("the taskId[" + taskId + "] was added.");
        }
        TaskScheduler scheduler = taskRegistrar.getScheduler();
        ScheduledFuture<?> future = scheduler.schedule(triggerTask.getRunnable(), triggerTask.getTrigger());
        scheduledFutures.add(future);
        taskFutures.put(taskId, future);
    }

    /**
     * 取消任务
     */
    public void cancelTriggerTask(String taskId) {
        ScheduledFuture<?> future = taskFutures.get(taskId);
        if (future != null) {
            future.cancel(true);
        }
        taskFutures.remove(taskId);
        scheduledFutures.remove(future);
        System.out.println("运行定时器：" + this.taskIds());
    }


    /**
     * 重置任务
     */
    public void resetTriggerTask(String taskId, TriggerTask triggerTask) {
        cancelTriggerTask(taskId);
        addTriggerTask(taskId, triggerTask);
    }

    /**
     * 任务编号
     */
    public Set<String> taskIds() {
        return taskFutures.keySet();
    }

    /**
     * 是否存在任务
     */
    public boolean hasTask(String taskId) {
        return this.taskFutures.containsKey(taskId);
    }

    /**
     * 任务调度是否已经初始化完成
     */
    public boolean inited() {
        PageHelper.startPage(1, 2);
        List<Schedule> scheduleList = schedulingDao.getData(3);
        PageInfo<Schedule> pageInfo = new PageInfo<>(scheduleList);
        for (Schedule schedule : pageInfo.getList()) {
            this.addTriggerTask(String.valueOf(schedule.getId()),
                    new TriggerTask(
                            () -> {
                                try {
                                    ((Task) Class.forName(schedule.getClassPath()).getConstructor().newInstance()).run(); //执行定时任务
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            },
                            new CronTrigger(schedule.getCron())));
        }
        System.out.println("初始化定时器：" + this.taskIds());
        return this.taskRegistrar != null && this.taskRegistrar.getScheduler() != null;
    }

}
