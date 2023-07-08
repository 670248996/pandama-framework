package com.pandama.top.core;

import com.pandama.top.core.utils.SpringContextUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 核心服务自动配置类
 *
 * @author 王强
 * @date 2023-07-08 15:18:38
 */
@Configuration
@Import({SpringContextUtils.class})
public class CoreAutoConfiguration {

}
