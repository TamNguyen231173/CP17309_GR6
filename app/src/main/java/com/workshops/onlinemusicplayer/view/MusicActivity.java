package com.workshops.onlinemusicplayer.view;

import androidx.appcompat.app.AppCompatActivity;

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

public class MusicActivity extends AppCompatActivity {
    MediaPlayer mediaPlayer;
    ImageView imgnext, imgprev, song_img;
    TextView txtbaihat, txttencasi, txt_timeend, txt_timestart;
    SeekBar sk_Song;
    ArrayList<Song> arraySong;
    ImageView imgPlay;
    int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        AnhXa();
        AddSong();
        KhoiTaoNhac();
        ThoiGian();

        imgnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position++;
                if (position > arraySong.size() - 1){
                    position = 0;
                }
                if (mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                }
                KhoiTaoNhac();
                mediaPlayer.start();
                imgPlay.setImageResource(R.drawable.ic_baseline_pause_24);
                ThoiGian();
                UpdateTime();
            }
        });

        imgprev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position--;
                if (position < 0){
                    position = arraySong.size() - 1;
                }
                if (mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                }
                KhoiTaoNhac();
                mediaPlayer.start();
                imgPlay.setImageResource(R.drawable.ic_baseline_pause_24);
                ThoiGian();
                UpdateTime();
            }
        });

        imgPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                    imgPlay.setImageResource(R.drawable.play_ic);
                }else {
                    mediaPlayer.start();
                    imgPlay.setImageResource(R.drawable.ic_baseline_pause_24);
                }
                ThoiGian();
                UpdateTime();

            }
        });

        sk_Song.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(sk_Song.getProgress());
            }
        });
    }

    public void KhoiTaoNhac(){
        mediaPlayer = MediaPlayer.create(MusicActivity.this, arraySong.get(position).getResource());
        song_img.setBackgroundResource(arraySong.get(position).getImage());
        txtbaihat.setText(arraySong.get(position).getTitle());
        txttencasi.setText(arraySong.get(position).getSinger());
    }

    private void ThoiGian(){
        SimpleDateFormat dinhdang = new SimpleDateFormat("mm:ss");
        txt_timeend.setText(dinhdang.format(mediaPlayer.getDuration()));
        sk_Song.setMax(mediaPlayer.getDuration());
    }

    private void UpdateTime(){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat dinhdanggio = new SimpleDateFormat("mm:ss");
                txt_timestart.setText(dinhdanggio.format(mediaPlayer.getCurrentPosition()));
                //update format
                sk_Song.setProgress(mediaPlayer.getCurrentPosition());
                //Kiểm tra tg bài hát --> nếu kết thúc next
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        position++;
                        if (position > arraySong.size() - 1){
                            position = 0;
                        }
                        if (mediaPlayer.isPlaying()){
                            mediaPlayer.stop();
                        }
                        KhoiTaoNhac();
                        mediaPlayer.start();
                        imgPlay.setImageResource(R.drawable.ic_baseline_pause_24);
                        ThoiGian();
                        UpdateTime();
                    }
                });
                handler.postDelayed(this, 500);
            }
        },100);
    }

    public void AnhXa(){
        imgPlay = (ImageView) findViewById(R.id.imgPlay);
        txtbaihat = (TextView) findViewById(R.id.txtbaihat);
        txttencasi = (TextView) findViewById(R.id.txttencasi);
        song_img = (ImageView) findViewById(R.id.song_image);
        imgnext = (ImageView) findViewById(R.id.imgNext);
        imgprev = (ImageView)  findViewById(R.id.imgPrev);
        txt_timeend = (TextView) findViewById(R.id.time_end);
        sk_Song = (SeekBar) findViewById(R.id.seekBar_Song);
        txt_timestart = (TextView) findViewById(R.id.time_start);
    }

    public void AddSong(){
        arraySong = new ArrayList<>();
        arraySong.add(new Song("Ấn nút nhớ thả giấc mơ","Sơn Tùng MTP",R.drawable.sontung2, R.raw.annutnhothagiacmo));
        arraySong.add(new Song("Chắc ai đó sẽ về","Sơn Tùng MTP",R.drawable.sontung1, R.raw.chacaidoseve));
        arraySong.add(new Song("Muộn rồi mà sao còn","Sơn Tùng MTP",R.drawable.sontung3, R.raw.muonroimasaocon));
        arraySong.add(new Song("Có chàng trai viết trên cây","Phan Mạnh Quỳnh",R.drawable.manhquynh, R.raw.cochangtraiviettrencay));
        arraySong.add(new Song("Khi người mình yêu khóc","Phan Mạnh Quỳnh",R.drawable.manhquynh2, R.raw.khinguoiminhyeukhoc));
        arraySong.add(new Song("Thật bất ngờ","Trúc Nhân",R.drawable.trucnhan, R.raw.thatbatngo));
        arraySong.add(new Song("Tình yêu màu nắng","Trúc Nhân",R.drawable.trucnhan2, R.raw.tinhyeumaunang));
        arraySong.add(new Song("Sao cha không","Phan Mạnh Quỳnh",R.drawable.manhquynh3, R.raw.saochakhong));
        arraySong.add(new Song("Có không giữ mất đừng tìm","Trúc Nhân",R.drawable.trucnhan1, R.raw.cokhonggiumatdungtim));
    }
}