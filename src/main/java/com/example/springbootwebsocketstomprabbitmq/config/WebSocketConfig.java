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
        /*//点对点应配置一个/user消息代理，广播式应配置一个/topic消息代理,群发（mass），单独聊天（alone）
        registry.enableSimpleBroker("/topic", "/user", "/mass", "/alone");*/
        // 点对点使用的前缀 无需配置 默认/user
        registry.setUserDestinationPrefix("/user");
        //所有目的地以“/app”开头的，全局(客户端)使用的消息前缀,都会路由到@MessageMapping 注解方法中，而不会发布到代理队列或主题中
        registry.setApplicationDestinationPrefixes("/app");
        //使用RabbitMQ做为消息代理，替换默认的Simple Broker
        registry.enableStompBrokerRelay("/topic", "/queue")
                .setVirtualHost(rabbitmq_virtual_host)
                .setRelayHost(rabbitmq_host)
                .setClientLogin(rabbitmq_username)
                .setClientPasscode(rabbitmq_password)
                .setSystemLogin(rabbitmq_username)
                .setSystemPasscode(rabbitmq_password)
                .setSystemHeartbeatSendInterval(5000)
                .setSystemHeartbeatReceiveInterval(4000);
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(getHeaderParamInterceptor);
    }
}
