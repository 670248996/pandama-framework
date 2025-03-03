package com.pandama.top.app.scheduled;

import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.Counter;
import io.prometheus.client.Gauge;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 指标控制器
 *
 * @author 王强
 * @date 2023-07-08 11:55:46
 */
@Slf4j
@Component
@EnableScheduling
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class MetricsTask {

    private final CollectorRegistry collectorRegistry;

    static final Gauge algorithmNumber = Gauge.build().name("metrics_algorithm_number").labelNames("platform", "status").help("Inprogress requests.").register();
    static final Gauge threadNumber = Gauge.build().name("metrics_thread_number").labelNames("platform", "status").help("Inprogress requests.").register();
    static final Counter streamNumber = Counter.build().name("metrics_video_pull_stream_number").labelNames("platform", "channelId", "status").help("Inprogress requests.").register();
    static final Counter frameNumber = Counter.build().name("metrics_video_extract_frame_number").labelNames("platform", "channelId", "status").help("Inprogress requests.").register();
    static final Counter producerNumber = Counter.build().name("metrics_kafka_message_producer_number").labelNames("topic", "channelId", "status").help("Inprogress requests.").register();
    static final Counter consumerNumber = Counter.build().name("metrics_kafka_message_consumer_number").labelNames("topic", "channelId", "status").help("Inprogress requests.").register();
    static final Counter handlerNumber = Counter.build().name("metrics_kafka_message_handler_number").labelNames("topic", "channelId", "algorithm_id", "status").help("Inprogress requests.").register();

    @PostConstruct
    private void register() {
        algorithmNumber.register(collectorRegistry);
        threadNumber.register(collectorRegistry);
        streamNumber.register(collectorRegistry);
        frameNumber.register(collectorRegistry);
        producerNumber.register(collectorRegistry);
        consumerNumber.register(collectorRegistry);
        handlerNumber.register(collectorRegistry);
    }
    @Scheduled(fixedRate = 5000)
    public void task() {
        int algNumber = 10;
        int channelNum = 100;
        int frameNum = 5;
        Random random = new Random();
        AtomicInteger algorithmSuccess = new AtomicInteger();
        AtomicInteger algorithmFail = new AtomicInteger();
        for (int i = 0; i < algNumber; i++) {
            int randomNum = random.nextInt(10);
            if (randomNum > 2) {
                algorithmSuccess.getAndIncrement();
            } else {
                algorithmFail.getAndIncrement();
            }
        }

        AtomicInteger threadSuccess = new AtomicInteger();
        AtomicInteger threadFail = new AtomicInteger();
        for (int i = 0; i < channelNum; i++) {
            if (random.nextInt(10) > 2) {
                threadSuccess.getAndIncrement();
                streamNumber.labels("default", String.format("%020d", i), "0").inc();
                for (int i1 = 0; i1 < frameNum; i1++) {
                    frameNumber.labels("default", String.format("%020d", i), "0").inc();
                    if (random.nextInt(10) > 1) {
                        producerNumber.labels("default", String.format("%020d", i), "0").inc();
                        consumerNumber.labels("default", String.format("%020d", i), "0").inc();
                    } else {
                        producerNumber.labels("default", String.format("%020d", i), "1").inc();
                    }
                    handlerNumber.labels("default", String.format("%020d", i), String.format("%010d", i1), "0").inc();
                }
            } else {
                threadFail.getAndIncrement();
                streamNumber.labels("default", String.format("%020d", i), "1").inc();
                for (int i1 = 0; i1 < frameNum; i1++) {
                    frameNumber.labels("default", String.format("%020d", i), "1").inc();
                }
            }
        }

        algorithmNumber.labels("default", "0").set(algorithmSuccess.doubleValue());
        algorithmNumber.labels("default", "1").set(algorithmFail.doubleValue());

        threadNumber.labels("default", "0").set(threadSuccess.doubleValue());
        threadNumber.labels("default", "1").set(threadFail.doubleValue());
    }
}
