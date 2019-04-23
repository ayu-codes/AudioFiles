package com.ayu.audio.web.validation;

import com.ayu.audio.web.exception.FormatValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.EnumSet;
import java.util.List;

import static java.util.EnumSet.*;

@Component
public final class Validator {
    @Autowired
    private List<AudioFormatValidator> validators;

    public void validateFileFormat(MultipartFile file) throws FormatValidationException {
        EnumSet<SupportedFormats> supportedFormats = allOf(SupportedFormats.class);
        boolean isNotValid = false;
        if (validators != null) {
            for (AudioFormatValidator validator : validators) {
                try {
                    isNotValid = !validator.formatValidator(file.getInputStream());
                } catch (IOException e) {
                    throw new FormatValidationException(e.getMessage());
                }
            }
        }
        if (isNotValid) {
            throw new FormatValidationException(String.format("Provided file %1s is not valid. Valid formats is/are %2s",
                    file.getOriginalFilename(), supportedFormats.toString()));
        }
    }
}
