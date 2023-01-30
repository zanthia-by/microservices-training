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

    @GetMapping("/{id}")
    Song getSong(@PathVariable Long id) {
        return songService.getSong(id)
                .orElseThrow(() -> new SongNotFoundException(id));
    }

    @PostMapping
    public ResponseEntity<Song> addSong(@RequestBody Song song) {
        Song newSong = songService.addSong(song);
        if (newSong != null) {
            return new ResponseEntity(newSong, HttpStatus.CREATED);
        }
        //TODO response JSON
        //TODO return codes
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{id}")
    Song updateSong(@RequestBody Song newSong, @PathVariable Long id) {
        return songService.updateSong(id, newSong);
    }

    @DeleteMapping("/{ids}")
    void deleteEmployee(@PathVariable String ids) {
        //TODO
    }
}
