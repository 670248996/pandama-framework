package com.pandama.top.file.api.pojo.dto;

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
public class SliceUploadDTO {

    @ApiModelProperty("文件MD5")
    @NotBlank(message = "文件MD5不能为空")
    private String md5;

    @ApiModelProperty("文件名称")
    @NotBlank(message = "文件名称不能为空")
    private String fileName;

    @ApiModelProperty("文件大小")
    private Long fileSize;

    @ApiModelProperty("总分片数量")
    @NotNull(message = "总分片数量不能为空")
    private Integer chunkCount;

    @ApiModelProperty("当前分片号")
    @NotNull(message = "当前分片号不能为空")
    private Integer chunkIndex;

    @ApiModelProperty("分片大小")
    private Integer chunkSize;

    @ApiModelProperty("分片文件")
    @NotNull(message = "分片文件不能为空")
    private MultipartFile chunkFile;

    @ApiModelProperty("上传路径")
    private String path = "upload";

}
