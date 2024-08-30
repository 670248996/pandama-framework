package com.pandama.top.user.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 头像更新入参
 *
 * @author 王强
 * @date 2023-07-08 15:51:00
 */
@Data
@ApiModel("用户头像修改DTO")
public class AvatarUpdateDTO {

    @ApiModelProperty(value = "用户ID", hidden = true)
    private Long userId;

    @ApiModelProperty("头像")
    @NotBlank(message = "头像，avatar不能为空")
    private String avatar;

}
