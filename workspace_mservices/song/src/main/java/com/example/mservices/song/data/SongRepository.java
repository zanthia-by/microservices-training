package com.example.mservices.song.data;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SongRepository extends JpaRepository<SongEntity, Long> {
}
