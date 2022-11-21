package com.workshops.onlinemusicplayer.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.workshops.onlinemusicplayer.R;
import com.workshops.onlinemusicplayer.model.PlayListPopular;

import java.util.ArrayList;

public class PlayListPopularAdapter extends RecyclerView.Adapter<PlayListPopularAdapter.ViewHolder> {

    private ArrayList<PlayListPopular> ds;
    private Context context;

    public PlayListPopularAdapter(ArrayList<PlayListPopular> ds, Context context) {
        this.ds = ds;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_playlist_popular,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.imagePopular.setBackgroundResource(ds.get(position).getImagePopular());
        holder.txtNameSinger.setText(ds.get(position).getNameSinger());
        holder.txtNameSong.setText(ds.get(position).getNameSong());
    }

    @Override
    public int getItemCount() {
        return ds.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imagePopular;
        TextView txtNameSinger;
        TextView txtNameSong;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imagePopular = itemView.findViewById(R.id.imagePopular);
            txtNameSinger = itemView.findViewById(R.id.txtNameSinger);
            txtNameSong = itemView.findViewById(R.id.txtNameSong);
        }
    }
}
