package com.workshops.onlinemusicplayer.player;

import static com.workshops.onlinemusicplayer.MPConstants.CHANNEL_ID;
import static com.workshops.onlinemusicplayer.MPConstants.CLOSE_ACTION;
import static com.workshops.onlinemusicplayer.MPConstants.NEXT_ACTION;
import static com.workshops.onlinemusicplayer.MPConstants.NOTIFICATION_ID;
import static com.workshops.onlinemusicplayer.MPConstants.PLAY_PAUSE_ACTION;
import static com.workshops.onlinemusicplayer.MPConstants.PREV_ACTION;
import static com.workshops.onlinemusicplayer.MPConstants.REQUEST_CODE;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.bumptech.glide.Glide;
import com.workshops.onlinemusicplayer.R;
import com.workshops.onlinemusicplayer.helper.MusicLibraryHelper;
import com.workshops.onlinemusicplayer.model.Music;
import com.workshops.onlinemusicplayer.view.MainActivity;

import java.util.concurrent.ExecutionException;

public class PlayerNotificationManager {

    private final NotificationManager notificationManager;
    private final PlayerService playerService;
    private NotificationCompat.Builder notificationBuilder;

    PlayerNotificationManager(@NonNull final PlayerService playerService) {
        this.playerService = playerService;
        notificationManager = (NotificationManager) playerService.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    public final NotificationManager getNotificationManager() {
        return notificationManager;
    }

    private PendingIntent playerAction(@NonNull final String action) {
        final Intent pauseIntent = new Intent();
        pauseIntent.setAction(action);

        return PendingIntent.getBroadcast(playerService, REQUEST_CODE, pauseIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_MUTABLE);
    }

    private int getDominantColor(Bitmap bitmap) {
        if (bitmap == null) return Color.BLACK;

        Bitmap newBitmap = Bitmap.createScaledBitmap(bitmap, 1, 1, true);
        final int color = newBitmap.getPixel(0, 0);
        newBitmap.recycle();
        return color;
    }

    public Notification createNotification() {
        final Music song = playerService.getPlayerManager().getCurrentMusic();
        notificationBuilder = new NotificationCompat.Builder(playerService, CHANNEL_ID);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel();
        }

        final Intent openPlayerIntent = new Intent(playerService, MainActivity.class);
        openPlayerIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        final PendingIntent contentIntent = PendingIntent.getActivity(playerService, REQUEST_CODE,
                openPlayerIntent, PendingIntent.FLAG_MUTABLE);
        Bitmap albumArt = null;
//        try {
//            albumArt  = Glide.with(playerService.getApplicationContext()).asBitmap().load(song.getImage()).submit().get();
//        } catch (final ExecutionException | InterruptedException e) {
//            Log.e(">>>>>>>>Bitmap error", e.getMessage());
//        }

        notificationBuilder
                .setShowWhen(false)
                .setSmallIcon(R.drawable.ic_logo_spotify_24)
                .setContentTitle(song.getName())
                .setContentText(song.getSinger())
                .setProgress(100, playerService.getPlayerManager().getCurrentPosition(), true)
//                .setColor(getDominantColor(albumArt))
                .setColorized(false)
                .setContentIntent(contentIntent)
                .setAutoCancel(true)
//                .setLargeIcon(albumArt)
                .addAction(notificationAction(PREV_ACTION))
                .addAction(notificationAction(PLAY_PAUSE_ACTION))
                .addAction(notificationAction(NEXT_ACTION))
                .addAction(notificationAction(CLOSE_ACTION))
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC);

        notificationBuilder.setStyle(new androidx.media.app.NotificationCompat.MediaStyle().setShowActionsInCompactView(0, 1, 2));
        return notificationBuilder.build();
    }

    @SuppressLint("RestrictedApi")
    public void updateNotification() {
        if (notificationBuilder == null)
            return;

        notificationBuilder.setOngoing(playerService.getPlayerManager().isPlaying());
        PlayerManager playerManager = playerService.getPlayerManager();
        Music song = playerManager.getCurrentMusic();
//        Bitmap albumArt = MusicLibraryHelper.getThumbnail(playerService.getApplicationContext(),
//                song.getImage());

        if (notificationBuilder.mActions.size() > 0)
            notificationBuilder.mActions.set(1, notificationAction(PLAY_PAUSE_ACTION));

//        notificationBuilder
//                .setLargeIcon(albumArt)
//                .setColor(getDominantColor(albumArt));

        notificationBuilder
                .setContentTitle(song.getName())
                .setContentText(song.getSinger())
                .setColorized(false)
                .setSubText(song.getCategory());


        NotificationManagerCompat.from(playerService).notify(NOTIFICATION_ID, notificationBuilder.build());
    }

    @NonNull
    private NotificationCompat.Action notificationAction(@NonNull final String action) {
        int icon = R.drawable.ic_pause;

        switch (action) {
            case PREV_ACTION:
                icon = R.drawable.ic_skip_previous;
                break;
            case PLAY_PAUSE_ACTION:
                icon = playerService.getPlayerManager().isPlaying() ? R.drawable.ic_pause : R.drawable.ic_play;
                break;
            case NEXT_ACTION:
                icon = R.drawable.ic_skip_next;
                break;
            case CLOSE_ACTION:
                icon = R.drawable.ic_close;
                break;
        }
        return new NotificationCompat.Action.Builder(icon, action, playerAction(action)).build();
    }

    @RequiresApi(26)
    private void createNotificationChannel() {

        if (notificationManager.getNotificationChannel(CHANNEL_ID) == null) {
            final NotificationChannel notificationChannel =
                    new NotificationChannel(CHANNEL_ID,
                            playerService.getString(R.string.app_name),
                            NotificationManager.IMPORTANCE_LOW);

            notificationChannel.setDescription(
                    playerService.getString(R.string.app_name));

            notificationChannel.enableLights(false);
            notificationChannel.enableVibration(false);
            notificationChannel.setShowBadge(false);

            notificationManager.createNotificationChannel(notificationChannel);
        }
    }
}
