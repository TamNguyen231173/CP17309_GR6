package com.workshops.onlinemusicplayer.model;

public class Song {
    private String Name;
    private String Title;
    private int Anh;
    private int File;

    public Song() {
    }

    public Song(String name, String title, int anh, int file) {
        Name = name;
        Title = title;
        Anh = anh;
        File = file;
    }
    public int getAnh() {
        return Anh;
    }

    public void setAnh(int anh) {
        Anh = anh;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Song(String name, String title, int file) {
        Name = name;
        Title = title;
        File = file;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public int getFile() {
        return File;
    }

    public void setFile(int file) {
        File = file;
    }
}
