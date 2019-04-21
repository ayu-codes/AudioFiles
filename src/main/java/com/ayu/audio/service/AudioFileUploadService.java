package com.ayu.audio.service;

import org.springframework.stereotype.Service;

import com.ayu.audio.dao.AudioFilesDao;
import com.ayu.audio.dto.AudioFileUploadDto;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;

@Service
@Slf4j
public class AudioFileUploadService {
    private final AudioFilesDao audioFilesDao;

    public AudioFileUploadService(AudioFilesDao audioFilesDao) {
        this.audioFilesDao = audioFilesDao;

    }

    public AudioFileUploadDto uploadFile(MultipartFile file, String speakerName, Timestamp timestamp) {
        if (audioFilesDao.insertUploadedFile(file, speakerName , timestamp))
            return AudioFileUploadDto.builder().uploadMessage("File Uploaded successfully.").build();
        else
            return AudioFileUploadDto.builder().uploadMessage("File Uploaded failed.").build();
    }
}
