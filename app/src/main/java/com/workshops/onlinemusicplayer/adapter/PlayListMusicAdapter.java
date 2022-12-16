package com.workshops.onlinemusicplayer.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.workshops.onlinemusicplayer.R;
import com.workshops.onlinemusicplayer.helper.MusicLibraryHelper;
import com.workshops.onlinemusicplayer.listener.MusicSelectListener;
import com.workshops.onlinemusicplayer.listener.PlayListListener;
import com.workshops.onlinemusicplayer.model.Music;

import java.util.List;

public class PlayListMusicAdapter extends  RecyclerView.Adapter<PlayListMusicAdapter.MyViewHolder> {
    private final List<Music> musicList;
    private final PlayListListener playListListener;
    public MusicSelectListener listener;

    public PlayListMusicAdapter(MusicSelectListener listener, PlayListListener playListListener, List<Music> musics) {
        this.listener = listener;
        this.musicList = musics;
        this.playListListener = playListListener;
    }

    @NonNull
    @Override
    public PlayListMusicAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_public_playlist, parent, false);
        return new PlayListMusicAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayListMusicAdapter.MyViewHolder holder, int position) {
        Music music = musicList.get(position);
        holder.txtTitle.setText(music.getName());
        holder.txtSinger.setText(music.getSinger());

        holder.duration.setText(MusicLibraryHelper.formatDuration(music.getId()));
        Glide.with(holder.imgSong.getContext()).load(music.getImage()).into(holder.imgSong);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return musicList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final TextView txtTitle;
        private final TextView txtSinger;
        private final TextView duration;
        private final ImageView imgSong;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtSinger = itemView.findViewById(R.id.txtSinger);
            duration = itemView.findViewById(R.id.tv_duration);
            imgSong = itemView.findViewById(R.id.imgSong);

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
