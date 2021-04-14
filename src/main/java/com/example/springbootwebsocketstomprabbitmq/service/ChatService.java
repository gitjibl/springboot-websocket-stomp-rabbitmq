package com.example.springbootwebsocketstomprabbitmq.service;

import com.alibaba.fastjson.JSONObject;
import com.example.springbootwebsocketstomprabbitmq.model.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;

/**
 * @ProjectName: springboot-websocket-stomp-rabbitmq
 * @Package: com.example.springbootwebsocketstomprabbitmq.service
 * @ClassName: ChatService
 * @Author: jibl
 * @Description:
 * @Date: 2021/4/13 15:40
 * @Version: 1.0
 */
@Component
public class ChatService {

    @Autowired
    private SimpMessageSendingOperations simpMessageSendingOperations;

    public Boolean sendPublicMsg(String msg) {
        try {
            JSONObject msgJson = JSONObject.parseObject(msg);
            if (msgJson.getString("to").equals("ALL") && msgJson.getString("type").equals(ChatMessage.MessageType.CHAT.toString())){
                simpMessageSendingOperations.convertAndSend("/topic/public", msgJson);
            }else if (msgJson.getString("to").equals("ALL") && msgJson.getString("type").equals(ChatMessage.MessageType.JOIN.toString())) {
                simpMessageSendingOperations.convertAndSend("/topic/public", msgJson);
            }else if (msgJson.getString("to").equals("ALL") && msgJson.getString("type").equals(ChatMessage.MessageType.LEAVE.toString())) {
                simpMessageSendingOperations.convertAndSend("/topic/public", msgJson);
            }
        }catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public Boolean sendUserMsg(String msg) {
        try {
            JSONObject msgJson = JSONObject.parseObject(msg);
            if (msgJson.getString("type").equals(ChatMessage.MessageType.PRIVATE_CHAT.toString())){
                simpMessageSendingOperations.convertAndSendToUser(msgJson.getString("to"),"/topic/private", msgJson);
            }
        }catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
