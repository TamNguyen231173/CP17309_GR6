package com.workshops.onlinemusicplayer.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.workshops.onlinemusicplayer.R;
import com.workshops.onlinemusicplayer.model.Music;

import java.util.ArrayList;

public class PlayListMusicAdapter extends BaseAdapter {
    private boolean flag = false;
    private ArrayList<Music> ds;
    private Context context;

    public PlayListMusicAdapter(ArrayList<Music> ds, Context context) {
        this.ds = ds;
        this.context = context;
    }

    @Override
    public int getCount() {
        return ds.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        view = inflater.inflate(R.layout.item_public_playlist,null);
        TextView txtTitle = view.findViewById(R.id.txtTitle);
        TextView txtSinger = view.findViewById(R.id.txtSingle);
        ImageView imgSong = view.findViewById(R.id.imgSong);
        TextView duration = view.findViewById(R.id.tv_duration);

        Music song = ds.get(i);

        txtTitle.setText(song.getName());
        txtSinger.setText(song.getSinger());
        duration.setText("dcsdf");
        Glide.with(context).load(song.getImage()).into(imgSong);

        return view;
    }
}
