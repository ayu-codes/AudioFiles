package com.ayu.audio.web;

import com.ayu.audio.dto.AudioFileDeleteDto;
import com.ayu.audio.service.AudioFileDeleteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.ayu.audio.constants.AudioConstants.DELETE;

@RestController
@RequestMapping(DELETE)
public class AudioFileDeleteController {
    private final AudioFileDeleteService audioFileDeleteService;

    @Autowired
    public AudioFileDeleteController(AudioFileDeleteService audioFileDeleteService) {
        this.audioFileDeleteService = audioFileDeleteService;
    }

    @RequestMapping(method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteAudioFile(@RequestParam("fileName") String fileName) {
        return new ResponseEntity<>(audioFileDeleteService.deleteFile(fileName), HttpStatus.OK);
    }
}
