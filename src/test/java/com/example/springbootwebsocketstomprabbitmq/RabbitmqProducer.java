package com.example.springbootwebsocketstomprabbitmq;

import com.example.springbootwebsocketstomprabbitmq.model.ChatMessage;
import com.example.springbootwebsocketstomprabbitmq.utils.JsonUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @ProjectName: springboot-websocket-stomp-rabbitmq
 * @Package: com.example.springbootwebsocketstomprabbitmq
 * @ClassName: RabbitmqProducer
 * @Author: jibl
 * @Description:
 * @Date: 2021/4/13 12:01
 * @Version: 1.0
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class RabbitmqProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void hello() {
        System.out.println("hello!");
    }

    @Test
    public void say(){
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setSender("jibl");
        chatMessage.setContent("这是一条测试消息！");
        chatMessage.setTo("ALL");
        chatMessage.setType(ChatMessage.MessageType.CHAT);
        rabbitTemplate.convertAndSend("topicExchange","topic.public", JsonUtil.parseObjToJson(chatMessage));
    }
}
