package com.ayu.audio.web.validation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import com.musicg.wave.WaveHeader;

public class TestHeader {

	public static void main(String[] args) throws FileNotFoundException {
		InputStream inputStream = new FileInputStream(
				new File("F:\\utils\\se\\ws_nu\\audio\\src\\oth\\resources\\sample\\gongs.wav"));
		WaveHeader waveHeader = new WaveHeader(inputStream);
		System.out.println(waveHeader.getFormat());
		System.out.println(waveHeader.isValid());
	}

}
