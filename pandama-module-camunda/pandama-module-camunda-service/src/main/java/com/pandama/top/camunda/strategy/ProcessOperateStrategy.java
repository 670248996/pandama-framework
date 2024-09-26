package com.pandama.top.camunda.strategy;


import com.pandama.top.camunda.service.ProcessService;

/**
 * 流程操作策略
 *
 * @author 王强
 * @date 2024-09-14 09:16:33
 */
public enum ProcessOperateStrategy {
    /**
     * 启动
     */
    START {
        @Override
        public void execute(ProcessService process) {
            System.out.println("Process START");
        }
    },
    /**
     * 批准
     */
    APPROVE {
        @Override
        public void execute(ProcessService process) {
            System.out.println("Process APPROVE");
        }
    },
    /**
     * 拒绝
     */
    REJECT {
        @Override
        public void execute(ProcessService process) {
            System.out.println("Process REJECT");
        }
    },
    /**
     * 回退
     */
    REBACK {
        @Override
        public void execute(ProcessService process) {
            System.out.println("Process REBACK");
        }
    },
    /**
     * 挂起
     */
    SUSPEND {
        @Override
        public void execute(ProcessService process) {
            System.out.println("Process SUSPEND");
        }
    },
    /**
     * 激活
     */
    ACTIVATE {
        @Override
        public void execute(ProcessService process) {
            System.out.println("Process ACTIVATE");
        }
    },
    /**
     * 删除
     */
    DELETE {
        @Override
        public void execute(ProcessService process) {
            System.out.println("Process DELETE");
        }
    };

    public abstract void execute(ProcessService process);
}
