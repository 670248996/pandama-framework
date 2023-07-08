package com.pandama.top.starter.web.upload;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.fileupload.ProgressListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

/**
 * 文件上载过程侦听器
 *
 * @author 王强
 * @date 2023-07-08 15:34:18
 */
@Slf4j
@Component
public class FileUploadProgressListener implements ProgressListener {
    @Autowired
    private ProgressBar progressBar;
    private HttpSession session;

    public void setSession(HttpSession session) {
        this.session = session;
        session.setAttribute("upload_percent", 0);
    }

    @Override
    public void update(long bytesRead, long contentLength, int items) {
        int percent = (int) (bytesRead * 100.0 / contentLength);
        session.setAttribute("upload_percent", percent);
        progressBar.printPercentProcess(bytesRead, contentLength);
    }
}
