package com.example.demo.system.kafka.controller;

import org.springframework.web.bind.annotation.RestController;


@RestController
//@EnableBinding(value = {MyOutPut.class, MyInPut.class})
public class KafkaController {
  /*  @Resource
    private MyOutPut myOutPut;
    @Resource
    private KafkaTemplate<String, String> template;


//   @StreamListener(value = MyInPut.INPUT, condition = "headers['flag']=='a'")
//    public void listenKafak_a(@Payload User user) {
//        System.out.println("listenKafak:a:" + user);
////        redisUtil.lSet("kafka", user.toString());
//    }

//    @StreamListener(value = MyInPut.MYINPUT, condition = "headers['flag']=='b'")
    public void MyListenKafak_b(@Payload User user) {
        System.out.println("MyListenKafak:b:!!!" + user);
//        redisUtil.lSet("kafka", user.toString());
    }

//    @StreamListener(value = MyInPut.MYINPUT_C, condition = "headers['flag']=='c'")
//    public void MyListenKafak_c(@Payload User user) {
//        System.out.println("MyListenKafak_c c:" + user);
//    }

    @PostMapping("sentKafak")
    public Result sentKafak() {
        String string = UUID.randomUUID().toString();
        User user = null;
        switch (8081) {
            case 8081:
                user = new User(string, "b");
                break;
            case 8082:
                user = new User(string, "c");
                break;
            default:
                user = new User(string, "a");
        }
        System.out.println("!! "+user);
        Message ms = MessageBuilder.withPayload(user).setHeader("flag", user.getPass()).build();
        return Result.success(string + ": " + myOutPut.output().send(ms));
    }
    @PostConstruct
    public void init() {
        WebFluxSecurityConfig.strNonelist.add("/sentKafak");
        WebFluxSecurityConfig.strNonelist.add("/getConsumer");
    }
*/
}
