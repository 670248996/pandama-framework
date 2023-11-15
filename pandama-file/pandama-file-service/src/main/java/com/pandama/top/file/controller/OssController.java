package com.pandama.top.file.controller;

import com.pandama.top.core.global.response.Response;
import com.pandama.top.core.utils.UserInfoUtils;
import com.pandama.top.file.open.facade.FileDubboService;
import com.pandama.top.file.open.pojo.dto.SliceUploadDTO;
import com.pandama.top.file.open.pojo.dto.UploadFileDTO;
import com.pandama.top.file.open.pojo.vo.UploadVO;
import com.pandama.top.logRecord.annotation.OperationLog;
import com.pandama.top.logRecord.context.LogRecordContext;
import com.pandama.top.minio.conf.MinioConfig;
import com.pandama.top.minio.utils.MinioUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;

/**
 * OSS文件服务接口
 *
 * @author 王强
 * @date 2023-07-08 15:37:33
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/oss")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class OssController {

    private final FileDubboService fileService;

    private final MinioUtils minioUtils;

    private final MinioConfig minioConfig;

    /**
     * 文件上传
     *
     * @param file 文件
     */
    @OperationLog(bizEvent = "'2'", msg = "'【' + #user.username + '】上传文件【' + #vo.filePath + '】'", tag = "'1'")
    @PostMapping("/upload")
    public Response<UploadVO> upload(@RequestParam("file") MultipartFile file) {
        LogRecordContext.putVariables("user", UserInfoUtils.getUserInfo());
        String fileName = file.getOriginalFilename();
        UploadFileDTO build = UploadFileDTO.builder().file(file).fileName(fileName).build();
        return Response.success(fileService.uploadFile(build));
    }

    /**
     * 文件分片上传
     *
     * @param dto     入参
     * @return com.pandama.top.file.api.pojo.vo.SliceUploadVO
     * @author 王强
     */
    @OperationLog(condition = "#complete == true" , bizEvent = "'2'", msg = "'【' + #user.username + '】分片上传【' + #vo.filePath + '】'", tag = "'1'")
    @PostMapping("/sliceUpload")
    public Response<UploadVO> sliceUpload(@Validated SliceUploadDTO dto) throws Exception {
        LogRecordContext.putVariables("user", UserInfoUtils.getUserInfo());
        return Response.success(fileService.sliceUpload(dto));
    }

    /**
     * 删除
     *
     * @param filePath 文件路径
     */
    @OperationLog(bizEvent = "'2'", msg = "'【' + #user.username + '】删除文件【' + #filePath + '】'", tag = "'2'")
    @DeleteMapping("/")
    public Response<?> delete(@RequestParam("filePath") String filePath) {
        LogRecordContext.putVariables("user", UserInfoUtils.getUserInfo());
        fileService.deleteFile(filePath);
        return Response.success();
    }

    /**
     * 获取文件信息
     *
     * @param filePath  文件名称
     */
    @GetMapping("/info")
    public Response<String> getFileStatusInfo(@RequestParam("filePath") String filePath) {
        return Response.success(fileService.getFileInfo(filePath));
    }

    /**
     * 获取文件外链网络
     *
     * @param filePath  文件名称
     * @return
     */
    @GetMapping("/url")
    public Response<String> getFileUrl(@RequestParam("filePath") String filePath) {
        return Response.success(fileService.getFileUrl(filePath));
    }

    /**
     * 文件下载
     *
     * @param filePath  文件名称
     */
    @OperationLog(bizEvent = "'2'", msg = "'【' + #user.username + '】下载文件【' + #filePath + '】'", tag = "'3'")
    @GetMapping("/download")
    public void download(@RequestParam("filePath") String filePath, HttpServletResponse response) {
        try {
            LogRecordContext.putVariables("user", UserInfoUtils.getUserInfo());
            InputStream fileInputStream = minioUtils.getObject(minioConfig.getBucketName(), filePath);
            response.setHeader("Content-Disposition", "attachment;filename=" + filePath);
            response.setContentType("application/force-download");
            response.setCharacterEncoding("UTF-8");
            IOUtils.copy(fileInputStream, response.getOutputStream());
        } catch (Exception e) {
            log.error("下载失败");
        }
    }

}