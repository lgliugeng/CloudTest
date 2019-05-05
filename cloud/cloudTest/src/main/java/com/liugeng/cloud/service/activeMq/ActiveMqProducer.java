package com.liugeng.cloud.service.activeMq;

import com.liugeng.cloud.common.redis.RedisService;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEvent;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import javax.jms.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class ActiveMqProducer{

    @Value("${spring.activemq.broker-url}")
    private String url;

    @Value("${activemq.queuename}")
    private String queueName;

    @Value("${activemq.topicname}")
    private String topicName;


    @Autowired
    private ActiveMQConnectionFactory activeMQConnectionFactory;

    @Autowired
    private RedisService redisService;


    /**
    * 方法说明   模拟生产
    * @方法名    produceQueueMessage
    * @参数      []
    * @返回值    void
    * @异常
    * @创建时间  2019/5/5 10:14
    * @创建人    liugeng
    */
    public void produceQueueMessage(){
        ExecutorService pool = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 50; i++) {//生产
            pool.submit(new produceThread(activeMQConnectionFactory,"queueName",i,redisService.getJedis()));
        }
        pool.shutdown();
    }

    /**
    * 方法说明   activeMq监听
    * @方法名    listenQueue
    * @参数      [string]
    * @返回值    void
    * @异常
    * @创建时间  2019/5/5 10:13
    * @创建人    liugeng
    */
    @JmsListener(destination = "outName")
    public void listenQueue(String string){
        System.out.println("produceListen:"+string);
    }

}

class produceThread implements Runnable{

    private ActiveMQConnectionFactory activeMQConnectionFactory;

    private String desName;

    private int i;

    private Jedis jedis;

    public produceThread(ActiveMQConnectionFactory activeMQConnectionFactory,String desName,int i,Jedis jedis){
        this.activeMQConnectionFactory = activeMQConnectionFactory;
        this.desName = desName;
        this.i = i;
        this.jedis = jedis;
    }

    @Override
    public void run() {
        Connection connection = null;
        Session session = null;
        try {
            //创建连接
            connection = activeMQConnectionFactory.createConnection();
            //连接开始
            connection.start();
            //创建会话
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            //创建队列
            Destination destination = session.createQueue(desName);
            //创建生产者
            MessageProducer producer =  session.createProducer(destination);
            TextMessage message = session.createTextMessage(""+i);
            //发送消息
            producer.send(message);
            //放入redis
            jedis.set(""+i,"produce_"+i);
            //大打印
            System.out.println("添加商品"+ i + "成功");
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("商品"+i+"添加失败");
        }finally {
            try {
                if(null != session){
                    session.close();
                }
                if(null != connection){
                    //关闭连接
                    connection.close();
                }
            }catch (Exception e){
                e.printStackTrace();
                System.out.println("连接关闭失败");
            }
        }
    }
}
