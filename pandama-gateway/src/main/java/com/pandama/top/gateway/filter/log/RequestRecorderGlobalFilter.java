package com.pandama.top.gateway.filter.log;


import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * 请求日志记录全局过滤器
 *
 * @author 王强
 * @date 2023-07-08 15:39:05
 */
@Slf4j
@Component
public class RequestRecorderGlobalFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest originalRequest = exchange.getRequest();
        URI originalRequestUrl = originalRequest.getURI();
        // 只记录http的请求
        String scheme = originalRequestUrl.getScheme();
        if ((!"http".equals(scheme) && !"https".equals(scheme))) {
            return chain.filter(exchange);
        }
        //这是我要打印的log-StringBuilder
        StringBuilder logBuilder = new StringBuilder();
        // 返回解码
        RecorderServerHttpResponseDecorator response = new RecorderServerHttpResponseDecorator(exchange.getResponse());
        //请求解码
        RecorderServerHttpRequestDecorator recorderServerHttpRequestDecorator = new RecorderServerHttpRequestDecorator(exchange.getRequest());
        //增加过滤拦截吧
        ServerWebExchange ex = exchange.mutate()
                .request(recorderServerHttpRequestDecorator)
                .response(response)
                .build();
        //  观察者模式 打印一下请求log
        response.beforeCommit(() -> Mono.defer(() -> printLog(logBuilder, response)));
        return recorderOriginalRequest(logBuilder, ex)
                .then(chain.filter(ex))
                .then();
    }

    private Mono<Void> recorderOriginalRequest(StringBuilder logBuffer, ServerWebExchange exchange) {
        logBuffer.append(System.currentTimeMillis()).append("------------");
        ServerHttpRequest request = exchange.getRequest();
        return recorderRequest(request, logBuffer.append("\n原始请求：\n"));
    }

    /**
     * 记录原始请求逻辑
     *
     * @param request   请求
     * @param logBuffer 日志缓冲区
     * @return reactor.core.publisher.Mono<java.lang.Void>
     * @author 王强
     */
    private Mono<Void> recorderRequest(ServerHttpRequest request, StringBuilder logBuffer) {
        URI uri = request.getURI();
        HttpMethod method = request.getMethod();
        HttpHeaders headers = request.getHeaders();
        logBuffer.append(method).append(' ').append(uri).append('\n');
        logBuffer.append("------------请求头------------\n");
        headers.forEach((name, values) -> {
            values.forEach(value -> {
                logBuffer.append(name).append(":").append(value).append('\n');
            });
        });
        Charset bodyCharset = null;
        if (hasBody(method)) {
            long length = headers.getContentLength();
            if (length <= 0) {
                logBuffer.append("------------无body------------\n");
            } else {
                logBuffer.append("------------body 长度:").append(length).append(" contentType:");
                MediaType contentType = headers.getContentType();
                if (contentType == null) {
                    logBuffer.append("null，不记录body------------\n");
                } else if (!shouldRecordBody(contentType)) {
                    logBuffer.append(contentType).append("，不记录body------------\n");
                } else {
                    bodyCharset = getMediaTypeCharset(contentType);
                    logBuffer.append(contentType).append("------------\n");
                }
            }
        }
        if (bodyCharset != null) {
            return doRecordReqBody(logBuffer, request.getBody(), bodyCharset)
                    .then(Mono.defer(() -> {
                        logBuffer.append("\n------------ end ------------\n\n");
                        return Mono.empty();
                    }));
        } else {
            logBuffer.append("------------ end ------------\n\n");
            return Mono.empty();
        }
    }

    /**
     * 打印日志
     *
     * @param logBuilder 日志建设者
     * @param response   响应
     * @return reactor.core.publisher.Mono<java.lang.Void>
     * @author 王强
     */
    private Mono<Void> printLog(StringBuilder logBuilder, ServerHttpResponse response) {
        HttpStatus statusCode = response.getStatusCode();
        assert statusCode != null;
        logBuilder.append("响应：").append(statusCode.value()).append(" ").append(statusCode.getReasonPhrase()).append('\n');
        HttpHeaders headers = response.getHeaders();
        logBuilder.append("------------响应头------------\n");
        headers.forEach((name, values) -> {
            values.forEach(value -> {
                logBuilder.append(name).append(":").append(value).append('\n');
            });
        });
        logBuilder.append("\n------------ end at ")
                .append(System.currentTimeMillis())
                .append("------------\n\n");
        log.info(logBuilder.toString());
        return Mono.empty();
    }

    private boolean hasBody(HttpMethod method) {
        // 只记录这3种请求的body
        if (method == HttpMethod.POST || method == HttpMethod.PUT || method == HttpMethod.PATCH) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 记录简单的常见的文本类型的request的body和response的body
     *
     * @param contentType 内容类型
     * @return boolean
     * @author 王强
     */
    private boolean shouldRecordBody(MediaType contentType) {
        String type = contentType.getType();
        String subType = contentType.getSubtype();
        if ("application".equals(type)) {
            return "json".equals(subType) || "x-www-form-urlencoded".equals(subType) || "xml".equals(subType) || "atom+xml".equals(subType) || "rss+xml".equals(subType);
        } else if ("text".equals(type)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取请求的参数
     *
     * @param logBuffer 日志缓冲区
     * @param body      身体
     * @param charset   字符集
     * @return reactor.core.publisher.Mono<java.lang.Void>
     * @author 王强
     */
    private Mono<Void> doRecordReqBody(StringBuilder logBuffer, Flux<DataBuffer> body, Charset charset) {
        return DataBufferUtils.join(body).doOnNext(buffer -> {
            CharBuffer charBuffer = charset.decode(buffer.asByteBuffer());
            logBuffer.append(charBuffer);
            DataBufferUtils.release(buffer);
        }).then();
    }

    private Charset getMediaTypeCharset(@Nullable MediaType mediaType) {
        if (mediaType != null && mediaType.getCharset() != null) {
            return mediaType.getCharset();
        } else {
            return StandardCharsets.UTF_8;
        }
    }

    @Override
    public int getOrder() {
        //在GatewayFilter之前执行
        return -1;
    }
}
