package com.example.mservices.resource.service;

public class InvalidAudioMpegException extends RuntimeException {

    InvalidAudioMpegException() {
        super("Invalid MP3");
    }

    public InvalidAudioMpegException(Throwable cause) {
        super("Invalid MP3", cause);
    }
}
