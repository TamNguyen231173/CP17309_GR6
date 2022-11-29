package com.workshops.onlinemusicplayer;

import com.workshops.onlinemusicplayer.listener.MusicSelectListener;

public class MPConstants {
    public static final String PACKAGE_NAME = "com.workshops.onlinemusicplayer";

    public static final String MEDIA_SESSION_TAG = "com.workshops.onlinemusicplayer.MediaSession";

    public static final int NOTIFICATION_ID = 101;
    public static final String PLAY_PAUSE_ACTION = "com.workshops.onlinemusicplayer.PLAYPAUSE";
    public static final String NEXT_ACTION = "com.workshops.onlinemusicplayer.NEXT";
    public static final String PREV_ACTION = "com.workshops.onlinemusicplayer.PREV";
    public static final String CLOSE_ACTION = "com.workshops.onlinemusicplayer.CLOSE";
    public static final String CHANNEL_ID = "com.workshops.onlinemusicplayer.CHANNEL_ID";
    public static final int REQUEST_CODE = 100;

    public static final float VOLUME_DUCK = 0.2f;
    public static final float VOLUME_NORMAL = 1.0f;
    public static final int AUDIO_NO_FOCUS_NO_DUCK = 0;
    public static final int AUDIO_NO_FOCUS_CAN_DUCK = 1;
    public static final int AUDIO_FOCUSED = 2;


    public static final int[] TAB_ICONS = new int[]{
            R.drawable.home,
            R.drawable.explore,
            R.drawable.heart,
            R.drawable.user
    };

    public static final String SETTINGS_ALBUM_REQUEST = "shared_pref_album_request";
    public static final String SETTINGS_EXCLUDED_FOLDER = "shared_pref_excluded_folders";

    public static MusicSelectListener musicSelectListener;
}
