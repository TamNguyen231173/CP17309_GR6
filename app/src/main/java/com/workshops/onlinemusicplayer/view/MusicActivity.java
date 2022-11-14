package com.workshops.onlinemusicplayer.view;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.workshops.onlinemusicplayer.R;
import com.workshops.onlinemusicplayer.model.Song;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class MusicActivity extends AppCompatActivity {
    MediaPlayer media_player;
    ImageView next_btn, prev_btn, song_img, repeat_btn, shuffle_btn;
    TextView name_song_music_ac, singer_name_music_ac, time_end, time_start;
    SeekBar song_seekbar;
    ArrayList<Song> array_song;
    ImageView play_btn;
    int position;
    List<Integer> numbers = new ArrayList<Integer>();
    Boolean repeat_song = false, shuffle_song = false;
    final int min = 0;
    int max;
    SimpleDateFormat format_time = new SimpleDateFormat("mm:ss");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        initViews();
        AddSong();
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

    public void initMusic() {
        media_player = media_player.create(MusicActivity.this, array_song.get(position).getResource());
        song_img.setBackgroundResource(array_song.get(position).getImage());
        name_song_music_ac.setText(array_song.get(position).getTitle());
        singer_name_music_ac.setText(array_song.get(position).getSinger());
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
    }

    public void nextSong() {
        if (!shuffle_song) {
            position++;
            if (position > array_song.size() - 1) {
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
            position = array_song.size() - 1;
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
        max = array_song.size() - 1;
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

    public void AddSong() {
        array_song = new ArrayList<>();
        array_song.add(new Song("Ấn nút nhớ thả giấc mơ", "Sơn Tùng MTP", R.drawable.sontung2, R.raw.annutnhothagiacmo));
        array_song.add(new Song("Chắc ai đó sẽ về", "Sơn Tùng MTP", R.drawable.sontung1, R.raw.chacaidoseve));
        array_song.add(new Song("Muộn rồi mà sao còn", "Sơn Tùng MTP", R.drawable.sontung3, R.raw.muonroimasaocon));
        array_song.add(new Song("Có chàng trai viết trên cây", "Phan Mạnh Quỳnh", R.drawable.manhquynh, R.raw.cochangtraiviettrencay));
        array_song.add(new Song("Khi người mình yêu khóc", "Phan Mạnh Quỳnh", R.drawable.manhquynh2, R.raw.khinguoiminhyeukhoc));
        array_song.add(new Song("Thật bất ngờ", "Trúc Nhân", R.drawable.trucnhan, R.raw.thatbatngo));
        array_song.add(new Song("Tình yêu màu nắng", "Trúc Nhân", R.drawable.trucnhan2, R.raw.tinhyeumaunang));
        array_song.add(new Song("Sao cha không", "Phan Mạnh Quỳnh", R.drawable.manhquynh3, R.raw.saochakhong));
        array_song.add(new Song("Có không giữ mất đừng tìm", "Trúc Nhân", R.drawable.trucnhan1, R.raw.cokhonggiumatdungtim));
    }
}