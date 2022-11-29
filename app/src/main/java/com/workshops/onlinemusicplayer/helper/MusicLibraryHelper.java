package com.workshops.onlinemusicplayer.helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.workshops.onlinemusicplayer.listener.CallBackDatabase;
import com.workshops.onlinemusicplayer.model.Music;

import java.lang.annotation.Target;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class MusicLibraryHelper {

    private static final String TAG = "ReadDataFromFirebase";
    private static final FirebaseFirestore mDatabase = FirebaseFirestore.getInstance();
    private static int id = 0;
    static long lastActive = new Date().getTime();

    public static List<Music> fetchMusicLibrary(Context context, CallBackDatabase callback) {

        List<Music> musicList = new ArrayList<>();

        mDatabase.collection("song")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                String title = (String) document.getData().get("name");
                                String singer = (String) document.getData().get("singer");
                                String image = (String) document.getData().get("image");
                                String audio = (String) document.getData().get("audio");
                                String lyrics = (String) document.getData().get("lyrics");

                                musicList.add(new Music(id, title, singer, image, audio, lyrics));
                                id++;
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                        callback.onCallback(musicList);
                    }
                });
        return musicList;
    }

    public static Bitmap getThumbnail(Context context, String url) {
        try {
            Bitmap bitmap  = Glide.with(context).asBitmap().load(url).submit().get();
            return bitmap;
        } catch (final ExecutionException | InterruptedException e) {
            Log.e(TAG, e.getMessage());
        }
        return null;
    }

    public static String formatDuration(long duration) {
        long seconds = TimeUnit.MILLISECONDS.toSeconds(duration);
        String second = String.valueOf(seconds);

        if (second.length() == 1)
            second = "0" + second;
        else
            second = second.substring(0, 2);

        return String.format(Locale.getDefault(), "%02dm %ss",
                TimeUnit.MILLISECONDS.toMinutes(duration),
                second
        );
    }

    public static String formatDurationTimeStyle(long duration) {
        long seconds = TimeUnit.MILLISECONDS.toSeconds(duration);
        String second = String.valueOf(seconds);

        if (second.length() == 1)
            second = "0" + second;
        else
            second = second.substring(0, 2);

        return String.format(Locale.getDefault(), "%02d:%s",
                TimeUnit.MILLISECONDS.toMinutes(duration),
                second
        );
    }

    public static String formatDate(long dateAdded) {
        SimpleDateFormat fromFormat = new SimpleDateFormat("s", Locale.getDefault());
        SimpleDateFormat toFormat = new SimpleDateFormat("d MMM yyyy", Locale.getDefault());

        try {
            Date date = fromFormat.parse(String.valueOf(dateAdded));
            assert date != null;
            return toFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }
}
