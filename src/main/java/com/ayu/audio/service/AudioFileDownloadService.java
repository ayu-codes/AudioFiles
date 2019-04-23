package com.ayu.audio.service;

import com.ayu.audio.dao.AudioFilesDao;
import com.ayu.audio.domain.StoredFileDetails;
import com.ayu.audio.domain.UserFileDetails;
import com.ayu.audio.dto.AudioFileDownloadDto;
import com.ayu.audio.dto.AudioFileIdDownloadDto;
import com.ayu.audio.exception.FileNotAvailableException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class AudioFileDownloadService {
    private final AudioFilesDao audioFilesDao;

    public AudioFileDownloadService(AudioFilesDao audioFilesDao) {
        this.audioFilesDao = audioFilesDao;
    }

    public AudioFileDownloadDto downloadFile(String fileName) throws FileNotAvailableException {
        List<UserFileDetails> fileDetailsList = audioFilesDao.getFileContentByFileName(fileName);
        if (fileDetailsList.isEmpty())
            throw new FileNotAvailableException(String.format("The requested file %s has not been uploaded yet.", fileName));
        if (fileDetailsList.size() == 1) {
            return AudioFileDownloadDto.builder().fileContent(fileDetailsList.get(0).getFileContent()).build();
        } else{
            List<StoredFileDetails> list = new ArrayList<>();

            fileDetailsList.stream().forEach(
                    fileDetails ->
                            list.add(new StoredFileDetails(
                                    fileDetails.getFileMetadata().getTimeStamp(),
                                    fileDetails.getFileName(),
                                    fileDetails.getFileMetadata().getSpeakerName(),
                                    fileDetails.getId()
                                    ))
            )
            ;
            return AudioFileDownloadDto.builder().storedFiles(list).build();
        }
    }

    public AudioFileIdDownloadDto downloadFileWithItemId(String fileId) throws FileNotAvailableException {
        UserFileDetails userFileDetails = audioFilesDao.getFileContentByFileId(fileId);
        if(userFileDetails.getFileName()==null && userFileDetails.getFileName().isEmpty())
            throw new FileNotAvailableException(String.format("The requested file with ID : %s has not been uploaded yet.", fileId));
        return AudioFileIdDownloadDto.builder().fileName(userFileDetails.getFileName()).fileContent(userFileDetails.getFileContent()).build();
    }
}
