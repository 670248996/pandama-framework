package com.pandama.top.file.open.facade;


import com.pandama.top.file.open.pojo.dto.SliceUploadDTO;
import com.pandama.top.file.open.pojo.dto.UploadFileDTO;
import com.pandama.top.file.open.pojo.dto.UploadImageDTO;
import com.pandama.top.file.open.pojo.vo.UploadVO;

import java.io.IOException;

/**
 * FileDubbo服务
 *
 * @author 王强
 * @date 2023-07-08 15:37:02
 */
public interface FileDubboService {

    /**
     * 文件上传
     *
     * @param dto 入参
     * @return com.pandama.top.file.open.pojo.vo.UploadFileDTO
     * @author 王强
     */
    UploadVO uploadFile(UploadFileDTO dto);

    /**
     * 图片上传
     *
     * @param dto 入参
     * @return com.pandama.top.file.open.pojo.vo.UploadImageDTO
     * @author 王强
     */
    UploadVO uploadImage(UploadImageDTO dto);

    /**
     * 分片上传
     *
     * @param dto 入参
     * @return com.pandama.top.file.open.pojo.vo.UploadVO
     * @author 王强
     */
    UploadVO sliceUpload(SliceUploadDTO dto) throws IOException;

    /**
     * 删除文件
     *
     * @param filePath 文件路径
     * @author 王强
     */
    void deleteFile(String filePath);

    /**
     * 获取文件谢谢
     *
     * @param filePath  文件路径
     * @return java.lang.String
     * @author 王强
     */
    String getFileInfo(String filePath);

    /**
     * 获取文件访问地址
     *
     * @param filePath  文件路径
     * @return java.lang.String
     * @author 王强
     */
    String getFileUrl(String filePath);

}
