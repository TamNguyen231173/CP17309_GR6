package com.workshops.onlinemusicplayer.listener;

import android.content.Context;
import com.workshops.onlinemusicplayer.model.Music;

public interface PlayListListener {
    void option(Context context, Music music);
}
