package com.example.mservices.song.webservice;

import com.example.mservices.song.service.Song;
import com.example.mservices.song.service.SongService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("songs")
public class SongController {
    private final SongService songService;

    public SongController(SongService songService) {
        this.songService = songService;
    }

    @GetMapping
    public List<Song> getAllSongs() {
        return songService.getAllSongs();
    }

    @PostMapping
    public ResponseEntity<Song> addSong(@RequestBody Song song) {
        Song newSong = songService.addSong(song);
        if (newSong != null) {
            return new ResponseEntity(newSong, HttpStatus.CREATED);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
}
