package com.pandama.top.core;

import com.pandama.top.core.utils.SpringContextUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @description: 核心服务自动配置类
 * @author: 白剑民
 * @dateTime: 2022/10/12 15:59
 */
@Configuration
@Import({SpringContextUtils.class})
public class CoreAutoConfiguration {

}
