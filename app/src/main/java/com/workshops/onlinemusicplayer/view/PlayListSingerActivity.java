package com.workshops.onlinemusicplayer.view;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.workshops.onlinemusicplayer.R;
import com.workshops.onlinemusicplayer.adapter.MusicAdapter;
import com.workshops.onlinemusicplayer.model.Music;

import java.util.ArrayList;

public class PlayListSingerActivity extends AppCompatActivity {
    private TextView nameSinger;
    private ImageView imageSinger;
    String name;
    int i;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<Music> list = new ArrayList<Music>();
    MusicAdapter adapter;
    ListView listViewPlaylist;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_list_singer);

        nameSinger = findViewById(R.id.nameSinger);
        imageSinger = findViewById(R.id.imageSinger);

        name = getIntent().getStringExtra("name");
        String image = getIntent().getStringExtra("image");

        nameSinger.setText(name);
        Picasso.get().load(image).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                imageSinger.setBackground(new BitmapDrawable(bitmap));
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });
        listViewPlaylist = findViewById(R.id.listSong);

        listViewPlaylist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Music song = list.get(i);
                Intent intent = new Intent(PlayListSingerActivity.this, MusicActivity.class);
                intent.putExtra("song_id", song.getId());
                startActivity(intent);
            }
        });

        db.collection("song")
                .whereEqualTo("singer", name)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                i++;
                                String title = (String) document.getData().get("name");
                                String singer = (String) document.getData().get("id_singer");
                                String image = (String) document.getData().get("image");

                                list.add(new Music(i, title, singer, image));
                            }
                            adapter = new MusicAdapter(list, PlayListSingerActivity.this);
                            listViewPlaylist.setAdapter(adapter);

                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

}