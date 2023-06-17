package com.pandama.top.app.message;

import com.pandama.top.rocketmq.pojo.BaseMessage;
import lombok.Data;

import java.time.LocalDate;

@Data
public class MemberMessage extends BaseMessage {


    private LocalDate birthday;

    private String username;
}
