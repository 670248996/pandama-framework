package com.pandama.top.file.biz.file.strategy;

import com.pandama.top.file.biz.pojo.dto.FileUploadDTO;
import com.pandama.top.file.biz.pojo.vo.FileUploadVO;

/**
 * @description: 片上传策略
 * @author: 王强
 * @dateTime: 2023-04-19 16:44:41
 */
public interface SliceUploadStrategy {

    /**
     * @param param 参数
     * @description: 分片上传
     * @author: 王强
     * @date: 2023-04-19 16:44:43
     * @return: FileUploadVO
     * @version: 1.0
     */
    FileUploadVO sliceUpload(FileUploadDTO param);

}
