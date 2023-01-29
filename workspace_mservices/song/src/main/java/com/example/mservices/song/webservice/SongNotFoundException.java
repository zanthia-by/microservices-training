package com.example.mservices.song.webservice;

public class SongNotFoundException extends RuntimeException {

    SongNotFoundException(Long id) {
        super("Could not find song: " + id);
    }
}
