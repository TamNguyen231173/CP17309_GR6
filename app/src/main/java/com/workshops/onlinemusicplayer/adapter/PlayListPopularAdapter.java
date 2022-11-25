package com.workshops.onlinemusicplayer.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.workshops.onlinemusicplayer.R;
import com.workshops.onlinemusicplayer.model.PlayListPopular;
import com.workshops.onlinemusicplayer.model.PlayListSinger;
import com.workshops.onlinemusicplayer.view.MusicActivity;

import java.util.ArrayList;

public class PlayListPopularAdapter extends RecyclerView.Adapter<PlayListPopularAdapter.ViewHolder> {

    private ArrayList<PlayListPopular> ds;
    private Context context;

    public PlayListPopularAdapter(ArrayList<PlayListPopular> ds, Context context) {
        this.ds = ds;
        this.context = context;
    }

    public void add(PlayListPopular popular) {
        ds.add(popular);
        notifyDataSetChanged();
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

        PlayListPopular popular = ds.get(position);
        holder.txtNameSong.setText(popular.getName());
        holder.txtNameSinger.setText(popular.getSinger());
        Glide.with(context).load(popular.getImage()).into(holder.imagePopular);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(context, "Popular"+position, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, MusicActivity.class);
                intent.putExtra("song_id", popular.getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ds.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imagePopular;
        TextView txtNameSinger;
        TextView txtNameSong;
        CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imagePopular = itemView.findViewById(R.id.imagePopular);
            txtNameSinger = itemView.findViewById(R.id.txtNameSinger);
            txtNameSong = itemView.findViewById(R.id.txtNameSong);
            cardView = itemView.findViewById(R.id.cardViewPopular);
        }
    }
}
