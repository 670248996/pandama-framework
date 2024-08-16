package com.pandama.top.file.controller;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


/**
 * @author lzt
 * @since 2024-07-15
 */
@Data
public class AnalysisTaskUploadVO {

    private static final long serialVersionUID = 1L;

    @NotBlank(message = "上传标记不能为空")
    private String uploadTag;

    @NotNull(message = "任务类型不能为空")
    private Integer type;

    @NotNull(message = "资源文件不能为空")
    private MultipartFile file;

}
