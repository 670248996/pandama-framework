package com.pandama.top.user.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pandama.top.core.pojo.vo.BaseUserInfoVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 系统用户信息出参
 *
 * @author 王强
 * @date 2023-07-08 15:53:10
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("系统用户信息VO")
public class SystemUserInfoVO extends BaseUserInfoVO {

    @ApiModelProperty("用户头像地址")
    private String avatar;

    @ApiModelProperty("身份证号")
    private String idCardNo;

    @ApiModelProperty("性别")
    private Integer gender;

    @ApiModelProperty("生日")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    @ApiModelProperty("年龄")
    private Integer age;

    @ApiModelProperty("学历")
    private Integer education;

    @ApiModelProperty("籍贯")
    private String nativePlace;

    @ApiModelProperty("职业")
    private String occupation;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("工作地")
    private String workPlace;

    @ApiModelProperty("在职状态")
    private Integer workingState;

    @ApiModelProperty("入职日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime onboardDate;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @ApiModelProperty("角色id列表")
    protected List<Long> roleIdList;

    @ApiModelProperty("角色编号列表")
    private List<String> roleCodeList;

    @ApiModelProperty("权限编号列表")
    private List<String> permCodeList;

    @ApiModelProperty("路由信息列表")
    private List<RouterTreeResultVO> routerList;

    @ApiModelProperty("密码过期时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime passwordExpireTime;

}
