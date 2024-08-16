package com.pandama.top.app.factory;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("wechat")
@Scope("prototype")
public class WechatPayment implements Payment {

    @Override
    public void pay() {
        System.out.println("wechat");
    }
}
