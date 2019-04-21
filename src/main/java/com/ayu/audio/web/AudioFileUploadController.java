package com.ayu.audio.web;

import com.ayu.audio.dto.AudioFileUploadDto;
import com.ayu.audio.service.AudioFileUploadService;
import com.ayu.audio.web.exception.FormatValidationException;
import com.ayu.audio.web.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;

import static com.ayu.audio.constants.AudioConstants.UPLOAD;
import static java.time.Instant.now;

@RestController
@RequestMapping(UPLOAD)
public class AudioFileUploadController {

    private AudioFileUploadService audioFileUploadService;
    private Validator validator;

    @Autowired
    public AudioFileUploadController(AudioFileUploadService audioFileUploadService, Validator validator) {
        this.audioFileUploadService = audioFileUploadService;
        this.validator = validator;
    }

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> uploadAudioFile(@RequestParam(name = "file") MultipartFile file,
                                             @RequestParam(name = "speakerName") String speakerName) throws FormatValidationException {
        Timestamp timestamp = Timestamp.from(now());
        validator.validateFileFormat(file);
        AudioFileUploadDto audioFileUploadDto = audioFileUploadService.uploadFile(file, speakerName, timestamp);
        return new ResponseEntity<>(audioFileUploadDto, HttpStatus.OK);
    }
}
