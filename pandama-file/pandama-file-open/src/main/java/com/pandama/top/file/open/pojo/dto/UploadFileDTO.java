package com.pandama.top.file.open.pojo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 分片上传入参
 *
 * @author 王强
 * @date 2023-11-02 16:51:18
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UploadFileDTO {

    @ApiModelProperty("文件名称")
    @NotBlank(message = "文件名称不能为空")
    private String fileName;

    @ApiModelProperty("文件对象")
    @NotNull(message = "文件对象不能为空")
    private MultipartFile file;

}
