package com.workshops.onlinemusicplayer.model;

import java.io.Serializable;

public class Song implements Serializable {
    private  int id;
    private String title, singer, category, resource, image, lyrics;

    public Song(int i, String title) {
    }

    public Song(int id, String title, String singer, String image) {
        this.title = title;
        this.singer = singer;
        this.id = id;
        this.image = image;
    }
    public Song(int id, String title, String image) {
        this.title = title;
//        this.singer = singer;
        this.id = id;
        this.image = image;
    }
    public Song(String singer) {
        this.singer = singer;
    }


    public Song(int id, String title, String singer, String image, String resource, String lyrics) {
        this.title = title;
        this.singer = singer;
        this.resource = resource;
        this.id = id;
        this.image = image;
        this.lyrics = lyrics;
    }

//    public Song(int id, String title, String singer, String category, String image, String resource) {
//        this.title = title;
//        this.singer = singer;
//        this.resource = resource;
//        this.id = id;
//        this.image = image;
//        this.category = category;
//    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLyrics() {
        return lyrics;
    }

    public void setLyrics(String lyrics) {
        this.lyrics = lyrics;
    }
}
