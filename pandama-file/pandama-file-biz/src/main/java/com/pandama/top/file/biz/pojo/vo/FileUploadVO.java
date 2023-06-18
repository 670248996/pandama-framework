package com.pandama.top.file.biz.pojo.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * @description: 文件上传出参
 * @author: 王强
 * @dateTime: 2023-04-19 11:12:46
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileUploadVO {

    @ApiModelProperty("上传文件路径")
    private String path;

    @ApiModelProperty("文件标识")
    private String fileId;

    @ApiModelProperty("编号")
    private Integer code;

    @ApiModelProperty("当前时间戳")
    private Long mtime;

    @ApiModelProperty("是否上传完成")
    private Boolean uploadComplete;

    @ApiModelProperty("分片md5信息")
    private Map<Integer, String> chunkMd5Info;

    @ApiModelProperty("缺少的分片")
    private List<Integer> missChunks;

    @ApiModelProperty("文件大小")
    private Long size;

    @ApiModelProperty("文件类型")
    private String fileExt;

}
