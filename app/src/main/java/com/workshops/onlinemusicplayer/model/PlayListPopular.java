package com.workshops.onlinemusicplayer.model;

public class PlayListPopular {
    private String nameSong;
    private String nameSinger;
    private int imagePopular;

    public PlayListPopular() {
    }

    public PlayListPopular(String nameSong, String nameSinger, int imagePopular) {
        this.nameSong = nameSong;
        this.nameSinger = nameSinger;
        this.imagePopular = imagePopular;
    }

    public String getNameSong() {
        return nameSong;
    }

    public void setNameSong(String nameSong) {
        this.nameSong = nameSong;
    }

    public String getNameSinger() {
        return nameSinger;
    }

    public void setNameSinger(String nameSinger) {
        this.nameSinger = nameSinger;
    }

    public int getImagePopular() {
        return imagePopular;
    }

    public void setImagePopular(int imagePopular) {
        this.imagePopular = imagePopular;
    }
}
