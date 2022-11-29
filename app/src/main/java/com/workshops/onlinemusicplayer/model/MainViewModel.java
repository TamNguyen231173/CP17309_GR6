package com.workshops.onlinemusicplayer.model;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.workshops.onlinemusicplayer.helper.MusicLibraryHelper;
import com.workshops.onlinemusicplayer.listener.CallBackDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainViewModel extends ViewModel {

    public List<Music> songsList = new ArrayList<>();

    public MainViewModel(Context context) {
        initSongList(context);
    }

    private void initSongList(Context context) {
        List<Music> musicList = MusicLibraryHelper.fetchMusicLibrary(context, new CallBackDatabase() {
            @Override
            public List<Music> onCallback(List<Music> result) {
                return result;
            }
        });

        songsList.addAll(musicList);

    }

    public List<Music> getSongs(boolean reverse) {
        if (reverse)
            Collections.reverse(songsList);

        return songsList;
    }

}

