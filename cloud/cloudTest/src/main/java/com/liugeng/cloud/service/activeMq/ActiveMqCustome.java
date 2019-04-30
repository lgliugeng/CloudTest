package com.liugeng.cloud.service.activeMq;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import javax.jms.*;

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

    public void customeQueueMessage() throws Exception{
        //ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(url);
        //创建连接
        Connection connection = activeMQConnectionFactory.createConnection();
        //连接开始
        connection.start();
        //创建会话
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //创建队列
        Destination destination = session.createQueue(queueName);
        //创建生产者
        MessageConsumer consumer =  session.createConsumer(destination);
        //接收消息
        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                try {
                    TextMessage textMessage = (TextMessage)message;
                    System.out.println("获得："+textMessage.getText());
                }catch (Exception e){

                }
            }
        });
        //关闭连接
        connection.close();
    }
    @JmsListener(destination = "queueName",containerFactory = "jmsListenerContainerQueue")
    @SendTo("outName")
    public String listenQueue(String string){
        System.out.println("customeListen:"+string);
        return "收到消息："+ string + "over!!!";
    }

}

class customeThread implements Runnable{

    private String string;

    private Jedis jedis;

    public customeThread(String string){
        this.string = string;
    }

    @Override
    public void run() {
    }


}
