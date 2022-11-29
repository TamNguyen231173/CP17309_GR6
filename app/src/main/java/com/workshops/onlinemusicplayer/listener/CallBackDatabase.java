package com.workshops.onlinemusicplayer.listener;

import com.workshops.onlinemusicplayer.model.Music;

import java.util.List;

public interface CallBackDatabase {
        List<Music> onCallback(List<Music> result);
}
