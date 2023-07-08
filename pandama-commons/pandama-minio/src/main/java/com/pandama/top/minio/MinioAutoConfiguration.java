package com.pandama.top.minio;

import com.pandama.top.minio.conf.MinioConfig;
import com.pandama.top.minio.utils.MinioUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * minio自动化配置
 *
 * @author 王强
 * @date 2023-07-08 15:26:35
 */
@Configuration
@Import({MinioConfig.class, MinioUtils.class})
public class MinioAutoConfiguration {
}
