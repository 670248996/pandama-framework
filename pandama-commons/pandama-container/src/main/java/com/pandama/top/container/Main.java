package com.pandama.top.container;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerCmd;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.command.InspectContainerResponse;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.RestartPolicy;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.httpclient5.ApacheDockerHttpClient;
import com.pandama.top.container.entity.BaseContainer;

import java.net.URI;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static String DOCKER_HOST = "tcp://123.60.104.105:2375";

    public static String CONTAINER_PREFIX = "Container-";

    public static void main(String[] args) {
        System.out.println("Hello world!");

        BaseContainer nacos = new BaseContainer();
        nacos.setContainerName("Nacos");
        nacos.setImageName("nacos/nacos-server:v2.1.2");
        nacos.setPortBindings("8848:8848");
        nacos.setEnv("JVM_XMS=512m,JVM_XMX=512m,JVM_XMN=256m,PREFER_HOST_MODE=ip,MODE=standalone");
        nacos.setPrivileged(true);
        nacos.setRestartPolicy(RestartPolicy.onFailureRestart(3));

        BaseContainer redis = new BaseContainer();
        redis.setContainerName("Redis");
        redis.setImageName("redis:6.2.6");
        redis.setPortBindings("6379:6379");
        redis.setPrivileged(true);
        redis.setRestartPolicy(RestartPolicy.onFailureRestart(3));

        /**
         * docker 开启2375端口
         * vim /usr/lib/systemd/system/docker.service
         *
         * ExecStart=/usr/bin/dockerd -H fd:// -H tcp://0.0.0.0:2375
         */
        Map<String, BaseContainer> collect = Stream.of(nacos, redis).collect(Collectors.toMap(BaseContainer::getContainerName, Function.identity()));

        // 创建Docker连接
        ApacheDockerHttpClient client = new ApacheDockerHttpClient.Builder()
                .dockerHost(URI.create(DOCKER_HOST))
                .build();
        // 创建Docker客户端
        DockerClient dockerClient = DockerClientBuilder.getInstance()
                .withDockerHttpClient(client)
                .build();

        // 获取所有容器列表，并打印每个容器的基本信息
        List<Container> containerList = dockerClient.listContainersCmd().exec();
        containerList.stream().filter(p -> p.getNames()[0].contains(CONTAINER_PREFIX)).forEach(container -> {
            System.out.println("ID: " + container.getId());
            System.out.println("名称: " + container.getNames()[0].substring(1));
            System.out.println("镜像: " + container.getImage());
            System.out.println("状态: " + container.getStatus());
            // 停止容器
            dockerClient.stopContainerCmd(container.getId()).exec();
            InspectContainerResponse inspect = dockerClient.inspectContainerCmd(container.getId()).exec();
            if (Boolean.FALSE.equals(inspect.getState().getRunning())) {
                System.out.println("停止容器成功: " + inspect.getState());
                dockerClient.removeContainerCmd(container.getId()).exec();
            }
        });

        collect.forEach((name, container) -> {
            HostConfig hostConfig = HostConfig.newHostConfig()
                    //是否自动删除容器
                    .withAutoRemove(false)
                    .withOomScoreAdj(1000)
                    .withPrivileged(container.getPrivileged())
                    .withPortBindings(PortBinding.parse(container.getPortBindings()))
                    .withInit(true)
                    .withRestartPolicy(container.getRestartPolicy());
            // 创建容器
            CreateContainerCmd createContainerCmd = dockerClient.createContainerCmd(container.getImageName())
                    .withName(CONTAINER_PREFIX + name)
                    .withHostConfig(hostConfig);
            Optional.ofNullable(container.getEnv()).ifPresent(env -> createContainerCmd.withEnv(env.split(",")));
            CreateContainerResponse create = createContainerCmd.exec();
            // 启动容器
            dockerClient.startContainerCmd(create.getId()).exec();
            InspectContainerResponse inspect = dockerClient.inspectContainerCmd(create.getId()).exec();
            if (Boolean.TRUE.equals(inspect.getState().getRunning())) {
                System.out.println("启动容器成功: " + inspect.getState());
            }
        });
    }
}