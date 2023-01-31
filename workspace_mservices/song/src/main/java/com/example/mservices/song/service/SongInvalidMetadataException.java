package com.example.mservices.song.service;

public class SongInvalidMetadataException extends RuntimeException {

    SongInvalidMetadataException() {
        super("Invalid metadata for the song");
    }
}
