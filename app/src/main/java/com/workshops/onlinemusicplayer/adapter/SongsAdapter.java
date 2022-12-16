package com.workshops.onlinemusicplayer.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.workshops.onlinemusicplayer.MPPreferences;
import com.workshops.onlinemusicplayer.R;
import com.workshops.onlinemusicplayer.helper.MusicLibraryHelper;
import com.workshops.onlinemusicplayer.listener.MusicSelectListener;
import com.workshops.onlinemusicplayer.listener.PlayListListener;
import com.workshops.onlinemusicplayer.model.Music;
import com.bumptech.glide.Glide;

import java.util.List;
import java.util.Locale;

public class SongsAdapter extends RecyclerView.Adapter<SongsAdapter.MyViewHolder> {

    private final List<Music> musicList;
    private final PlayListListener playListListener;
    public MusicSelectListener listener;
    private boolean flag;

    public SongsAdapter(MusicSelectListener listener, PlayListListener playListListener, List<Music> musics) {
        this.listener = listener;
        this.musicList = musics;
        this.playListListener = playListListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_songs, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Music music = musicList.get(position);
        holder.songName.setText(music.getName());
        holder.albumName.setText(
                String.format(Locale.getDefault(), "%s • %s",
                        music.getSinger(),
                        music.getCategory())
        );
        flag = music.isFlag();
        if (flag) {
            holder.likeBtn.setImageResource(R.drawable.ic_favorite_red_48);
        }
        holder.likeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!flag) {
                    holder.likeBtn.setBackgroundResource(R.drawable.ic_favorite_red_48);
                    music.setFlag(true);
                    FirebaseFirestore.getInstance()
                            .collection("song")
                            .document()
                            .update("flag", true);
                } else {
                    holder.likeBtn.setBackgroundResource(R.drawable.ic_favorite_black_30);
                    music.setFlag(false);
                    FirebaseFirestore.getInstance()
                            .collection("song")
                            .document()
                            .update("flag", false);
                }
            }
        });


        holder.songHistory.setText(MusicLibraryHelper.formatDuration(music.getId()));
        Glide.with(holder.albumArt.getContext()).load(music.getImage()).into(holder.albumArt);
    }

    @Override
    public int getItemCount() {
        return musicList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final TextView songName;
        private final TextView albumName;
        private final TextView songHistory;
        private final ImageView albumArt;
        private final ImageView likeBtn;
        private final boolean state;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            state = MPPreferences.getAlbumRequest(itemView.getContext());
            albumArt = itemView.findViewById(R.id.album_art_item);
            songHistory = itemView.findViewById(R.id.song_history);
            songName = itemView.findViewById(R.id.song_name);
            albumName = itemView.findViewById(R.id.song_album);
            likeBtn = itemView.findViewById(R.id.ic_heart_border);

            itemView.findViewById(R.id.root_layout).setOnClickListener(v -> {
                listener.setShuffleMode(false);
                listener.playQueue(musicList.subList(getAdapterPosition(), musicList.size()));
            });

            itemView.findViewById(R.id.root_layout).setOnLongClickListener(v -> {
                playListListener.option(itemView.getContext(), musicList.get(getAdapterPosition()));
                return true;
            });
        }
    }
}
