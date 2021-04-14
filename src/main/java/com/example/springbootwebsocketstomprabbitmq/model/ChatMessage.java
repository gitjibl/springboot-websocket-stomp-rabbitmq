package com.example.springbootwebsocketstomprabbitmq.model;

import java.util.Map;

/**
 * @ProjectName: springboot-websocket-stomp-rabbitmq
 * @Package: com.example.springbootwebsocketstomprabbitmq.model
 * @ClassName: ChatMessage
 * @Author: jibl
 * @Description:
 * @Date: 2021/4/13 15:03
 * @Version: 1.0
 */
public class ChatMessage {
    private MessageType type;
    private String content;
    private String sender;
    private String to;
    private Map<String,Object> onlineUsers;

    public enum MessageType {
        CHAT,
        JOIN,
        LEAVE,
        PRIVATE_CHAT
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Map<String, Object> getOnlineUsers() {
        return onlineUsers;
    }

    public void setOnlineUsers(Map<String, Object> onlineUsers) {
        this.onlineUsers = onlineUsers;
    }

    @Override
    public String toString() {
        return "ChatMessage{" +
                "type=" + type +
                ", content='" + content + '\'' +
                ", sender='" + sender + '\'' +
                ", to='" + to + '\'' +
                ", onlineUsers=" + onlineUsers +
                '}';
    }
}

