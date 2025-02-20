package com.pandama.top.app.utils;

import lombok.Data;

/**
 * 视频元信息
 *
 * @author 王强
 * @date 2025-01-02 22:30:55
 */
@Data
public class VideoMetaInfo {

    private Integer width;

    private Integer height;

    private Long size;

    private String format;

    private Long duration;

    private Integer bitRate;

    private Long sampleRate;

    private String encoder;

    private Float frameRate;

    private MusicMetaInfo musicMetaInfo;
}
