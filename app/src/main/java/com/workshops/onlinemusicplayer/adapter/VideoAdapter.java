package com.workshops.onlinemusicplayer.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.workshops.onlinemusicplayer.R;
import com.workshops.onlinemusicplayer.model.PlayListPopular;
import com.workshops.onlinemusicplayer.model.Video;

import java.util.ArrayList;

//public class VideoAdapter extends BaseAdapter{
public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder>{
        private ArrayList<Video> ds;
        private Context context;

    public VideoAdapter(ArrayList<Video> ds, Context context) {
        this.ds = ds;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_video_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,  int position) {
        holder.youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                String videoId = ds.get(position).getVideoID();
                youTubePlayer.cueVideo(videoId, 0);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ds.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        YouTubePlayerView youTubePlayerView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            youTubePlayerView = itemView.findViewById(R.id.youtube_player_view);
        }
    }


//        private ArrayList<Video> ds;
//        private Context context;
//
//    public VideoAdapter(ArrayList<Video> ds, Context context) {
//        this.ds = ds;
//        this.context = context;
//    }
//
//    @Override
//    public int getCount() {
//        return ds.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return null;
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return 0;
//    }
//
//    @Override
//    public View getView(int i, View view, ViewGroup parent) {
//        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
//        view = inflater.inflate(R.layout.layout_video_list,null);
//
//        YouTubePlayerView youTubePlayerView = view.findViewById(R.id.youtube_player_view);
//
//        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
//            @Override
//            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
//                String videoId = ds.get(i).getVideoID();
//                youTubePlayer.cueVideo(videoId, 0);
//            }
//        });
//        return view;
//    }
}


