package com.workshops.onlinemusicplayer.model;

import java.io.Serializable;

public class PlayListPopular implements Serializable {
    String  name, image, singer, resource, lyrics;
    int id;

    public PlayListPopular(int id, String name, String image, String singer, String resource, String lyrics) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.singer = singer;
        this.resource = resource;
        this.lyrics = lyrics;
    }

    public PlayListPopular(String image) {
        this.image = image;
    }

    public PlayListPopular(int id, String name, String image, String singer) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.singer = singer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getLyrics() {
        return lyrics;
    }

    public void setLyrics(String lyrics) {
        this.lyrics = lyrics;
    }
}
