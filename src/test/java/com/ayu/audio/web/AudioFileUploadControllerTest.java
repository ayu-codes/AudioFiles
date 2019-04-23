package com.ayu.audio.web;

import com.ayu.audio.dto.AudioFileCalendarEntryDto;
import com.ayu.audio.dto.AudioFileUploadDto;
import com.ayu.audio.service.AudioFileUploadService;
import com.ayu.audio.service.GoogleCalendarService;
import com.ayu.audio.web.validation.Validator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;

import static com.ayu.audio.constants.AudioConstants.UPLOAD;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = AudioFileUploadController.class)
public class AudioFileUploadControllerTest {
    @MockBean
    AudioFileUploadController audioFileUploadController;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AudioFileUploadService audioFileUploadService;

    @MockBean
    private GoogleCalendarService googleCalendarService;

    @MockBean
    private Validator validator;

    @Test
    public void givenWavFormatFile_ThenUploadFile() throws Exception {
        MockMultipartFile mockFile = new MockMultipartFile("gongs.wav", "gongs.wav", "audio/wav", "audio".getBytes());
        MultipartFile[] files = new MultipartFile[]{mockFile};
        AudioFileUploadDto audioFileUploadDto = AudioFileUploadDto.builder().message("File gongs.wav uploaded successfully.").build();
        AudioFileCalendarEntryDto audioFileCalendarEntryDto = AudioFileCalendarEntryDto.builder().message("Audio file upload event for gongs.wav added in Google calendar.").build();
        when(audioFileUploadService.uploadFile(files[0], files[0].getOriginalFilename(), new Timestamp(1L)))
                .thenReturn(audioFileUploadDto);
        when(googleCalendarService.pushEvents(files[0].getOriginalFilename(), new Timestamp(1L)))
                .thenReturn(audioFileCalendarEntryDto);

        mockMvc.perform(
                MockMvcRequestBuilders
                        .multipart("/" + UPLOAD)
                        .file(mockFile)
                        .param("file", "")
                        .param("speakerName", "ayu")
        )
                .andExpect(status().isOk())
                .andReturn()
        ;
    }
}