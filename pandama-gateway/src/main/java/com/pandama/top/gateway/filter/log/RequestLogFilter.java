package com.pandama.top.gateway.filter.log;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashSet;

/**
 * @description: 请求日志全局过滤器 输出请求日志
 * @author: 王强
 * @dateTime: 2022-10-25 14:38:50
 */
@Slf4j
@Component
public class RequestLogFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        URI uri = request.getURI();
        String path = request.getPath().pathWithinApplication().value();
        String method = request.getMethodValue();
        HttpHeaders headers = request.getHeaders();
        String requestUrl = this.getRequestUrl(exchange);
        log.info("---> {} {}", method, requestUrl);
        if (HttpMethod.POST.name().equals(method)) {
            return DataBufferUtils.join(exchange.getRequest().getBody()).flatMap(dataBuffer -> {
                byte[] bytes = new byte[dataBuffer.readableByteCount()];
                dataBuffer.read(bytes);
                String bodyString = new String(bytes, StandardCharsets.UTF_8);
                log.info("---> body: {}", bodyString);
                exchange.getAttributes().put("POST_BODY", bodyString);
                DataBufferUtils.release(dataBuffer);
                Flux<DataBuffer> cachedFlux = Flux.defer(() -> {
                    DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
                    return Mono.just(buffer);
                });
                ServerHttpRequest mutatedRequest = new ServerHttpRequestDecorator(request) {
                    @Override
                    public Flux<DataBuffer> getBody() {
                        return cachedFlux;
                    }
                };
                return chain.filter(exchange.mutate().request(mutatedRequest).build());
            });
        } else if (HttpMethod.GET.name().equals(method)) {
            MultiValueMap<String, String> queryParams = request.getQueryParams();
            log.info("---> params: {}", queryParams);
            return chain.filter(exchange);
        }
        return chain.filter(exchange);
    }

    private String getRequestUrl(ServerWebExchange exchange) {
        ServerHttpRequest req = exchange.getRequest();
        LinkedHashSet<URI> uris = exchange.getRequiredAttribute(ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR);
        URI requestUri = uris.stream().findFirst().orElse(req.getURI());
        return requestUri.toString();
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }

}

