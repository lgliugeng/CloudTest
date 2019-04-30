package com.liugeng.cloud.service.activeMq;

import com.liugeng.cloud.common.redis.RedisService;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import javax.jms.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class ActiveMqProducer {

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

    public void produceQueueMessage() throws Exception{
        ExecutorService pool = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 50; i++) {
            pool.submit(new produceThread(activeMQConnectionFactory,"queueName",i,redisService.getJedis()));
        }
        pool.shutdown();
    }

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
        try {
            //创建连接
            connection = activeMQConnectionFactory.createConnection();
            //连接开始
            connection.start();
            //创建会话
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            //创建队列
            Destination destination = session.createQueue(desName);
            //创建生产者
            MessageProducer producer =  session.createProducer(destination);
            TextMessage message = session.createTextMessage("添加商品"+i);
            //发送消息
            producer.send(message);
            //放入redis
            jedis.set(""+i,"商品"+i);
            //大打印
            System.out.println("添加商品"+ i + "成功");
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("商品"+i+"添加失败");
        }finally {
            if(connection != null){
                try {
                    //关闭连接
                    connection.close();
                }catch (Exception e){
                    e.printStackTrace();
                    System.out.println("连接关闭失败");
                }
            }
        }
    }
}
