package com.pandama.top.starter.web.upload;

import lombok.NonNull;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 自定义多媒体解析器
 *
 * @author 王强
 * @date 2023-07-08 15:32:52
 */
@Component
public class CustomMultipartResolver extends CommonsMultipartResolver {

    @Autowired
    private FileUploadProgressListener listener;

    @Autowired
    private ProgressBar progressBar;

    @Override
    protected MultipartParsingResult parseRequest(@NonNull HttpServletRequest request) throws MultipartException {
        //初始化进度条
        progressBar.init();
        String encoding = determineEncoding(request);
        FileUpload fileUpload = prepareFileUpload(encoding);
        //fileUpload.setFileSizeMax(1024 * 1024 * 500);// 单个文件最大500M
        //fileUpload.setSizeMax(1024 * 1024 * 500);// 一次提交总文件最大500M
        //向文件上传进度监听器设置session用于存储上传进度
        listener.setSession(request.getSession());
        //將文件上传进度监听器加入到fileUpload中
        fileUpload.setProgressListener(listener);
        try {
            List<FileItem> fileItems = ((ServletFileUpload) fileUpload).parseRequest(request);
            return parseFileItems(fileItems, encoding);
        } catch (FileUploadException e) {
            e.printStackTrace();
        } finally {
            System.out.println();
        }
        return null;
    }
}
