package com.liugeng.cloud.common.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;

import javax.jms.Queue;
import javax.jms.Topic;

@Configuration
@EnableAutoConfiguration
public class ActiveConfid {

    @Bean
    public Queue queue(){
        Queue queue = new ActiveMQQueue();
        return queue;
    }

    @Bean
    public Topic topic(){
        Topic topic = new ActiveMQTopic();
        return topic;
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.activemq")
    public ActiveMQConnectionFactory getActiveMqConnectionFactory(){
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory();
        return activeMQConnectionFactory;
    }

    @Bean
    public JmsListenerContainerFactory<?>  jmsListenerContainerQueue(){
        DefaultJmsListenerContainerFactory jmsListenerContainerFactory = new DefaultJmsListenerContainerFactory();
        jmsListenerContainerFactory.setConnectionFactory(getActiveMqConnectionFactory());
        return jmsListenerContainerFactory;
    }

    @Bean
    public JmsListenerContainerFactory<?> jmsListenerContainerTopic(){
        DefaultJmsListenerContainerFactory jmsListenerContainerFactory = new DefaultJmsListenerContainerFactory();
        //设置为发布订阅方式, 默认情况下使用的生产消费者方式
        jmsListenerContainerFactory.setPubSubDomain(true);
        jmsListenerContainerFactory.setConnectionFactory(getActiveMqConnectionFactory());
        return jmsListenerContainerFactory;
    }
}
