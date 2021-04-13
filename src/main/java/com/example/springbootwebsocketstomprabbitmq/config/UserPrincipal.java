package com.example.springbootwebsocketstomprabbitmq.config;

import java.security.Principal;

/**
 * @Author : JCccc
 * @CreateTime : 2020/8/26
 * @Description :
 **/
public class UserPrincipal implements Principal {

    private final String name;

    public UserPrincipal(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
