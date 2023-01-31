package com.example.mservices.song.webservice;

import com.example.mservices.song.service.Song;
import com.example.mservices.song.service.SongService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

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
    public ResponseEntity<Map<String, Object>> addSong(@RequestBody Song song) {
        Song newSong = songService.addSong(song);
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("id", newSong.getId());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    Song updateSong(@RequestBody Song newSong, @PathVariable Long id) {
        return songService.updateSong(id, newSong);
    }

    @DeleteMapping()
    ResponseEntity<?> deleteSong(@RequestParam("id") String ids) {
        if (ids.length() >= 200) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .build();
        }

        var listOfIds = Stream.of(ids.split(","))
                .map(Long::parseLong)
                .toList();
        var deletedIds = songService.delete(listOfIds);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("ids", deletedIds);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
}
