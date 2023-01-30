package com.example.mservices.resource.webservice;

import com.example.mservices.resource.service.InvalidAudioMpegException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class InvalidAudioMpegAdvice {
    @ResponseBody
    @ExceptionHandler(InvalidAudioMpegException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String invalidAudioMpegExceptionHandler(InvalidAudioMpegException ex) {
        return ex.getMessage();
    }
}
