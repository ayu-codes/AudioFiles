package com.ayu.audio.web.validation;

import com.musicg.wave.WaveHeader;
import org.springframework.stereotype.Component;

import java.io.InputStream;

@Component
public class WaveFormatValidator implements AudioFormatValidator{
    @Override
    public boolean formatValidator(InputStream inputStream) {
        WaveHeader waveHeader = new WaveHeader(inputStream);
        return waveHeader.isValid();
    }
}
