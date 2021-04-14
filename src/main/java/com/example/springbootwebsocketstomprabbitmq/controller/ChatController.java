package com.example.springbootwebsocketstomprabbitmq.controller;

import com.example.springbootwebsocketstomprabbitmq.config.UserPrincipal;
import com.example.springbootwebsocketstomprabbitmq.model.ChatMessage;
import com.example.springbootwebsocketstomprabbitmq.utils.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.OnClose;
import java.security.Principal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
public class ChatController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChatController.class);
    private static Map<String, Object> clients = new ConcurrentHashMap<String, Object>();

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @MessageMapping("/chat.sendMessage")
    public void sendMessage(@Payload ChatMessage chatMessage, Principal principal) {
        try {
            System.out.println("---------------群发消息---------");
            String name = principal.getName();
            chatMessage.setSender(name);
            rabbitTemplate.convertAndSend("topicExchange", "topic.public", JsonUtil.parseObjToJson(chatMessage));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @MessageMapping("/chat.sendMessageToUser")
    public void sendMessageToUser(@Payload ChatMessage chatMessage, Principal principal) {
        try {
            System.out.println("---------------单发消息---------");
            String name = principal.getName();
            chatMessage.setSender(name);
            rabbitTemplate.convertAndSend("topicExchange", "topic.user", JsonUtil.parseObjToJson(chatMessage));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @MessageMapping("/chat.addUser")
    public void addUser(@Payload ChatMessage chatMessage) {
        LOGGER.info("有用户加入到了websocket 消息室:" + chatMessage.getSender());
        try {
            clients.put(chatMessage.getSender(), chatMessage.getSender());
            chatMessage.setOnlineUsers(clients);
            rabbitTemplate.convertAndSend("topicExchange", "topic.public", JsonUtil.parseObjToJson(chatMessage));

        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    @MessageMapping("/chat.deleteUser")
    public void deleteUser(@Payload ChatMessage chatMessage) {
        LOGGER.info("有用户离开websocket 消息室:" + chatMessage.getSender());
        try {
            clients.remove(chatMessage.getSender());
            chatMessage.setOnlineUsers(clients);
            rabbitTemplate.convertAndSend("topicExchange", "topic.public", JsonUtil.parseObjToJson(chatMessage));

        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

//    @ResponseBody
//    @GetMapping("/sendToOne")
//    public void sendToOne(@RequestParam("uid") String uid, @RequestParam("content") String content ){
//
//        ChatMessage chatMessage=new ChatMessage();
//        chatMessage.setType(ChatMessage.MessageType.CHAT);
//        chatMessage.setContent(content);
//        chatMessage.setTo(uid);
//        chatMessage.setSender("系统消息");
//        rabbitTemplate.convertAndSend("topicExchange","topic.user", JsonUtil.parseObjToJson(chatMessage));
//    }
}
