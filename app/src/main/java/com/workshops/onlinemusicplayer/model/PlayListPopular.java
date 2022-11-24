package com.workshops.onlinemusicplayer.model;

import java.io.Serializable;

public class PlayListPopular implements Serializable {
    String id, name, image, singer;

    public PlayListPopular(String id, String name, String image, String singer) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.singer = singer;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
}
