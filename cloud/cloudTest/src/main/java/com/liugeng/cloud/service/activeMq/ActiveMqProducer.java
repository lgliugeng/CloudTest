package com.liugeng.cloud.service.activeMq;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

import javax.jms.*;

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

    public void produceQueueMessage() throws Exception{
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
        MessageProducer producer =  session.createProducer(destination);
        for (int i = 0; i < 100; i++) {
            TextMessage message = session.createTextMessage("发送消息"+i);
            //发送消息
            producer.send(message);
            System.out.println("发送消息"+i);
        }
        //关闭连接
        connection.close();
    }

    @JmsListener(destination = "outName")
    public void listenQueue(String string){
        System.out.println("produceListen:"+string);
    }

}
