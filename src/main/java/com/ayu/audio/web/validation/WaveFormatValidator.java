package com.ayu.audio.web.validation;

import com.musicg.wave.WaveHeader;

import java.io.FileInputStream;

public class WaveFormatValidator implements AudioFormatValidator{
    @Override
    public boolean formatValidator(FileInputStream fileInputStream) {
        WaveHeader waveHeader = new WaveHeader(fileInputStream);
        return waveHeader.isValid();
    }
}
