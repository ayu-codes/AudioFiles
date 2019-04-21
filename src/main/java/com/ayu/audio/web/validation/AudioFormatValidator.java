package com.ayu.audio.web.validation;

import java.io.InputStream;

public interface AudioFormatValidator {
    boolean formatValidator(InputStream inputStream);
}
