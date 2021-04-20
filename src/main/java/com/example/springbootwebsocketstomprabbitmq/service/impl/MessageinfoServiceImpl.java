package com.example.springbootwebsocketstomprabbitmq.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.springbootwebsocketstomprabbitmq.entity.Messageinfo;
import com.example.springbootwebsocketstomprabbitmq.dao.MessageinfoMapper;
import com.example.springbootwebsocketstomprabbitmq.service.MessageinfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jibl
 * @since 2021-04-20
 */
@Service
public class MessageinfoServiceImpl extends ServiceImpl<MessageinfoMapper, Messageinfo> implements MessageinfoService {

    @Autowired
    private MessageinfoMapper messageinfoMapper;

    @Override
    public List<Messageinfo> getAllInfos(String username) {
        QueryWrapper<Messageinfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("USERTO","ALL");
        return messageinfoMapper.selectList(queryWrapper);
    }
}
