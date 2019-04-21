package com.ayu.audio.web.validation;

import java.io.FileInputStream;

public interface AudioFormatValidator {
    boolean formatValidator(FileInputStream fileInputStream);
}
