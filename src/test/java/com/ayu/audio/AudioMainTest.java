package com.ayu.audio;

import com.ayu.audio.web.AudioFileUploadController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AudioMainTest {
    @Autowired
    AudioFileUploadController audioFileUploadController;

    @Test
    public void givenController_thenControllerIsInitializedWithNotNullValue(){
        assertThat(audioFileUploadController).isNotNull();
    }

}