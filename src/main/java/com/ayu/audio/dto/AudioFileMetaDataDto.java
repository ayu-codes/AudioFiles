package com.ayu.audio.dto;

import com.ayu.audio.domain.StoredFileDetails;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AudioFileMetaDataDto {
    StoredFileDetails storedFileDetails;
}
