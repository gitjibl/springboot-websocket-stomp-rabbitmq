package com.example.springbootwebsocketstomprabbitmq.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.springbootwebsocketstomprabbitmq.entity.Messageinfo;
import com.example.springbootwebsocketstomprabbitmq.service.MessageinfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jibl
 * @since 2021-04-20
 */
@RestController
@CrossOrigin
@RequestMapping("/messageinfo")
public class MessageinfoController {

    @Autowired
    private MessageinfoService messageinfoService;

    @RequestMapping("/getAllInfos")
    public List<Messageinfo> getAllInfos(String username){
        List<Messageinfo> list =  messageinfoService.getAllInfos(username);
        return list;
    }
}

