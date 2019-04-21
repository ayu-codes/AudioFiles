package com.ayu.audio.web.validation;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ValidationResult {
    String failedMessage;
    SupportedFormats expectedFormat;
}
