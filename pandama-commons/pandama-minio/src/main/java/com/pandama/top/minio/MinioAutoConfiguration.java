package com.pandama.top.minio;

import com.pandama.top.minio.conf.MinioConfig;
import com.pandama.top.minio.utils.MinioUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @description: minio自动化配置
 * @author: 王强
 * @dateTime: 2023-06-17 14:19:05
 */
@Configuration
@Import({MinioConfig.class, MinioUtils.class})
public class MinioAutoConfiguration {
}
