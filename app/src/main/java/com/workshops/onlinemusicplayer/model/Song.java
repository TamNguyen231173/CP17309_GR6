package com.workshops.onlinemusicplayer.model;

import java.io.Serializable;

public class Song implements Serializable {
    private String title, singer, category;
    private int resource, id, image;

    public Song() {
    }

    public Song(int id, String title, String singer, int image, int resource) {
        this.title = title;
        this.singer = singer;
        this.resource = resource;
        this.id = id;
        this.image = image;
    }

    public Song(int id, String title, String singer, String category, int image, int resource) {
        this.title = title;
        this.singer = singer;
        this.resource = resource;
        this.id = id;
        this.image = image;
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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

    public int getResource() {
        return resource;
    }

    public void setResource(int resource) {
        this.resource = resource;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
