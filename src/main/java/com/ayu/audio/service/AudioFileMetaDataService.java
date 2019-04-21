package com.ayu.audio.service;

import com.ayu.audio.dao.AudioFilesDao;
import com.ayu.audio.domain.StoredFileDetails;
import com.ayu.audio.dto.AudioFileMetaDataDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AudioFileMetaDataService {
    private final AudioFilesDao audioFilesDao;

    @Autowired
    public AudioFileMetaDataService(AudioFilesDao audioFilesDao) {
        this.audioFilesDao = audioFilesDao;
    }

    public AudioFileMetaDataDto metaDataOfFile(String fileName) {
        return AudioFileMetaDataDto.builder().storedFileDetails(audioFilesDao.getFileMetadataByFileName(fileName)).build();
    }
}
