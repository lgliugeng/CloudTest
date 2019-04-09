package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TestService {

    @Autowired
    RestTemplate restTemplate;

    public String testService(String name){
        return restTemplate.getForObject("http://server-a/serverA?info=1111",String.class);
    }
}
