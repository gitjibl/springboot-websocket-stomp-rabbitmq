package com.example.springbootwebsocketstomprabbitmq.service;

import com.example.springbootwebsocketstomprabbitmq.entity.Messageinfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jibl
 * @since 2021-04-20
 */
public interface MessageinfoService extends IService<Messageinfo> {

    List<Messageinfo> getAllInfos(String username);
}
