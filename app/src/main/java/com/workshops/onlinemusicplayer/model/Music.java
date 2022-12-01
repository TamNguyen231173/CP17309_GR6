package com.workshops.onlinemusicplayer.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Music implements Serializable {
    private  int id;
    private String name, singer, category, resource, image, lyrics;
    private boolean flag;
    public Map<String, Boolean> stars = new HashMap<>();

    public Music() {
    }
    public Music(int id, String name, String singer, String image) {
        this.name = name;
        this.singer = singer;
        this.id = id;
        this.image = image;
    }

    public Music(int id, String name, String singer, String image, boolean flag) {
        this.name = name;
        this.singer = singer;
        this.id = id;
        this.image = image;
        this.flag = flag;
    }
    public Music(int id, String name, String image) {
        this.name = name;
        this.id = id;
        this.image = image;
    }

    public Music(String singer) {
        this.singer = singer;
    }


    public Music(int id, String name, String singer, String image, String resource, String lyrics) {
        this.name = name;
        this.singer = singer;
        this.resource = resource;
        this.id = id;
        this.image = image;
        this.lyrics = lyrics;
    }

    public Music(int id, String name, String singer, String image, String resource, String lyrics, String category, boolean flag) {
        this.id = id;
        this.name = name;
        this.singer = singer;
        this.category = category;
        this.resource = resource;
        this.image = image;
        this.lyrics = lyrics;
        this.flag = flag;
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

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
