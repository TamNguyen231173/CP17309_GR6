package com.workshops.onlinemusicplayer.model;

import java.io.Serializable;

public class Singer implements Serializable {
    String name, id, image;


    public Singer(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public Singer(String name, String id, String image) {
        this.name = name;
        this.id = id;
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
