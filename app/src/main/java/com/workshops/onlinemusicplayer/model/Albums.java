package com.workshops.onlinemusicplayer.model;

import java.io.Serializable;

public class Albums implements Serializable {
    int id;
    String name, image;

    public Albums(int id, String name, String image) {
        this.id = id;
        this.image = image;
        this.name = name;
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
}
