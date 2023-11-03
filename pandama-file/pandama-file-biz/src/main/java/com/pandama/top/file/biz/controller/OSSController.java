package com.pandama.top.file.biz.controller;

import com.pandama.top.core.global.response.Response;
import com.pandama.top.file.api.pojo.dto.SliceUploadDTO;
import com.pandama.top.file.api.pojo.dto.UploadDTO;
import com.pandama.top.file.api.pojo.vo.UploadVO;
import com.pandama.top.minio.conf.MinioConfig;
import com.pandama.top.minio.utils.MinioUtils;
import com.pandama.top.redis.utils.RedisUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.util.IOUtils;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
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
public class OSSController {

    private final MinioUtils minioUtils;

    private final MinioConfig minioConfig;

    private final RedisUtils redisUtils;

    /**
     * 文件上传
     *
     * @param dto 入参
     */
    @PostMapping("/upload")
    public Response<?> upload(@Validated UploadDTO dto) {
        try {
            MultipartFile file = dto.getFile();
            String fileName = dto.getFileName();
            String filePath = dto.getPath() + "/" + fileName;
            minioUtils.uploadFile(minioConfig.getBucketName(), file, filePath, file.getContentType());
            return Response.success(UploadVO.builder().fileName(fileName).filePath(filePath).build());
        } catch (Exception e) {
            return Response.fail("上传失败");
        }
    }

    /**
     * 删除
     *
     * @param fileName 文件名称
     */
    @DeleteMapping("/")
    public void delete(@RequestParam("fileName") String fileName) {
        minioUtils.removeFile(minioConfig.getBucketName(), fileName);
    }

    /**
     * 获取文件信息
     *
     * @param fileName  文件名称
     */
    @GetMapping("/info")
    public Response<String> getFileStatusInfo(@RequestParam("fileName") String fileName) {
        return Response.success(minioUtils.getFileStatusInfo(minioConfig.getBucketName(), fileName));
    }

    /**
     * 获取文件外链网络
     *
     * @param fileName  文件名称
     * @return
     */
    @GetMapping("/url")
    public Response<String> getFileUrl(@RequestParam("fileName") String fileName) {
        return Response.success(minioUtils.getPresignedObjectUrl(minioConfig.getBucketName(), fileName));
    }

    /**
     * 文件下载
     *
     * @param fileName  文件名称
     */
    @GetMapping("/download")
    public void download(@RequestParam("fileName") String fileName, HttpServletResponse response) {
        try {
            InputStream fileInputStream = minioUtils.getObject(minioConfig.getBucketName(), fileName);
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            response.setContentType("application/force-download");
            response.setCharacterEncoding("UTF-8");
            IOUtils.copy(fileInputStream, response.getOutputStream());
        } catch (Exception e) {
            log.error("下载失败");
        }
    }

    /**
     * 文件分片上传
     *
     * @param dto     入参
     * @return com.pandama.top.file.api.pojo.vo.SliceUploadVO
     * @author 王强
     */
    @PostMapping("/sliceUpload")
    public Response<UploadVO> sliceUpload(@Validated SliceUploadDTO dto, HttpServletRequest request) throws Exception {
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        MultipartFile file = dto.getChunkFile();
        String filePath = dto.getPath() + "/" + dto.getFileName();
        UploadVO uploadVO = UploadVO.builder().fileName(dto.getFileName()).filePath(filePath).build();
        if (isMultipart) {
            // 分片数量大于1，则需要将分片文件上传到临时目录，上传完成后合并
            if (dto.getChunkIndex() != null && dto.getChunkCount() > 1) {
                String chunkPath = minioConfig.getTmpPath() + "/" + dto.getMd5() + "/" + dto.getChunkIndex();
                String redisKey = minioConfig.getTmpPath() + ":" + dto.getMd5();
                uploadVO.setChunkPath(chunkPath);
                if (!redisUtils.getBit(redisKey, dto.getChunkIndex()).orElse(false)) {
                    minioUtils.uploadFile(minioConfig.getBucketName(), chunkPath, file.getInputStream());
                    redisUtils.setBit(redisKey, dto.getChunkIndex(), true);
                    if (redisUtils.bitCount(redisKey).orElse(0L).equals(Long.valueOf(dto.getChunkCount()))) {
                        String fileName = dto.getPath() + "/" + dto.getFileName();
                        minioUtils.mergeObject(minioConfig.getBucketName(), fileName, dto.getMd5(), dto.getChunkCount());
                    }
                }
            } else {
                // 分片数量等于1，则不需要分片，直接上传到指定目录
                minioUtils.uploadFile(minioConfig.getBucketName(), file, filePath, file.getContentType());
            }
        }
        return Response.success(uploadVO);
    }

}