package com.workshops.onlinemusicplayer.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.workshops.onlinemusicplayer.R;
import com.workshops.onlinemusicplayer.adapter.MusicAdapter;
import com.workshops.onlinemusicplayer.model.Music;

import java.util.ArrayList;

public class PlayListAlbumActivity extends AppCompatActivity {
    private TextView nameAlbum;
    private ImageView imageAlbum;
    String name;
    int i;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<Music> list = new ArrayList<Music>();
    MusicAdapter adapter;
    ListView listViewPlaylist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_list_album);
    }
}