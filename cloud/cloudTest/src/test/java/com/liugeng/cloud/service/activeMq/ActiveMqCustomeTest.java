package com.liugeng.cloud.service.activeMq;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ActiveMqCustomeTest {

    @Autowired
    private ActiveMqProducer activeMqProducer;

    @Test
    public void producerTest(){
        activeMqProducer.produceQueueMessage();;
    }

}