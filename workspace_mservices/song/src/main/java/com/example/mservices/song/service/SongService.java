package com.example.mservices.song.service;

import com.example.mservices.song.data.SongEntity;
import com.example.mservices.song.data.SongRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
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
        validate(song);
        var entity = toEntity(song);
        var newEntity = songRepository.save(entity);
        return toSong(newEntity);
    }

    private void validate(Song song) {
        if (StringUtils.isBlank(song.getName())
                || StringUtils.isBlank(song.getArtist()))
            throw new SongInvalidMetadataException();
    }

    public Song updateSong(Long id, Song newSong) {
        var found = songRepository.findById(id);
        if (found.isPresent()) {
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

    public List<Long> delete(List<Long> ids) {
        List<Long> deletedIds = new ArrayList<>();
        for (long id : ids) {
            var songEntity = songRepository.findById(id).orElse(null);
            if (songEntity == null) {
                continue;
            }
            deletedIds.add(songEntity.getId());
            songRepository.delete(songEntity);
        }
        deletedIds.sort(Comparator.naturalOrder());
        return deletedIds;
    }
}
