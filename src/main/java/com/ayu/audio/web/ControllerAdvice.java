package com.ayu.audio.web;

import com.ayu.audio.exception.FileNotAvailableException;
import com.ayu.audio.web.exception.FormatValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.DataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;

@org.springframework.web.bind.annotation.ControllerAdvice
@Slf4j
public class ControllerAdvice {
    @InitBinder
    private void directFieldAccess(DataBinder dataBinder) {
        dataBinder.initDirectFieldAccess();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> internalServerError(Exception exception) {
        log.error(exception.getMessage(), exception);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("There is some issue with service. Kindly check later.");
    }

    @ExceptionHandler(FileNotAvailableException.class)
    public ResponseEntity<?> fileNotAvailableException(FileNotAvailableException exception) {
        return ResponseEntity.badRequest().body(exception.getMessage());
    }

    @ExceptionHandler(FormatValidationException.class)
    public ResponseEntity<?> formatValidationException(FormatValidationException exception){
        return ResponseEntity.badRequest().body(exception.getMessage());
    }
}
