package com.ayu.audio.service;

import com.ayu.audio.dao.AudioFilesDao;
import com.ayu.audio.dto.AudioFileDeleteDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AudioFileDeleteService {
    private final AudioFilesDao audioFilesDao;

    @Autowired
    public AudioFileDeleteService(AudioFilesDao audioFilesDao) {
        this.audioFilesDao = audioFilesDao;
    }

    public AudioFileDeleteDto deleteFile(String fileName) {
        if (
                audioFilesDao.deleteFileByFileName(fileName))
            return AudioFileDeleteDto.builder().fileUploadMessage("File " + fileName + " deleted successfully.").build();
        else
            return AudioFileDeleteDto.builder().fileUploadMessage("File " + fileName + " delete failed.").build();
    }
}
