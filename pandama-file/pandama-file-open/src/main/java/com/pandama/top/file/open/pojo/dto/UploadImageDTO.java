package com.pandama.top.file.open.pojo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

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
public class UploadImageDTO {

    @ApiModelProperty("文件名称")
    @NotBlank(message = "文件名称不能为空")
    private String fileName;

    @ApiModelProperty("图片对象")
    @NotBlank(message = "图片对象不能为空")
    private String base64;

}
