package com.pandama.top.app.utils;

import lombok.Data;

/**
 * 音乐元信息
 *
 * @author 王强
 * @date 2025-01-02 22:30:51
 */
@Data
public class MusicMetaInfo {

    private Integer width;

    private Integer height;

    private Long size;

    private String format;

    private Long duration;

    private Integer bitRate;

    private Long sampleRate;
}
