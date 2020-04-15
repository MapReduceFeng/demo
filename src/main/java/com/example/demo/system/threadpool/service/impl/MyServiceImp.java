package com.example.demo.system.threadpool.service.impl;


import com.example.demo.system.annotation.RedisDB;
import com.example.demo.system.entity.RedisData;
import com.example.demo.system.entity.RedisSource;
import com.example.demo.system.redis.RedisUtil;
import com.example.demo.system.threadpool.service.MyService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Random;

@Service
public class MyServiceImp implements MyService {


    @Async("myExecutors")
    public void test(int i) {
        try {
            Thread.sleep(new Random().nextInt(1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " :" + i);
    }


   /* @Autowired
    private testMapper testMappers;
    private List<String> list = new ArrayList<>();
    private String id;
    private ArrayBlockingQueue al = new ArrayBlockingQueue(100);*/


  /*  @Override
    @Async("myExecutors")
    public void testTest(ArrayBlockingQueue token, IdEntity i) {
        while (token.size() > 0) {
            try {
                    synchronized (this) {
                        if (i.getId()<100) {
                        System.out.println(Thread.currentThread().getName() + " ===" + token.poll() + " " + i.getId());
                        i.Ids();
                        Thread.sleep(1);
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }*/
        /*aaa b = new aaa(this.list);
        Thread b1 = new Thread(b);
        Thread b2 = new Thread(b);
        Thread b3 = new Thread(b);
        b1.start();
        b2.start();
        b3.start();*/


    //  }

   /* public ArrayBlockingQueue getAl() {
        return al;
    }

    public void setAl(ArrayBlockingQueue al) {
        this.al = al;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }*/

   /* @PostConstruct
    @Override
    public void b() {
        String Str = UUID.randomUUID().toString();
        for (int i = 0; i < 100; i++) {
            list.add(i + "");
            // al.add(Str);
        }
    }

    @Override
    public Object sql() {
        testExample a = new testExample();
        a.createCriteria().andIdNotEqualTo(1000);
        PageHelper.startPage(2, 2);
        List<test> list = testMappers.selectByExample(a);
        PageInfo<test> pageInfo = new PageInfo<>(list);
        System.out.println(pageInfo.toString());
        return pageInfo.getList();
    }
*/
   /* @Override
    @Async("myExecutors")
    public void bb() {
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i) + "  " + Thread.currentThread().getName());
            try {
                Thread.sleep(1000);
                this.setId(i + "");
                System.out.println(this.getId());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }*/

   /* @Override
    public void test1() {
        try {
            System.out.println(Thread.currentThread().getName());
            System.out.println("111111111111");
            Thread.sleep(2000);
            System.out.println("2222222222222");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    Semaphore semaphore = new Semaphore(3);

    @Async("myExecutors")
    public String test() {
        try {
            semaphore.acquire();
            System.out.println(Thread.currentThread().getName() + "==" + (3 - semaphore.availablePermits()));
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        semaphore.release();
        return "ok";
    }*/

/*    private void a() {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3);
        cyclicBarrier.getNumberWaiting();
        try {
            cyclicBarrier.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
        ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
        reentrantReadWriteLock.readLock().lock();
        reentrantReadWriteLock.readLock().unlock();
        reentrantReadWriteLock.writeLock().lock();
        reentrantReadWriteLock.writeLock().unlock();
        ReentrantLock lock = new ReentrantLock();
        Condition condition = lock.newCondition();

        Exchanger<String> stringExchanger = new Exchanger<>();
        try {
            String a = stringExchanger.exchange("0000");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        CountDownLatch countDownLatch = new CountDownLatch(3);
        try {
            countDownLatch.countDown();
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            condition.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        condition.signal();

    }*/


}

/*class aaa implements Runnable {
    int i = 0;
    private List list;

    public aaa(List<String> list) {
        this.list = list;
    }

    @Override
    public void run() {
        while (i <= 100) {
            synchronized (this) {
                try {
                    i++;
                    if (i < 100) {
                        System.out.println(Thread.currentThread().getName() + " ===" + list.get(i));
                    }
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}*/
