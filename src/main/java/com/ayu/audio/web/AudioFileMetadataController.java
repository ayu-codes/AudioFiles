package com.ayu.audio.web;

import com.ayu.audio.service.AudioFileMetaDataService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.ayu.audio.constants.AudioConstants.METADATA;

@RestController
@RequestMapping(METADATA)
public class AudioFileMetadataController {
    private final AudioFileMetaDataService audioFileMetaDataService;

    public AudioFileMetadataController(AudioFileMetaDataService audioFileMetaDataService) {
        this.audioFileMetaDataService = audioFileMetaDataService;
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> metadataAudioFile(@RequestParam(name = "fileName") String fileName) {
        return new ResponseEntity<>(audioFileMetaDataService.metaDataOfFile(fileName), HttpStatus.OK);
    }
}
