package com.example.mservices.song.webservice;

import com.example.mservices.song.service.SongInvalidMetadataException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class SongInvalidMetadataAdvice {
    @ResponseBody
    @ExceptionHandler(SongInvalidMetadataException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String songNotFoundHandler(SongInvalidMetadataException ex) {
        return ex.getMessage();
    }
}
