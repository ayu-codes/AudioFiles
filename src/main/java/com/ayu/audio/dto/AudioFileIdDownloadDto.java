package com.ayu.audio.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AudioFileIdDownloadDto {
    private byte[] fileContent;
    private String fileName;
}
