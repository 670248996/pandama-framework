package com.pandama.top.file.api.pojo.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 分片上传出参
 *
 * @author 王强
 * @date 2023-11-02 16:51:13
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UploadVO {

    @ApiModelProperty("文件名称")
    private String fileName;

    @ApiModelProperty("文件路径")
    private String filePath;

    @ApiModelProperty("分片路径")
    private String chunkPath;

}
