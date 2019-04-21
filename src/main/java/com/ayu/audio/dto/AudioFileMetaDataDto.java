package com.ayu.audio.dto;

import com.ayu.audio.domain.StoredFileDetails;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class AudioFileMetaDataDto {
    List<StoredFileDetails> storedFileDetails;
}
