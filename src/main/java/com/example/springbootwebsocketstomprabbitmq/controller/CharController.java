package com.example.springbootwebsocketstomprabbitmq.controller;

import com.example.springbootwebsocketstomprabbitmq.model.ChatMessage;
import com.example.springbootwebsocketstomprabbitmq.utils.JsonUtil;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @ProjectName: springboot-websocket-stomp-rabbitmq
 * @Package: com.example.springbootwebsocketstomprabbitmq.controller
 * @ClassName: CharController
 * @Author: jibl
 * @Description:
 * @Date: 2021/4/13 15:01
 * @Version: 1.0
 */
@RestController
public class CharController {


    @Autowired
    private RabbitTemplate rabbitTemplate;

    @MessageMapping("/chat.sendMessage")
    public void sendMessageAlone(@Payload ChatMessage chatMessage, Principal principal) {
        try {
            System.out.println("---------------消息发送---------");
            String name = principal.getName();
            chatMessage.setSender(name);
            rabbitTemplate.convertAndSend("topicExchange", "topic.public", JsonUtil.parseObjToJson(chatMessage));
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
