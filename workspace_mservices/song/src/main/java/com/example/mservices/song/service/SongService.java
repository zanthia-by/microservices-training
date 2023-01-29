package com.example.mservices.song.service;

import com.example.mservices.song.data.SongEntity;
import com.example.mservices.song.data.SongRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SongService {
    private final SongRepository songRepository;

    public SongService(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    public List<Song> getAllSongs() {
        return songRepository.findAll().stream()
                .map(this::toSong)
                .toList();
    }

    public Optional<Song> getSong(Long id) {
        return songRepository.findById(id)
                .map(this::toSong);
    }

    public Song addSong(Song song) {
        var entity = toEntity(song);
        var newEntity = songRepository.save(entity);
        return toSong(newEntity);
    }


    public Song updateSong(Long id, Song newSong) {
        var found = songRepository.findById(id);
        if (!found.isEmpty()) {
            var entity = found.get();
            fillEntity(entity, newSong);
            songRepository.save(entity);
            return toSong(entity);
        } else {
            var newEntity = toEntity(newSong);
            songRepository.save(newEntity);
            return toSong(newEntity);
        }
    }

    private Song toSong(SongEntity entity) {
        Song song = new Song();
        song.setId(entity.getId());
        song.setName(entity.getName());
        song.setArtist(entity.getArtist());
        song.setAlbum(entity.getAlbum());
        song.setLength(entity.getLengthSec());
        song.setResourceId(entity.getResourceId());
        song.setYear(entity.getYear());
        return song;
    }

    private SongEntity toEntity(Song song) {
        SongEntity entity = new SongEntity();
        entity.setId(song.getId());
        fillEntity(entity, song);
        return entity;
    }

    private void fillEntity(SongEntity entity, Song song) {
        entity.setName(song.getName());
        entity.setArtist(song.getArtist());
        entity.setAlbum(song.getAlbum());
        entity.setLengthSec(song.getLength());
        entity.setResourceId(song.getResourceId());
        entity.setYear(song.getYear());
    }

}
