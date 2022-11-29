package com.workshops.onlinemusicplayer.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.workshops.onlinemusicplayer.R;
import com.workshops.onlinemusicplayer.model.Albums;

import java.util.ArrayList;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.ViewHolder> {
    private ArrayList<Albums> ds;
    private Context context;

    public AlbumAdapter(ArrayList<Albums> ds, Context context) {
        this.ds = ds;
        this.context = context;
    }

    public void add(Albums albums) {
        ds.add(albums);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_album,parent,false);
        return new AlbumAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Albums albums = ds.get(position);
        holder.NameAlbum.setText(albums.getName());
        Glide.with(context).load(albums.getImage()).into(holder.imageAlbum);

    }

    @Override
    public int getItemCount() {
        return ds.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageAlbum;
        TextView NameAlbum;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageAlbum = itemView.findViewById(R.id.song_image);
            NameAlbum = itemView.findViewById(R.id.txtTitel);
        }
    }

}
