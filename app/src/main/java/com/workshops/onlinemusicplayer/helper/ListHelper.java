package com.workshops.onlinemusicplayer.helper;

import com.workshops.onlinemusicplayer.model.Music;

import java.util.ArrayList;
import java.util.List;

import kotlin.collections.CollectionsKt;

public class ListHelper {

    public static List<Music> searchMusicByName(List<Music> list, String query) {
        List<Music> newList = new ArrayList<>(list);
        return CollectionsKt.filter(newList, music ->
                (music.getName().toLowerCase().contains(query) || music.getSinger().toLowerCase().contains(query)) ||
                        (music.getCategory().toLowerCase().contains(query))
        );
    }


    public static String ifNull(String val) {
        return val == null ? "" : val;
    }
}
