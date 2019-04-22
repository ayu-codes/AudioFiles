package com.ayu.audio.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AudioFileDto {
    AudioFileUploadDto audioFileUpload;
    AudioFileCalendarEntryDto audioFileCalendarEntry;
}
