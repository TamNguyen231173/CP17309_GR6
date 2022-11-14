package com.workshops.onlinemusicplayer.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.workshops.onlinemusicplayer.R;
import com.workshops.onlinemusicplayer.adapter.MusicAdapter;
import com.workshops.onlinemusicplayer.adapter.TrendAdapter;
import com.workshops.onlinemusicplayer.model.Song;
import com.workshops.onlinemusicplayer.service.MusicService;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    ArrayList<Song> list;
    ListView listViewPlaylist;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        //List view song
        listViewPlaylist = view.findViewById(R.id.listViewPlaylist);
        list = new ArrayList<Song>();

        list = new ArrayList<>();
        list.add(new Song("Survival","Drake",R.drawable.drake,R.raw.survival));
        list.add(new Song("Bad Guy","Billie Eilish",R.drawable.bad_guy,R.raw.bad_guy));
        list.add(new Song("Comethru","Drake",R.drawable.bi,R.raw.comethru));

        listViewPlaylist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intentStop = new Intent(getContext(), MusicService.class);
                getContext().stopService(intentStop);
                Song song = list.get(i);
                Intent intent = new Intent(getContext(), MusicService.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("list_song",song);
                intent.putExtras(bundle);
                getContext().startService(intent);
            }
        });

        MusicAdapter adapter = new MusicAdapter(list,getContext());
        listViewPlaylist.setAdapter(adapter);
        //end listView song

        //tablayout
        TabLayout tabLayout = view.findViewById(R.id.tab_layout_fragment);
        ViewPager2 viewPager2 = view.findViewById(R.id.view_pager);
        viewPager2.setSaveEnabled(false);
        TrendAdapter demoAdapter = new TrendAdapter(getChildFragmentManager(), getLifecycle());
        viewPager2.setAdapter(demoAdapter);

        new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("News");
                    break;
                case 1:
                    tab.setText("Video");
                    break;
                case 2:
                    tab.setText("Artists");
                    break;
                case 3:
                    tab.setText("Podcast");
                    break;
            }
        }).attach();
        //end tablayout

        return view;
    }
}

