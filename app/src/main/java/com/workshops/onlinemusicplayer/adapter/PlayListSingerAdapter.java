package com.workshops.onlinemusicplayer.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.workshops.onlinemusicplayer.R;
import com.workshops.onlinemusicplayer.model.PlayListSinger;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class PlayListSingerAdapter extends RecyclerView.Adapter<PlayListSingerAdapter.ViewHolder> {

    private ArrayList<PlayListSinger> ds;
    private Context context;

    public PlayListSingerAdapter(ArrayList<PlayListSinger> ds, Context context) {
        this.ds = ds;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_playlist_singer,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.imageSinger.setImageResource(ds.get(position).getImageSinger());
        holder.txtNameSinger.setText(ds.get(position).getNameSinger());
    }

    @Override
    public int getItemCount() {
        return ds.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView imageSinger;
        TextView txtNameSinger;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageSinger = itemView.findViewById(R.id.profile_image);
            txtNameSinger = itemView.findViewById(R.id.txtNameSinger);
        }
    }
}
