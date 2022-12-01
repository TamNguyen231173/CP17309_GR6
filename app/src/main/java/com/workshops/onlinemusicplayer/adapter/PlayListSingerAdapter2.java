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

import com.bumptech.glide.Glide;
import com.workshops.onlinemusicplayer.R;
import com.workshops.onlinemusicplayer.model.PlayListSinger;
import com.workshops.onlinemusicplayer.model.RecyclerViewInterface;

import java.util.ArrayList;



public class PlayListSingerAdapter2 extends RecyclerView.Adapter<PlayListSingerAdapter2.ViewHolder> {

    private final RecyclerViewInterface recyclerViewInterface;
    private ArrayList<PlayListSinger> ds;
    private Context context;

    public PlayListSingerAdapter2(ArrayList<PlayListSinger> ds, Context context, RecyclerViewInterface recyclerViewInterface) {
        this.ds = ds;
        this.context = context;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    public void add(PlayListSinger singer) {
        ds.add(singer);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_playlist_singer2,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

//        holder.imageSinger.setImageResource(ds.get(position).getImageSinger());
        PlayListSinger singer = ds.get(position);
//        holder.NameSinger.setText(ds.get(position).getName());
        holder.NameSinger.setText(singer.getName());
        Glide.with(context).load(singer.getImage()).into(holder.imageSinger);
    }

    @Override
    public int getItemCount() {
        return ds.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageSinger;
        TextView NameSinger;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageSinger = itemView.findViewById(R.id.profile_image);
            NameSinger = itemView.findViewById(R.id.txtNameSinger1);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(recyclerViewInterface != null){
                        int pos = getAdapterPosition();
                        if(pos != RecyclerView.NO_POSITION){
                            recyclerViewInterface.onClickItem(pos);
                        }
                    }


                }
            });
        }
    }
}
