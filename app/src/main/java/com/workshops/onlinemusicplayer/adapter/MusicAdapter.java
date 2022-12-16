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
import com.google.firebase.firestore.FirebaseFirestore;
import com.workshops.onlinemusicplayer.R;
import com.workshops.onlinemusicplayer.model.Music;

import java.util.ArrayList;

public class MusicAdapter extends BaseAdapter {
    private static boolean flag;
    private ArrayList<Music> ds;
    private Context context;
//    private FirebaseDatabase db;

    public MusicAdapter(ArrayList<Music> ds, Context context) {
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
        view = inflater.inflate(R.layout.layout_list_view,null);
        TextView txtTitle = view.findViewById(R.id.txtitle);
        TextView txtSinger = view.findViewById(R.id.txtSingle);
        ImageView imgSong = view.findViewById(R.id.imgSong);
        ImageView imgHeart = view.findViewById(R.id.imgHeart);

        Music music = ds.get(i);

        txtTitle.setText(music.getName());
        txtSinger.setText(music.getSinger());
        Glide.with(context).load(music.getImage()).into(imgSong);
        flag = music.isFlag();
        if (flag) {
            imgHeart.setImageResource(R.drawable.ic_favorite_red_48);
        }
        imgHeart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!flag){
                    imgHeart.setImageResource(R.drawable.ic_favorite_red_48);
                    flag = true;
                    FirebaseFirestore.getInstance()
                            .collection("song")
                            .document(music.getId_flag())
                            .update("flag", true);
                }else{
                    imgHeart.setImageResource(R.drawable.ic_favorite_black_30);
                    flag = false;
                    FirebaseFirestore.getInstance()
                            .collection("song")
                            .document(music.getId_flag())
                            .update("flag", false);
                }
            }
        });
        return view;
    }
}

