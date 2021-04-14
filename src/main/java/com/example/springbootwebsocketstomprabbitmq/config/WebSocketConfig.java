package com.example.springbootwebsocketstomprabbitmq.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * @ProjectName: springboot-websocket-stomp-rabbitmq
 * @Package: com.example.springbootwebsocketstomprabbitmq.config
 * @ClassName: WebSocketConfig
 * @Author: jibl
 * @Description:websocket stomp 配置
 * @Date: 2021/4/13 9:12
 * @Version: 1.0
 */
@Configuration
@EnableWebSocketMessageBroker //注解开启使用STOMP协议来传输基于代理(message broker),支持使用@MessageMapping
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Value("${spring.rabbitmq.virtual-host}")
    private String rabbitmq_virtual_host;
    @Value("${spring.rabbitmq.host}")
    private String rabbitmq_host;
    @Value("${spring.rabbitmq.username}")
    private String rabbitmq_username;
    @Value("${spring.rabbitmq.password}")
    private String rabbitmq_password;

    @Autowired
    GetHeaderParamInterceptor getHeaderParamInterceptor;

    /**
     * 注册stomp端点，主要是起到连接作用
     *
     * @param registry
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        //注册一个STOMP的endpoint,并指定使用SockJS协议
        registry.addEndpoint("/ws").setAllowedOrigins("*").withSockJS();
    }

    /**
     * 配置消息代理(Message Broker)
     *
     * @param registry
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        /*
         *  1. 配置一对一消息前缀， 客户端接收一对一消息需要配置的前缀 如“'/user/'+userid + '/message'”，
         *     是客户端订阅一对一消息的地址 stompClient.subscribe js方法调用的地址
         *  2. 使用@SendToUser发送私信的规则不是这个参数设定，在框架内部是用UserDestinationMessageHandler处理，
         *     而不是而不是 AnnotationMethodMessageHandler 或  SimpleBrokerMessageHandler
         *     or StompBrokerRelayMessageHandler，是在@SendToUser的URL前加“user+sessionId"组成
         */
        registry.setUserDestinationPrefix("/user");
        /*
         *  "/app" 为配置应用服务器的地址前缀，表示所有以/app 开头的客户端消息或请求
         *   都会路由到带有@MessageMapping 注解的方法中
         */
        registry.setApplicationDestinationPrefixes("/app");
        /*
         *  enableStompBrokerRelay 配置外部的STOMP服务，需要安装额外的支持 比如rabbitmq或activemq
         * 1. 配置代理域，可以配置多个，此段代码配置代理目的地的前缀为 /topic 或者 /queue
         *    我们就可以在配置的域上向客户端推送消息
         * 3. 可以通过 setRelayHost 配置代理监听的host,默认为localhost
         * 4. 可以通过 setRelayPort 配置代理监听的端口，默认为61613
         * 5. 可以通过 setClientLogin 和 setClientPasscode 配置账号和密码
         * 6. setxxx这种设置方法是可选的，根据业务需要自行配置，也可以使用默认配置
         */
//        registry.enableStompBrokerRelay("/topic", "/queue")
//                .setVirtualHost(rabbitmq_virtual_host)
//                .setRelayHost(rabbitmq_host)
//                .setClientLogin(rabbitmq_username)
//                .setClientPasscode(rabbitmq_password)
//                .setSystemLogin(rabbitmq_username)
//                .setSystemPasscode(rabbitmq_password)
//                .setSystemHeartbeatSendInterval(5000)
//                .setSystemHeartbeatReceiveInterval(4000);
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(getHeaderParamInterceptor);
    }
}
