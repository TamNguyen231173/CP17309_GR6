package com.workshops.onlinemusicplayer.adapter;

import static com.workshops.onlinemusicplayer.fragment.HomeFragment.singers;

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
import com.workshops.onlinemusicplayer.model.Albums;
import com.workshops.onlinemusicplayer.model.Song;

import java.util.ArrayList;

public class AlbumAdapter extends BaseAdapter {
    private boolean flag = false;
    private ArrayList<Albums> ds;
    private Context context;

    public AlbumAdapter( ArrayList<Albums> ds, Context context) {
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
        view = inflater.inflate(R.layout.item_album,null);
        TextView txtTitle = view.findViewById(R.id.txtTitel);
        ImageView imgSong = view.findViewById(R.id.song_image);

        Albums albums = ds.get(i);

        txtTitle.setText(albums.getName());
        Glide.with(context).load(albums.getImage()).into(imgSong);
        return view;
    }

}
