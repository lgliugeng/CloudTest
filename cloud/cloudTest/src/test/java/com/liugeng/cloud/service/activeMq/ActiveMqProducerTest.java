package com.liugeng.cloud.service.activeMq;

import org.apache.activemq.command.ActiveMQQueue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ActiveMqProducerTest {

    @Autowired
    private ActiveMqProducer activeMqProducer;

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Test
    public void testActiveMqProducer(){
        try {
            for (int i = 0; i < 50; i++) {
                jmsMessagingTemplate.convertAndSend(new ActiveMQQueue("queueName"),"发送消息"+i);
                System.out.println("发送消息"+i);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}