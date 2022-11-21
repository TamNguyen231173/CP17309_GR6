package com.workshops.onlinemusicplayer.model;

public class PlayListSinger {
    private String nameSinger;
    private int imageSinger;

    public PlayListSinger() {
    }

    public PlayListSinger(String nameSinger, int imageSinger) {
        this.nameSinger = nameSinger;
        this.imageSinger = imageSinger;
    }

    public String getNameSinger() {
        return nameSinger;
    }

    public void setNameSinger(String nameSinger) {
        this.nameSinger = nameSinger;
    }

    public int getImageSinger() {
        return imageSinger;
    }

    public void setImageSinger(int imageSinger) {
        this.imageSinger = imageSinger;
    }
}
