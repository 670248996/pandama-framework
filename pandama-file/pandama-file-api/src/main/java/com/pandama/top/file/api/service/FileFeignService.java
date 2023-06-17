package com.pandama.top.file.api.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

@FeignClient(name = "pandama-file", path = "/pandama/file")
public interface FileFeignService {

    /**
     * @param file 文件
     * @description: 上传
     * @author: 王强
     * @date: 2023-06-17 14:40:56
     * @return: String
     * @version: 1.0
     */
    @PostMapping("/oss/upload")
    String upload(@RequestParam("file") MultipartFile file);

    /**
     * @param fileName 文件名称
     * @description: 删除
     * @author: 王强
     * @date: 2023-06-17 14:40:58
     * @return: void
     * @version: 1.0
     */
    @DeleteMapping("/oss/")
    void delete(@RequestParam("fileName") String fileName);

    /**
     * @param fileName 文件名称
     * @description: 获取文件状态信息
     * @author: 王强
     * @date: 2023-06-17 14:40:59
     * @return: String
     * @version: 1.0
     */
    @GetMapping("/oss/info")
    String getFileStatusInfo(@RequestParam("fileName") String fileName);

    /**
     * @param fileName 文件名称
     * @description: 获取presigned对象url
     * @author: 王强
     * @date: 2023-06-17 14:41:02
     * @return: String
     * @version: 1.0
     */
    @GetMapping("/oss/url")
    String getPresignedObjectUrl(@RequestParam("fileName") String fileName);

    /**
     * @param fileName 文件名称
     * @param response 响应
     * @description: 下载
     * @author: 王强
     * @date: 2023-06-17 14:41:03
     * @return: void
     * @version: 1.0
     */
    @GetMapping("/oss/download")
    void download(@RequestParam("fileName") String fileName, HttpServletResponse response);

}
