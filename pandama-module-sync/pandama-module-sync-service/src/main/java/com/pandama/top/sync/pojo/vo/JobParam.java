package com.pandama.top.sync.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * 任务参数
 *
 * @author 王强
 * @date 2024-04-24 12:27:57
 */
@Data
public class JobParam {

    @ApiModelProperty("开始日期")
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING, timezone = "GMT+8")
    @NotNull(message = "startDate不能为空")
    private LocalDate startDate;

    @ApiModelProperty("结束日期")
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING, timezone = "GMT+8")
    @NotNull(message = "endDate不能为空")
    private LocalDate endDate;
}
