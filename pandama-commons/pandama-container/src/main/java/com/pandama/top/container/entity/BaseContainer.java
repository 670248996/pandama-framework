package com.pandama.top.container.entity;


import com.github.dockerjava.api.model.RestartPolicy;
import com.pandama.top.mybatisplus.entity.BaseEntity;
import lombok.Data;

/**
 * 基础容器
 *
 * @author 王强
 * @date 2023-07-08 15:13:04
 */
@Data
public class BaseContainer extends BaseEntity {
    /**
     * 容器ID
     */
    private String containerId;

    /**
     * 容器名称
     */
    private String containerName;

    /**
     * 镜像名称
     */
    private String imageName;

    /**
     * 特权
     */
    private Boolean privileged;

    /**
     * 重启策略（默认失败重启3次）
     */
    private RestartPolicy restartPolicy = RestartPolicy.onFailureRestart(3);

    /**
     * 绑定端口
     */
    private String portBindings;

    /**
     * 环境
     */
    private String env;

    /**
     * CPU
     */
    private Long cpu;

    /**
     * GPU
     */
    private String gpu;

    /**
     * 内存
     */
    private String memory;

    /**
     * 命令
     */
    private String command;

    /**
     * 实例数量
     */
    private Integer instances;

}
