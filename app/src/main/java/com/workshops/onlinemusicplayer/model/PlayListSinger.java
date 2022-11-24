package com.workshops.onlinemusicplayer.model;

import java.io.Serializable;

public class PlayListSinger implements Serializable {
    String name, id, image;


    public PlayListSinger(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public PlayListSinger(String id, String name, String image) {
        this.id = id;
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
