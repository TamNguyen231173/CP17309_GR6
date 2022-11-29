package com.workshops.onlinemusicplayer.listener;

import com.workshops.onlinemusicplayer.model.Music;

import java.util.List;

public interface MusicSelectListener {
    void playQueue(List<Music> musicList);

    void setShuffleMode(boolean mode);
}