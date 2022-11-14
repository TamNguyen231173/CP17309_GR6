package com.workshops.onlinemusicplayer.model;

import java.io.Serializable;

public class Song implements Serializable {
    private int  id;
    private String title, singer, category, resource, image;

    public Song(int i, String title, String id_singer, String resource) {
    }

    public Song(int id, String title, String singer, String image, String resource) {
        this.title = title;
        this.singer = singer;
        this.resource = resource;
        this.id = id;
        this.image = image;
    }
    public Song(int id, String title, String resource) {
        this.title = title;
        this.resource = resource;
        this.id = id;
    }

    public Song(int id, String title, String singer, String category, String image, String resource) {
        this.title = title;
        this.singer = singer;
        this.resource = resource;
        this.id = id;
        this.image = image;
        this.category = category;
    }

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
}
