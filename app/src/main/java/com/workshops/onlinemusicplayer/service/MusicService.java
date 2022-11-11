package com.workshops.onlinemusicplayer.service;

<<<<<<< HEAD
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.workshops.onlinemusicplayer.R;
import com.workshops.onlinemusicplayer.broadcast_receiver.MusicReceiver;

import java.security.Provider;

public class MusicService extends Service {
=======
import static com.workshops.onlinemusicplayer.model.MyApplication.CHANNEL_ID;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.media.session.MediaSessionCompat;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.workshops.onlinemusicplayer.R;
import com.workshops.onlinemusicplayer.broadcast_receiver.MusicReceiver;
import com.workshops.onlinemusicplayer.fragment.HomeFragment;
import com.workshops.onlinemusicplayer.model.Song;


public class MusicService extends Service {
    private static final int ACTION_PAUSE = 1;
    private static final int ACTION_PLAY = 2;
    private static final int ACTION_NEXT = 3;
    private static final int ACTION_PREVOUS = 4;
    private static final int ACTION_CLOSE = 5;


    private MediaPlayer player;
    private boolean isPlaying;
    private Song mSong;
>>>>>>> eb10f9575a ae2e474677a3daf15074e85f138e6e
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
<<<<<<< HEAD
=======

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Bundle bundle = intent.getExtras();
        Song song = (Song) bundle.get("list_song");

        if(song != null){
            mSong = song;
            startPlayMediaService(song);
            sendNotification(song);
        }

        int actionMusic = intent.getIntExtra("action_music_service",0);
        handleActionMusic(actionMusic);

        return START_NOT_STICKY;
    }

    private void startPlayMediaService(Song song) {
        if(player == null){
            player = MediaPlayer.create(getApplicationContext(),song.getResouce());
        }
        player.start();
        player.setVolume(100,100);
        isPlaying = true;

    }
    private void handleActionMusic(int action){
        switch (action){
            case ACTION_PAUSE:
                pauseMusic();
                break;
            case ACTION_PLAY:
                playMusic();
                break;
            case ACTION_NEXT:

                break;
            case ACTION_PREVOUS:

                break;
            case ACTION_CLOSE:
                stopSelf();
                break;
        }
    }
    private void pauseMusic(){
        if(player != null && isPlaying){
            player.pause();
            isPlaying = false;
            sendNotification(mSong);
        }
    }
    private void playMusic(){
        if(player != null && !isPlaying){
            player.start();
            isPlaying = true;
            sendNotification(mSong);

        }
    }

    private void sendNotification(Song song){
        Intent intent = new Intent(getApplicationContext(), HomeFragment.class);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), song.getImage());
        MediaSessionCompat mediaSessionCompat = new MediaSessionCompat(this,"Media Session");
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this,CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_logo_spotify_24)
                .setSubText("GR6")
                .setContentTitle(song.getTitle())
                .setContentText(song.getSingle())
                .setLargeIcon(bitmap)
                // Apply the media style template
                .setStyle(new androidx.media.app.NotificationCompat.MediaStyle()
                        .setShowActionsInCompactView(0,1,2 /* #1: pause button */)
                        .setMediaSession(mediaSessionCompat.getSessionToken()));

        if(isPlaying){
            notificationBuilder
            // Add media control buttons that invoke intents in your media service
                    .addAction(R.drawable.ic_skip_previous, "Previous", null)   // #0
                    .addAction(R.drawable.ic_pause, "Pause", getPendingIntent(this,ACTION_PAUSE)) //#1             // #1
                    .addAction(R.drawable.ic_skip_next, "Next", null)//#2
                    .addAction(R.drawable.ic_close, "Close", getPendingIntent(this,ACTION_CLOSE));  // #3
        }else{
            notificationBuilder
                    // Add media control buttons that invoke intents in your media service
                    .addAction(R.drawable.ic_skip_previous, "Previous", null)   // #0
                    .addAction(R.drawable.ic_play, "Pause", getPendingIntent(this,ACTION_PLAY)) //#1             // #1
                    .addAction(R.drawable.ic_skip_next, "Next", null)//#2
                    .addAction(R.drawable.ic_close, "Close", getPendingIntent(this,ACTION_CLOSE));  // #3
        }

        Notification notification = notificationBuilder.build();

        startForeground(1,notification);
    }
    
    private PendingIntent getPendingIntent(Context context, int action){
        Intent intent = new Intent(this, MusicReceiver.class);
        intent.putExtra("action_music",action);
        return PendingIntent.getBroadcast(context.getApplicationContext(),action,intent,PendingIntent.FLAG_UPDATE_CURRENT);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(player != null){
            player.release();
            player = null;
        }

    }
}
