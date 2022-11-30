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
import com.workshops.onlinemusicplayer.model.Albums;
import com.workshops.onlinemusicplayer.model.RecyclerViewInterface;

import java.util.ArrayList;

public class AlbumAdapter2 extends RecyclerView.Adapter<AlbumAdapter2.ViewHolder> {
    private final RecyclerViewInterface recyclerViewInterface;
    private ArrayList<Albums> ds;
    private Context context;

    public AlbumAdapter2(ArrayList<Albums> ds, Context context, RecyclerViewInterface recyclerViewInterface) {
        this.ds = ds;
        this.context = context;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    public void add(Albums albums) {
        ds.add(albums);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_album2,parent,false);
        return new AlbumAdapter2.ViewHolder(view);
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
