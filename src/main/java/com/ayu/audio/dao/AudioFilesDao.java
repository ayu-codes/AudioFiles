package com.ayu.audio.dao;

import com.ayu.audio.domain.FileMetadata;
import com.ayu.audio.domain.StoredFileDetails;
import com.ayu.audio.domain.UserFileDetails;
import com.ayu.audio.repository.AudioFileUploadRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
public class AudioFilesDao {
    private AudioFileUploadRepository audioFileUploadRepository;

    public AudioFilesDao(AudioFileUploadRepository audioFileUploadRepository) {
        this.audioFileUploadRepository = audioFileUploadRepository;
    }

    public boolean insertUploadedFile(MultipartFile file, String speakerName, Timestamp timestamp) {
        try {
            FileMetadata fileMetadata = new FileMetadata();
            UserFileDetails userFileDetails = new UserFileDetails();
            userFileDetails.setFileName(file.getOriginalFilename());
            fileMetadata.setSpeakerName(speakerName);
            fileMetadata.setTimeStamp(timestamp);
            userFileDetails.setFileMetadata(fileMetadata);
            userFileDetails.setFileContent(file.getBytes());
            fileMetadata.setUserFileDetails(userFileDetails);
            return audioFileUploadRepository.saveAndFlush(userFileDetails) != null;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return false;
    }

    public List<UserFileDetails> getFileContentByFileNameOrSpeakerName(String fileName, String speakerName) {
        return Arrays.asList(audioFileUploadRepository.findByFileName(fileName));
    }

    public StoredFileDetails getFileMetadataByFileName(String fileName) {
        UserFileDetails userFileDetails = audioFileUploadRepository.findByFileName(fileName);
        StoredFileDetails storedFileDetails = new StoredFileDetails(userFileDetails.getFileMetadata().getTimeStamp(),
                userFileDetails.getFileName(), userFileDetails.getFileMetadata().getSpeakerName());
        return storedFileDetails;
    }

    @Transactional
    public boolean deleteFileByFileName(String fileName) {
        return audioFileUploadRepository.deleteByFileName(fileName) != null;
    }
}
