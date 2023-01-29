package com.example.mservices.song.data;

import jakarta.persistence.*;

@Entity
@Table(name = "SONG")
public class SongEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "song_generator")
    @SequenceGenerator(name="song_generator", sequenceName = "SONG_SEQ", allocationSize=1)
    @Column(name = "ID")
    private long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "ARTIST")
    private String artist;

    @Column(name = "ALBUM")
    private String album;

    @Column(name = "LENGTH_SEC")
    private Integer lengthSec;

    @Column(name = "RESOURCE_ID")
    private Long resourceId;

    @Column(name = "YEAR")
    private Integer year;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public Integer getLengthSec() {
        return lengthSec;
    }

    public void setLengthSec(Integer lengthSec) {
        this.lengthSec = lengthSec;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "Song{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", artist='" + artist + '\'' +
                ", album='" + album + '\'' +
                ", lengthSec=" + lengthSec +
                ", resourceId=" + resourceId +
                ", year=" + year +
                '}';
    }
}
