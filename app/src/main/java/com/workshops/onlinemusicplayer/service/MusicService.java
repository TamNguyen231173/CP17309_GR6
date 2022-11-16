package com.workshops.onlinemusicplayer.service;

import static com.workshops.onlinemusicplayer.model.MyApplication.CHANNEL_ID;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadata;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaSessionCompat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.workshops.onlinemusicplayer.R;
import com.workshops.onlinemusicplayer.broadcast_receiver.MusicReceiver;
import com.workshops.onlinemusicplayer.fragment.HomeFragment;
import com.workshops.onlinemusicplayer.model.Song;

public class MusicService extends Service {
    private static final int ACTION_PAUSE = 1;
    private static final int ACTION_PLAY = 2;
    private static final int ACTION_NEXT = 3;
    private static final int ACTION_PREVIOUS = 4;
    private static final int ACTION_CLOSE = 5;

    private MediaPlayer player;
    private boolean isPlaying;
    private Song mSong;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Bundle bundle = intent.getExtras();
        Song song = (Song) bundle.get("list_song");

        if (song != null) {
            mSong = song;
            //startPlayMediaService(song);
            sendNotification(song);
        }

        int actionMusic = intent.getIntExtra("action_music_service", 0);
        handleActionMusic(actionMusic);

        return START_NOT_STICKY;
    }

    private void startPlayMediaService(Song song) {
        if (player == null) {
            player = MediaPlayer.create(getApplicationContext(), Uri.parse(song.getResource()));
        }
        player.start();
        player.setVolume(100, 100);
        isPlaying = true;
    }

    private void handleActionMusic(int action) {
        switch (action) {
            case ACTION_PAUSE:
                pauseMusic();
                break;
            case ACTION_PLAY:
                playMusic();
                break;
            case ACTION_NEXT:

                break;
            case ACTION_PREVIOUS:

                break;
            case ACTION_CLOSE:
                stopSelf();
                break;
        }
    }

    private void pauseMusic() {
        if (player != null && isPlaying) {
            player.pause();
            isPlaying = false;
            sendNotification(mSong);
        }
    }

    private void playMusic() {
        if (player != null && !isPlaying) {
            player.start();
            isPlaying = true;
            sendNotification(mSong);
        }
    }

    private void sendNotification(Song song) {
        Intent intent = new Intent(getApplicationContext(), HomeFragment.class);
        PendingIntent homePendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        final Bitmap[] musicThumbnail = new Bitmap[1];
        MediaSessionCompat mediaSessionCompat = new MediaSessionCompat(this, "Media Session");

        mediaSessionCompat.setMetadata(new MediaMetadataCompat.Builder()
                .putString(MediaMetadata.METADATA_KEY_TITLE, song.getTitle())
                .putString(MediaMetadata.METADATA_KEY_ARTIST, song.getSinger())
                .putString(MediaMetadata.METADATA_KEY_ALBUM_ART_URI, song.getImage())
                //.putLong(MediaMetadata.METADATA_KEY_DURATION, mediaPlayer.getDuration() / 1000)
                .build()
        );
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_logo_spotify_24)
                .setSubText("GR6")
                .setContentTitle(song.getTitle())
                .setContentText(song.getSinger())
                .setContentIntent(homePendingIntent)
                .setSound(null)
                .addAction(R.drawable.ic_skip_previous, "Previous", null)   // #0
                .addAction(R.drawable.ic_pause, "Pause", null) //#1             // #1
                .addAction(R.drawable.ic_skip_next, "Next", null)//#2
                .addAction(R.drawable.ic_close, "Close", null)  // #3
                // Apply the media style template
                .setStyle(new androidx.media.app.NotificationCompat.MediaStyle()
                        .setShowActionsInCompactView(0, 1, 2 /* #1: pause button */)
                        .setMediaSession(mediaSessionCompat.getSessionToken()));

        Glide.with(MusicService.this)
                .asBitmap()
                .load(song.getImage())
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        notificationBuilder.setLargeIcon(resource);
                        musicThumbnail[0] = resource;

//        if (isPlaying) {
//            notificationBuilder
//                    // Add media control buttons that invoke intents in your media service
//                    .addAction(R.drawable.ic_skip_previous, "Previous", null)   // #0
//                    .addAction(R.drawable.ic_pause, "Pause", getPendingIntent(this, ACTION_PAUSE)) //#1             // #1
//                    .addAction(R.drawable.ic_skip_next, "Next", null)//#2
//                    .addAction(R.drawable.ic_close, "Close", getPendingIntent(this, ACTION_CLOSE));  // #3
//        } else {
//            notificationBuilder
//                    // Add media control buttons that invoke intents in your media service
//                    .addAction(R.drawable.ic_skip_previous, "Previous", null)   // #0
//                    .addAction(R.drawable.ic_play, "Pause", getPendingIntent(this, ACTION_PLAY)) //#1             // #1
//                    .addAction(R.drawable.ic_skip_next, "Next", null)//#2
//                    .addAction(R.drawable.ic_close, "Close", getPendingIntent(this, ACTION_CLOSE));  // #3
//        }

                        Notification notification = notificationBuilder.build();

                        startForeground(1, notification);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                        notificationBuilder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.bad_guy));
                        Notification notification = notificationBuilder.build();
                        startForeground(1, notification);
                    }
                });
    }

    private PendingIntent getPendingIntent(Context context, int action) {
        Intent intent = new Intent(this, MusicReceiver.class);
        intent.putExtra("action_music", action);
        return PendingIntent.getBroadcast(context.getApplicationContext(), action, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.release();
            player = null;
        }
    }
}
