package com.example.springbootwebsocketstomprabbitmq;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.*.dao")
public class SpringbootWebsocketStompRabbitmqApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootWebsocketStompRabbitmqApplication.class, args);
    }

}
