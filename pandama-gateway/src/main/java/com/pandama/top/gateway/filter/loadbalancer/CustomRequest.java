package com.pandama.top.gateway.filter.loadbalancer;

import org.springframework.cloud.client.loadbalancer.DefaultRequest;
import org.springframework.web.server.ServerWebExchange;

/**
 * 自定义请求
 *
 * @author 王强
 * @date 2024-05-18 10:12:33
 */
public class CustomRequest extends DefaultRequest<ServerWebExchange> {

    public CustomRequest(ServerWebExchange context) {
        super(context);
    }
}
