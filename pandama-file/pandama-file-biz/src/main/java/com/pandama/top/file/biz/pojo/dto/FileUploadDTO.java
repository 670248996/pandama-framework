package com.pandama.top.file.biz.pojo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

/**
 * @description: 文件上传入参
 * @author: 王强
 * @dateTime: 2023-04-19 11:12:38
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FileUploadDTO {

    @ApiModelProperty("文件MD5")
    private String md5;

    @ApiModelProperty("上传文件的文件名称")
    private String fileName;

    @ApiModelProperty("上传文件的文件大小")
    private Long fileSize;

    @ApiModelProperty("总分片数量")
    private Integer chunkCount;

    @ApiModelProperty("当前为第几块分片")
    private Integer chunkIndex;

    @ApiModelProperty("按多大的文件粒度进行分片")
    private Integer chunkSize;

    @ApiModelProperty("分片对象")
    private MultipartFile chunkFile;

    @ApiModelProperty("上传文件到指定目录")
    private String path = "upload";

}
