package com.workshops.onlinemusicplayer.view;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.viewpager.widget.ViewPager;

import com.workshops.onlinemusicplayer.MPConstants;
import com.workshops.onlinemusicplayer.MPPreferences;
import com.workshops.onlinemusicplayer.R;
import com.workshops.onlinemusicplayer.dialog.PlayerDialog;
import com.workshops.onlinemusicplayer.adapter.MainPagerAdapter;
import com.workshops.onlinemusicplayer.listener.MusicSelectListener;
import com.workshops.onlinemusicplayer.model.Music;
import com.workshops.onlinemusicplayer.player.PlayerBuilder;
import com.workshops.onlinemusicplayer.player.PlayerListener;
import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.android.material.tabs.TabLayout;
import com.workshops.onlinemusicplayer.player.PlayerManager;

import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity
        implements MusicSelectListener, PlayerListener, View.OnClickListener {

    public static final int REQUEST_CODE = 1;

    private RelativeLayout playerView;
    private ImageView albumArt;
    private TextView songName;
    private TextView songDetails;
    private ImageButton play_pause;
    private ImageButton closePlayerViewButton;
    private LinearProgressIndicator progressIndicator;
    private PlayerDialog playerDialog;

    private PlayerBuilder playerBuilder;
    private PlayerManager playerManager;
    private boolean albumState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        permission();

        hideSystemBars();

        MPConstants.musicSelectListener = this;

        setUpUiElements();

        albumState = MPPreferences.getAlbumRequest(this);
        MPConstants.musicSelectListener = this;

        MaterialCardView playerLayout = findViewById(R.id.player_layout);
        albumArt = findViewById(R.id.albumArt);
        progressIndicator = findViewById(R.id.song_progress);
        playerView = findViewById(R.id.player_view);
        songName = findViewById(R.id.song_title);
        songDetails = findViewById(R.id.song_details);
        play_pause = findViewById(R.id.control_play_pause);
        closePlayerViewButton = findViewById(R.id.control_close_music);

        play_pause.setOnClickListener(this);
        playerLayout.setOnClickListener(this);
        closePlayerViewButton.setOnClickListener(this);
    }

    private void permission() {
        if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
        != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}
            ,REQUEST_CODE);
        }
        else{
            Toast.makeText(this, "Permission Granted !", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_CODE)
        {
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, "Permission Granted !", Toast.LENGTH_SHORT).show();
            }
            else
            {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}
                        ,REQUEST_CODE);
            }
        }
    }

    private void setPlayerView() {
        if (playerManager != null && playerManager.isPlaying()) {
            playerView.setVisibility(View.VISIBLE);
            onMusicSet(playerManager.getCurrentMusic());
        }
    }

    public void setUpUiElements() {
        playerBuilder = new PlayerBuilder(MainActivity.this, this);
        MainPagerAdapter sectionsPagerAdapter = new MainPagerAdapter(
                getSupportFragmentManager(), this);

        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        viewPager.setOffscreenPageLimit(4);

        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        for (int i = 0; i < tabs.getTabCount(); i++) {
            tabs.getTabAt(i).setIcon(MPConstants.TAB_ICONS[i]);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (playerDialog != null)
            playerDialog.dismiss();
    }

    @Override
    public void playQueue(List<Music> musicList) {
        if (musicList.size() > 0) {
            playerManager.setMusicList(musicList);
            setPlayerView();
        }
    }

    @Override
    public void setShuffleMode(boolean mode) {
        playerManager.getPlayerQueue().setShuffle(mode);
    }

    @Override
    public void onPrepared() {
        playerManager = playerBuilder.getPlayerManager();
        setPlayerView();
    }

    @Override
    public void onStateChanged(int state) {
        if (state == State.PLAYING)
            play_pause.setImageResource(R.drawable.ic_pause);
        else
            play_pause.setImageResource(R.drawable.ic_play);
    }

    @Override
    public void onPositionChanged(int position) {
        progressIndicator.setProgress(position);
    }

    @Override
    public void onMusicSet(Music music) {
        songName.setText(music.getName());
        songDetails.setText(
                String.format(Locale.getDefault(), "%s • %s",
                        music.getSinger(), music.getCategory()));
        playerView.setVisibility(View.VISIBLE);

        if (albumState)
            Glide.with(getApplicationContext())
                    .load(music.getImage())
                    .centerCrop()
                    .into(albumArt);

        if (playerManager != null && playerManager.isPlaying())
            play_pause.setImageResource(R.drawable.ic_pause);
        else
            play_pause.setImageResource(R.drawable.ic_play);
    }

    @Override
    public void onPlaybackCompleted() {
    }

    @Override
    public void onRelease() {
        playerView.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.control_play_pause)
            playerManager.playPause();

        else if (id == R.id.control_close_music)
            closePlayerView();

        else if (id == R.id.player_layout)
            setUpPlayerDialog();
    }

    private void closePlayerView() {
        if (playerView.getVisibility() == View.VISIBLE) {
            playerView.setVisibility(View.GONE);
            playerManager.pauseMediaPlayer();
        }
    }

    private void setUpPlayerDialog() {
        playerDialog = new PlayerDialog(this, playerManager);
        playerDialog.show();
    }

    private void hideSystemBars() {
        WindowInsetsControllerCompat windowInsetsController =
                ViewCompat.getWindowInsetsController(getWindow().getDecorView());
        if (windowInsetsController == null) {
            return;
        }
        windowInsetsController.setSystemBarsBehavior(
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        );
        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars());
    }
}
