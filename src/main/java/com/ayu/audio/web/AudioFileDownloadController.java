package com.ayu.audio.web;

import com.ayu.audio.dto.AudioFileDownloadDto;
import com.ayu.audio.service.AudioFileDownloadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.TimeUnit;

import static com.ayu.audio.constants.AudioConstants.DOWNLOAD;

@RestController
@RequestMapping(DOWNLOAD)
public class AudioFileDownloadController {
    private AudioFileDownloadService audioFileDownloadService;

    @Autowired
    public AudioFileDownloadController(AudioFileDownloadService audioFileDownloadService) {
        this.audioFileDownloadService = audioFileDownloadService;
    }

    @RequestMapping(method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_OCTET_STREAM_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<StreamingResponseBody> downloadAudioFile(@RequestParam(name = "fileName") String fileName,
                                                                   @RequestParam(name = "speakerName") String speakerName) throws IOException {
        AudioFileDownloadDto audioFileDownloadDto = audioFileDownloadService.downloadFile(fileName, speakerName);
//        ByteArrayResource resource = new ByteArrayResource(audioFileDownloadDto.getFileContent());
//        OutputStream outputStream = new ByteArrayOutputStream();
        ByteBuffer buffer = ByteBuffer.wrap(audioFileDownloadDto.getFileContent());
        byte[] arr = new byte[2048];
        StreamingResponseBody responseBody = outputStream -> {
            while (buffer.hasRemaining()) {
                int length = Math.min(buffer.remaining(), arr.length);
                buffer.get(arr, 0, length);
                outputStream.write(arr);
                outputStream.flush();
            }
        };
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDisposition(ContentDisposition.builder("attachment").filename(fileName).build());
        headers.setCacheControl(CacheControl.maxAge(600, TimeUnit.SECONDS));
        return new ResponseEntity<>(responseBody, headers, HttpStatus.OK);
    }
}
