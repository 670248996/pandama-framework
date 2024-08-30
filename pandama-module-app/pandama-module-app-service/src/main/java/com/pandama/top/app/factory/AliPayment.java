package com.pandama.top.app.factory;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("ali")
@Scope("prototype")
public class AliPayment implements Payment {

    @Override
    public void pay() {
        System.out.println("ali");
    }
}
