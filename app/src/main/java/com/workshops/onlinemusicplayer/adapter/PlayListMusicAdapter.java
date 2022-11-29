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
        TextView txtTitle = view.findViewById(R.id.txtitle);
        TextView txtSinger = view.findViewById(R.id.txtSingle);
        ImageView imgSong = view.findViewById(R.id.imgSong);
        ImageView imgHeart = view.findViewById(R.id.imgHeart);

        Music song = ds.get(i);

        txtTitle.setText(song.getName());
//        txtSingle.setText(song.getSinger());
        for (int i1=0; i1<singers.size(); i1++) {
            if (song.getSinger().equals(singers.get(i1).getId())) {
                txtSinger.setText(singers.get(i1).getName());
            }
        }
        Glide.with(context).load(song.getImage()).into(imgSong);
//        imgHeart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (!flag){
//                    imgHeart.setImageResource(R.drawable.ic_favorite_red_48);
//                    flag = true;
//                }else{
//                    imgHeart.setImageResource(R.drawable.ic_favorite_black_30);
//                    flag = false;
//                }
//            }
//        });
        return view;
    }
}
