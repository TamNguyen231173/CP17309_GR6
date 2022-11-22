package com.workshops.onlinemusicplayer.service;

import static com.workshops.onlinemusicplayer.model.MyApplication.CHANNEL_ID;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import android.util.Log;

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
    private static final int ACTION_PLAY_OR_PAUSE = 1;
    private static final int ACTION_NEXT = 2;
    private static final int ACTION_PREVIOUS = 3;
    private static final int ACTION_CLOSE = 4;


    public static final String EVENT_PLAY_OR_PAUSE = "EVENT_ACTION_PLAY_OR_PAUSE";
    public static final String EVENT_ACTION_NEXT = "EVENT_ACTION_NEXT";
    public static final String EVENT_ACTION_PREVIOUS = "EVENT_ACTION_PREVIOUS";
    public static final String EVENT_ACTION_CLOSE = "EVENT_ACTION_CLOSE";

    private MediaPlayer player;
    private boolean notifyPlay = false;
    private Song mSong;
    IntentFilter intentFilter;

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
            sendNotification(song);
        }

        intentFilter = new IntentFilter();
        intentFilter.addAction("PlayOrPause");

        int actionMusic = intent.getIntExtra("action_music_service", 0);
        handleActionMusic(actionMusic);

        return START_NOT_STICKY;
    }

    private void handleActionMusic(int action) {
        switch (action) {
            case ACTION_PLAY_OR_PAUSE:
                playOrPause();
                break;
            case ACTION_NEXT:
                nextMusic();
                break;
            case ACTION_PREVIOUS:
                previousMusic();
                break;
            case ACTION_CLOSE:
                closeMusic();
                break;
        }
    }

    private void closeMusic() {
        stopSelf();
        notifyPlay = false;
        Intent intentPause = new Intent();
        intentPause.putExtra("action", EVENT_ACTION_CLOSE);
        sendBroadcast(intentPause);
    }

    private void nextMusic() {
        notifyPlay = true;
        sendNotification(mSong);
        Intent intentNext = new Intent();
        intentNext.setAction("Next");
        intentNext.putExtra("action", EVENT_ACTION_NEXT);
        sendBroadcast(intentNext);

    }

    private void previousMusic() {
        notifyPlay = true;
        sendNotification(mSong);
        Intent intentPrevious = new Intent();
        intentPrevious.setAction("Previous");
        intentPrevious.putExtra("action", EVENT_ACTION_PREVIOUS);
        sendBroadcast(intentPrevious);
    }

    private void playOrPause() {
        Intent intent = new Intent();
        intent.setAction("PlayOrPause");
        intent.putExtra("action", EVENT_PLAY_OR_PAUSE);
        sendBroadcast(intent);
        notifyPlay = !notifyPlay;
        sendNotification(mSong);
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

                        if (!notifyPlay) {
                            notificationBuilder
                                    .addAction(R.drawable.ic_skip_previous, "Previous", getPendingIntent(MusicService.this, ACTION_PREVIOUS))
                                    .addAction(R.drawable.ic_play, "Play", getPendingIntent(MusicService.this, ACTION_PLAY_OR_PAUSE))
                                    .addAction(R.drawable.ic_skip_next, "Next", getPendingIntent(MusicService.this, ACTION_NEXT))
                                    .addAction(R.drawable.ic_close, "Close", getPendingIntent(MusicService.this, ACTION_NEXT));
                        } else {
                            notificationBuilder
                                    .addAction(R.drawable.ic_skip_previous, "Previous", getPendingIntent(MusicService.this, ACTION_PREVIOUS))
                                    .addAction(R.drawable.ic_pause, "Pause", getPendingIntent(MusicService.this, ACTION_PLAY_OR_PAUSE))
                                    .addAction(R.drawable.ic_skip_next, "Next", getPendingIntent(MusicService.this, ACTION_NEXT))
                                    .addAction(R.drawable.ic_close, "Close", getPendingIntent(MusicService.this, ACTION_NEXT));
                        }

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

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(">>>>>>>>>>>>TAG", "Is playing: " + notifyPlay);
            notifyPlay = intent.getBooleanExtra("isPlaying", !notifyPlay);
        }
    };
}
