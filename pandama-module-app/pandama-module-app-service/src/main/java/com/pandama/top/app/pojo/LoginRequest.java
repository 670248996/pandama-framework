package com.pandama.top.app.pojo;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author: hyj
 * @date 2020/12/22 10:08
 */
@Data
@Accessors(chain = true)
@ApiModel
public class LoginRequest {
    private String userId;
    private String timestamp;
    private String clientHash;
    private String devId;
    private String sessionId;
}
