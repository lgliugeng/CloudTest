package com.liugeng.cloud.service.activeMq;

import com.liugeng.cloud.common.redis.RedisService;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.context.support.WebApplicationContextUtils;
import redis.clients.jedis.Jedis;

import javax.jms.*;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@Component
public class ActiveMqCustome {

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
    * 方法说明   模拟消费
    * @方法名    customeQueueMessage
    * @参数      []
    * @返回值    void
    * @异常
    * @创建时间  2019/5/5 10:13
    * @创建人    liugeng
    */
    ExecutorService pool = Executors.newFixedThreadPool(5);
    public void customeQueueMessage() {
        //ExecutorService pool = Executors.newFixedThreadPool(5);
        /*for (int i = 0; i < 50; i++) {//消费
            pool.submit(new customeThread(activeMQConnectionFactory,"queueName",i,redisService.getJedis()));
        }*/
    }

    /**
    * 方法说明   activeMq监听
    * @方法名    listenQueue
    * @参数      [string]
    * @返回值    java.lang.String
    * @异常
    * @创建时间  2019/5/5 10:13
    * @创建人    liugeng
    */
    @JmsListener(destination = "queueName",containerFactory = "jmsListenerContainerQueue")
    @SendTo("outName")
    public String listenQueue(String string){
        System.out.println("customeListen:"+string);
        pool.submit(new customeThread(activeMQConnectionFactory,"queueName",string,redisService.getJedis()));
        return "收到消息:"+string + "over!!!";
    }

}

class customeThread implements Runnable{

    private ActiveMQConnectionFactory activeMQConnectionFactory;

    private String desName;

    private String i;

    private Jedis jedis;

    public customeThread(ActiveMQConnectionFactory activeMQConnectionFactory,String desName,String i,Jedis jedis){
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
            MessageConsumer consumer =  session.createConsumer(destination);
            //接收消息
            synchronized (this){
                if(null != i && !"".equals(i)){
                    String val = "produce_"+i+"_custome";
                    jedis.set(""+i,new String(val.getBytes("UTF-8")));
                    System.out.println("商品"+i+"被消费了！");
                }else{
                    System.out.println("商品"+i+"被消费了,但是没有被记录！");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("商品"+i+"消费失败");
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
