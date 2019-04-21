package com.ayu.audio.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AudioFileDeleteDto {
    private String fileUploadMessage;
}
