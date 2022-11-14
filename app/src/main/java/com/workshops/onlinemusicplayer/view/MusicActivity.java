package com.workshops.onlinemusicplayer.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.workshops.onlinemusicplayer.R;
import com.workshops.onlinemusicplayer.adapter.MusicAdapter;
import com.workshops.onlinemusicplayer.fragment.HomeFragment;
import com.workshops.onlinemusicplayer.model.Song;
import com.workshops.onlinemusicplayer.service.MusicService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class MusicActivity extends AppCompatActivity {
    private static final String TAG = "Read data from firebase";
    MediaPlayer media_player;
    ImageView next_btn, prev_btn, song_img, repeat_btn, shuffle_btn;
    TextView name_song_music_ac, singer_name_music_ac, time_end, time_start;
    SeekBar song_seekbar;
    ArrayList<Song> list = new ArrayList<Song>();
    ImageView play_btn;
    int position;
    List<Integer> numbers = new ArrayList<Integer>();
    Boolean repeat_song = false, shuffle_song = false;
    final int min = 0;
    int max;
    int i;
    SimpleDateFormat format_time = new SimpleDateFormat("mm:ss");
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);

        Intent intent = getIntent();
        int id_song = intent.getIntExtra("song_id", 1);
        position = id_song;

        readData();
        initViews();
        initMusic();
        showTime();

        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextSong();
            }
        });

        prev_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prevSong();
            }
        });

        play_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playAndPause();
            }
        });

        repeat_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                repeatSong();
            }
        });


        shuffle_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shuffleSong();
            }
        });

        song_seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                media_player.seekTo(song_seekbar.getProgress());
            }
        });
    }

    public void playAndPause(){
        if (media_player.isPlaying()) {
            media_player.pause();
            play_btn.setImageResource(R.drawable.play_ic);
        } else {
            media_player.start();
            play_btn.setImageResource(R.drawable.ic_baseline_pause_24);
        }
        showTime();
        UpdateTime();
    }

    public void initMusic() {
        media_player = media_player.create(MusicActivity.this, Uri.parse(list.get(position).getResource()));
        song_img.setBackgroundResource(Integer.parseInt(list.get(position).getImage()+" "));
        name_song_music_ac.setText(list.get(position).getTitle());
        singer_name_music_ac.setText(list.get(position).getSinger());
    }

    private void showTime() {
        time_end.setText(format_time.format(media_player.getDuration()));
        song_seekbar.setMax(media_player.getDuration());
    }

    private void UpdateTime() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                time_start.setText(format_time.format(media_player.getCurrentPosition()));
                //update format
                song_seekbar.setProgress(media_player.getCurrentPosition());
                //Kiểm tra tg bài hát --> nếu kết thúc next
                media_player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        nextSong();
                    }
                });
                handler.postDelayed(this, 500);
            }
        }, 100);
    }

    public void renderSong() {
        initMusic();
        media_player.start();
        play_btn.setImageResource(R.drawable.ic_baseline_pause_24);
        showTime();
        UpdateTime();
        Intent intentStop = new Intent(getApplicationContext(), MusicService.class);
        getApplicationContext().stopService(intentStop);
        Song song = list.get(position);
        Intent intent = new Intent(getApplicationContext(), MusicService.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("list_song", song);
        intent.putExtras(bundle);
        getApplicationContext().startService(intent);

    }

    public void nextSong() {
        if (!shuffle_song) {
            position++;
            if (position > list.size() - 1) {
                position = 0;
            }
        } else {
            randomSong();
        }
        media_player.stop();
        renderSong();
    }

    public void prevSong() {
        position--;
        if (position < 0) {
            position = list.size() - 1;
        }
        media_player.stop();
        renderSong();
    }

    public void repeatSong() {
        repeat_song = !repeat_song;

        if (repeat_song) {
            repeat_btn.setColorFilter(Color.rgb(255, 135, 135));
            media_player.setLooping(true);
        } else {
            repeat_btn.setColorFilter(Color.rgb(125, 125, 125));
            media_player.setLooping(false);
        }
    }

    public void shuffleSong() {
        shuffle_song = !shuffle_song;

        if (shuffle_song) {
            shuffle_btn.setColorFilter(Color.rgb(255, 135, 135));
        } else {
            shuffle_btn.setColorFilter(Color.rgb(125, 125, 125));
        }
    }

    public void randomSong() {
        max = list.size() - 1;
        while(numbers.contains(position)) {
            position = (new Random()).nextInt(max - min + 1) +min;
        }
        numbers.add(position);
        if(numbers.size() >= max) numbers.clear();
    }

    public void initViews() {
        play_btn = (ImageView) findViewById(R.id.play_btn);
        name_song_music_ac = (TextView) findViewById(R.id.name_song_music_ac);
        singer_name_music_ac = (TextView) findViewById(R.id.singer_name_music_ac);
        song_img = (ImageView) findViewById(R.id.song_image);
        next_btn = (ImageView) findViewById(R.id.next_btn);
        prev_btn = (ImageView) findViewById(R.id.prev_btn);
        time_end = (TextView) findViewById(R.id.time_end);
        song_seekbar = (SeekBar) findViewById(R.id.song_seekbar);
        time_start = (TextView) findViewById(R.id.time_start);
        repeat_btn = (ImageView) findViewById(R.id.repeat_btn);
        shuffle_btn = (ImageView) findViewById(R.id.shuffle_btn);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void readData() {
        i = 0;
        db.collection("song")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                i++;
                                String title = (String) document.getData().get("name");
                                String singer = (String) document.getData().get("singer");
                                String image = (String) document.getData().get("image");
                                String audio = (String) document.getData().get("audio");

                                list.add(new Song(i, title, singer, image, audio));
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }

                    }
                });
    }
}