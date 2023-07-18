package com.pandama.top.file.api.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * 文件Feign服务
 *
 * @author 王强
 * @date 2023-07-08 15:37:02
 */
@FeignClient(name = "pandama-file", path = "/pandama/file")
public interface FileFeignService {

    /**
     * 上传
     *
     * @param file 文件
     * @return java.lang.String
     * @author 王强
     */
    @PostMapping("/oss/upload")
    String upload(@RequestParam("file") MultipartFile file);

    /**
     * 删除
     *
     * @param fileName 文件名称
     * @author 王强
     */
    @DeleteMapping("/oss/")
    void delete(@RequestParam("fileName") String fileName);

    /**
     * 获取文件状态信息
     *
     * @param fileName 文件名称
     * @return java.lang.String
     * @author 王强
     */
    @GetMapping("/oss/info")
    String getFileStatusInfo(@RequestParam("fileName") String fileName);

    /**
     * 获取presigned对象url
     *
     * @param fileName 文件名称
     * @return java.lang.String
     * @author 王强
     */
    @GetMapping("/oss/url")
    String getPresignedObjectUrl(@RequestParam("fileName") String fileName);

    /**
     * 下载
     *
     * @param fileName 文件名称
     * @param response 响应
     * @author 王强
     */
    @GetMapping("/oss/download")
    void download(@RequestParam("fileName") String fileName, HttpServletResponse response);

}
