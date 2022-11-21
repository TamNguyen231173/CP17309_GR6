package com.workshops.onlinemusicplayer.model;

public class Video {
    private String videoID;

    public Video() {
    }

    public Video(String videoID) {
        this.videoID = videoID;
    }

    public String getVideoID() {
        return videoID;
    }

    public void setVideoID(String videoID) {
        this.videoID = videoID;
    }
}
