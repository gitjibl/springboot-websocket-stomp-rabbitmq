package com.example.springbootwebsocketstomprabbitmq.service;

import com.alibaba.fastjson.JSONObject;
import com.example.springbootwebsocketstomprabbitmq.dao.MessageinfoMapper;
import com.example.springbootwebsocketstomprabbitmq.entity.Messageinfo;
import com.example.springbootwebsocketstomprabbitmq.model.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;

import java.util.Date;

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
    private MessageinfoMapper messageinfoMapper;

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
            if(msgJson.getString("type").equals(ChatMessage.MessageType.CHAT.toString())){
                messageinfoMapper.insert(JsontoMessageInfo(msgJson));
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
                messageinfoMapper.insert(JsontoMessageInfo(msgJson));
            }
        }catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public Messageinfo JsontoMessageInfo(JSONObject msgJson){
        Messageinfo messageinfo = new Messageinfo();
        messageinfo.setUserfrom(msgJson.get("sender").toString());
        messageinfo.setUserto(msgJson.get("to").toString());
        messageinfo.setContent(msgJson.get("content").toString());
        messageinfo.setCreatetime(new Date());
        return messageinfo;
    }

}
