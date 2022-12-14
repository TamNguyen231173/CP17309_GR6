package com.workshops.onlinemusicplayer.fragment.homeTabLayout;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.workshops.onlinemusicplayer.R;
import com.workshops.onlinemusicplayer.adapter.VideoAdapter;
import com.workshops.onlinemusicplayer.model.Video;

import java.util.ArrayList;

public class VideoFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    private RecyclerView videoList;
    private VideoAdapter adapter;
    private LinearLayoutManager layoutManagerPopular;
    private ArrayList<Video> list = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_video, container, false);
        videoList = view.findViewById(R.id.videoList);

        list.add(new Video("YGhE2HZ8g8M"));
        list.add(new Video("nG1-7gExImU"));
        list.add(new Video("Hzbr4jMxvBk"));
        list.add(new Video("wssbBe_t-r4"));
        list.add(new Video("dV-znS6RPbQ"));
        list.add(new Video("KRaWnd3LJfs"));
        list.add(new Video("-uFQzcY7YHc"));
        list.add(new Video("xpVfcZ0ZcFM"));
        list.add(new Video("cHHLHGNpCSA"));
        videoList = view.findViewById(R.id.videoList);
        adapter = new VideoAdapter(list,getActivity());

        videoList.setAdapter(adapter);
        layoutManagerPopular = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL, false);
        videoList.setLayoutManager(layoutManagerPopular);
        videoList.setAdapter(adapter);


        return view;
    }
}