package com.pandama.top.file.service;

import com.pandama.top.core.global.exception.CommonException;
import com.pandama.top.core.utils.DateUtils;
import com.pandama.top.file.open.facade.FileDubboService;
import com.pandama.top.file.open.pojo.dto.SliceUploadDTO;
import com.pandama.top.file.open.pojo.dto.UploadFileDTO;
import com.pandama.top.file.open.pojo.dto.UploadImageDTO;
import com.pandama.top.file.open.pojo.vo.UploadVO;
import com.pandama.top.logRecord.context.LogRecordContext;
import com.pandama.top.minio.conf.MinioConfig;
import com.pandama.top.minio.utils.MinioUtils;
import com.pandama.top.redis.utils.RedisUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件服务实现类
 *
 * @author 王强
 * @date 2023-11-15 13:29:32
 */
@Slf4j
@Service
@DubboService
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class FileServiceImpl implements FileDubboService {

    private final MinioUtils minioUtils;

    private final MinioConfig minioConfig;

    private final RedisUtils redisUtils;

    @Override
    public UploadVO uploadFile(UploadFileDTO dto) {
        try {
            MultipartFile file = dto.getFile();
            String fileName = dto.getFileName();
            String objectName = "files/" + DateUtils.parseTime(DateUtils.TimeFormat.YM_SLASH) + "/" + fileName;
            String fileUrl = minioConfig.getEndpoint() + "/" + minioConfig.getBucketName() + "/" + objectName;
            UploadVO build = UploadVO.builder().fileName(fileName).filePath(objectName).fileUrl(fileUrl).build();
            LogRecordContext.putVariables("vo", build);
            minioUtils.uploadFile(minioConfig.getBucketName(), file, objectName, file.getContentType());
            log.info("文件上传成功: {}", build);
            return build;
        } catch (Exception e) {
            log.error("文件上传失败: {}", e.getMessage());
            throw new CommonException("文件上传失败!");
        }
    }

    @Override
    public UploadVO uploadImage(UploadImageDTO dto) {
        try {
            String fileName = dto.getFileName() + ".jpg";
            String objectName = "images/" + DateUtils.parseTime(DateUtils.TimeFormat.YM_SLASH) + "/" + fileName;
            String fileUrl = minioConfig.getEndpoint() + "/" + minioConfig.getBucketName() + "/" + objectName;
            UploadVO build = UploadVO.builder().fileName(fileName).filePath(objectName).fileUrl(fileUrl).build();
            LogRecordContext.putVariables("vo", build);
            minioUtils.uploadImage(minioConfig.getBucketName(), dto.getBase64(), objectName);
            log.info("图片上传成功: {}", build);
            return build;
        } catch (Exception e) {
            log.error("图片上传失败: {}", e.getMessage());
            throw new CommonException("图片上传失败!");
        }
    }

    @Override
    public UploadVO sliceUpload(SliceUploadDTO dto) {
        try {
            MultipartFile file = dto.getChunkFile();
            String objectName = "largeFiles/" + DateUtils.parseTime(DateUtils.TimeFormat.YM_SLASH) + "/" + dto.getFileName();
            String fileUrl = minioConfig.getEndpoint() + "/" + minioConfig.getBucketName() + "/" + objectName;
            UploadVO build = UploadVO.builder().fileName(dto.getFileName()).filePath(objectName).fileUrl(fileUrl).build();
            LogRecordContext.putVariables("vo", build);
            // 分片数量大于1，则需要将分片文件上传到临时目录，上传完成后合并
            if (dto.getChunkIndex() != null && dto.getChunkCount() > 1) {
                String chunkPath = "tmp/" + dto.getMd5() + "/" + dto.getChunkIndex();
                String redisKey = "tmp:" + dto.getMd5();
                if (!redisUtils.getBit(redisKey, dto.getChunkIndex()).orElse(false)) {
                    minioUtils.uploadFile(minioConfig.getBucketName(), chunkPath, file.getInputStream());
                    redisUtils.setBit(redisKey, dto.getChunkIndex(), true);
                    if (redisUtils.bitCount(redisKey).orElse(0L).equals(Long.valueOf(dto.getChunkCount()))) {
                        minioUtils.mergeObject(minioConfig.getBucketName(), objectName, dto.getMd5(), dto.getChunkCount());
                        LogRecordContext.putVariables("complete", true);
                    }
                }
            } else {
                // 分片数量等于1，则不需要分片，直接上传
                minioUtils.uploadFile(minioConfig.getBucketName(), file, objectName, file.getContentType());
                LogRecordContext.putVariables("complete", true);
            }
            log.info("分片上传成功: {}", build);
            return build;
        } catch (Exception e) {
            log.error("分片上传失败: {}", e.getMessage());
            throw new CommonException("文件上传失败!");
        }
    }

    @Override
    public void deleteFile(String filePath) {
        try {
            minioUtils.removeFile(minioConfig.getBucketName(), filePath);
            log.info("文件删除成功: {}", filePath);
        } catch (Exception e) {
            log.error("文件删除失败: {}", e.getMessage());
            throw new CommonException("文件删除失败!");
        }
    }

    @Override
    public String getFileInfo(String filePath) {
        return minioUtils.getFileStatusInfo(minioConfig.getBucketName(), filePath);
    }

    @Override
    public String getFileUrl(String filePath) {
        return minioUtils.getPresignedObjectUrl(minioConfig.getBucketName(), filePath);
    }
}
