package com.example.demo.rabbit.service.impl;


import com.example.demo.rabbit.config.RabbitConfig;
import com.example.demo.rabbit.dao.RabbitDao;
import com.example.demo.rabbit.entity.RabbitEntity;
import com.example.demo.rabbit.service.RabbitService;
import com.example.demo.rabbit.task.RabbitTask;
import com.example.demo.rabbit.utils.JacksonUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

//@Service(version = "${dubbo.version}")
@Service
public class RabbitServiceImpl implements RabbitService {


    @Resource
    RabbitAdmin rabbitAdmin;
    @Resource
    RabbitDao rabbitDao;
    final JacksonUtils jacksonUtils = JacksonUtils.create();


    public void sendMessage(RabbitEntity rabbitEntity) {

        rabbitAdmin.deleteQueue(rabbitEntity.getQueueName());//删除之前的队列

        rabbitAdmin.declareQueue(new Queue(RabbitConfig.DLX_QUEUENAME, true, false, false, null));//定义接收死信列队转发的队列
        rabbitAdmin.declareExchange(new TopicExchange(RabbitConfig.DLX_EXCHANGENAME, true, false));//定义接收死信列队转发的交换机
        rabbitAdmin.declareBinding(new Binding(RabbitConfig.DLX_QUEUENAME, Binding.DestinationType.QUEUE, RabbitConfig.DLX_EXCHANGENAME, RabbitConfig.ROUTINGKEY, null));//邦定列队与交换机

        rabbitAdmin.declareQueue(new Queue(rabbitEntity.getQueueName(), true, false, false, RabbitConfig.getNextTime(rabbitEntity.getCron())));//定义死信队列
        rabbitAdmin.declareExchange(new TopicExchange(RabbitConfig.EXCHANGENAME, true, false));//定义死信交换机
        rabbitAdmin.declareBinding(new Binding(rabbitEntity.getQueueName(), Binding.DestinationType.QUEUE, RabbitConfig.EXCHANGENAME, rabbitEntity.getQueueName() + rabbitEntity.getId(), null));//邦定列队与交换机

        rabbitAdmin.getRabbitTemplate().convertAndSend(RabbitConfig.EXCHANGENAME, rabbitEntity.getQueueName() + rabbitEntity.getId(), jacksonUtils.objectToJson(rabbitEntity));//给交换器发送消息

    }


    //@RabbitListener(queues = RabbitConfig.DLX_QUEUENAME)
    public void getMessage(String message) {
        RabbitEntity rabbitEntity = null;
        try {
            rabbitEntity = jacksonUtils.jsonToObject(message, RabbitEntity.class);
            ((RabbitTask) Class.forName(rabbitEntity.getClassPackage()).getConstructor().newInstance()).run();//调用定时任务逻辑
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            rabbitEntity.setStatus("1");
            rabbitEntity = rabbitDao.getDataByIdAndStatus(rabbitEntity);
            if (null != rabbitEntity) {
                sendMessage(rabbitEntity);//再次提交任务
            }
        }
    }

    public HashMap<String, Object> getData(RabbitEntity rabbitEntity) {
        PageHelper.startPage(rabbitEntity.getPageNo(), rabbitEntity.getPageSize());
        List<RabbitEntity> list = rabbitDao.getData(rabbitEntity);
        PageInfo<RabbitEntity> pageInfo = new PageInfo<>(list);
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("list", pageInfo.getList());
        hashMap.put("count", pageInfo.getTotal());
        return hashMap;
    }


    public List<RabbitEntity> getDataByStatus(RabbitEntity rabbitEntity) {
        return rabbitDao.getDataByStatus(rabbitEntity);
    }


    public RabbitEntity getDataById(RabbitEntity rabbitEntity) {
        return rabbitDao.getDataById(rabbitEntity);
    }


    public int addData(RabbitEntity rabbitEntity) {
        return rabbitDao.addData(rabbitEntity);
    }


    public int updateDataStatus(RabbitEntity rabbitEntity) {
        if (rabbitEntity.getStatus().equals("1")) {
            sendMessage(rabbitEntity);
        }
        return rabbitDao.updateDataStatus(rabbitEntity);
    }


    public int updateData(RabbitEntity rabbitEntity) {
        return rabbitDao.updateData(rabbitEntity);
    }


    public List<RabbitEntity> getDataByName(RabbitEntity rabbitEntity) {
        return rabbitDao.getDataByName(rabbitEntity);
    }


   // @PostConstruct
    public void init() {
        rabbitAdmin.purgeQueue(RabbitConfig.DLX_QUEUENAME);
        RabbitEntity rabbit = new RabbitEntity();
        rabbit.setStatus("1");//启用状态
//        rabbitDao.getDataByStatus(rabbit).forEach(rabbitEntity -> sendMessage(rabbitEntity));
        System.out.println(" +++ " + rabbitDao.selectData());
    }

}
