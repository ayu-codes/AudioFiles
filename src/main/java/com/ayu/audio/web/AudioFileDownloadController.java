package com.ayu.audio.web;

import com.ayu.audio.dto.AudioFileDownloadDto;
import com.ayu.audio.dto.AudioFileIdDownloadDto;
import com.ayu.audio.exception.FileNotAvailableException;
import com.ayu.audio.service.AudioFileDownloadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

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

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<StreamingResponseBody> downloadAudioFile(@RequestParam(name = "fileName") String fileName)
            throws FileNotAvailableException {
        AudioFileDownloadDto audioFileDownloadDto = audioFileDownloadService.downloadFile(fileName);

        if (audioFileDownloadDto.getStoredFiles()!=null && !audioFileDownloadDto.getStoredFiles().isEmpty()) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            StreamingResponseBody body = outputStream -> {
                outputStream.write(audioFileDownloadDto.toString().getBytes());
                outputStream.flush();
            };
            return new ResponseEntity<>(body, headers, HttpStatus.OK);
        } else {
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
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDisposition(ContentDisposition.builder("attachment").filename(fileName).build());
            headers.setCacheControl(CacheControl.maxAge(600, TimeUnit.SECONDS));
            return new ResponseEntity<>(responseBody, headers, HttpStatus.OK);
        }
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{itemId}")
    public ResponseEntity<StreamingResponseBody> downloadAudioFileWithId(@PathVariable String itemId) throws FileNotAvailableException {
        AudioFileIdDownloadDto audioFileIdDownloadDto = audioFileDownloadService.downloadFileWithItemId(itemId);

        ByteBuffer buffer = ByteBuffer.wrap(audioFileIdDownloadDto.getFileContent());
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
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDisposition(ContentDisposition.builder("attachment").filename(audioFileIdDownloadDto.getFileName()).build());
        headers.setCacheControl(CacheControl.maxAge(600, TimeUnit.SECONDS));
        return new ResponseEntity<>(responseBody, headers, HttpStatus.OK);
    }
}
