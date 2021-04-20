package com.example.springbootwebsocketstomprabbitmq.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ProjectName: springboot-websocket-stomp-rabbitmq
 * @Package: com.example.springbootwebsocketstomprabbitmq.controller
 * @ClassName: IndexController
 * @Author: jibl
 * @Description:
 * @Date: 2021/4/13 9:51
 * @Version: 1.0
 */
@Controller
public class IndexController {

    @RequestMapping("/")
    public String index(){
        return "pages/index";
    }
}
