package com.workshops.onlinemusicplayer.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.workshops.onlinemusicplayer.R;
import com.workshops.onlinemusicplayer.adapter.MusicAdapter;
import com.workshops.onlinemusicplayer.adapter.TrendAdapter;
import com.workshops.onlinemusicplayer.model.Song;
import com.workshops.onlinemusicplayer.service.MusicService;
import com.workshops.onlinemusicplayer.view.MusicActivity;

import java.util.ArrayList;
import java.util.Map;

public class HomeFragment extends Fragment {
    ArrayList<Song> list;
    ListView listViewPlaylist;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        //List view song
        listViewPlaylist = view.findViewById(R.id.listViewPlaylist);
//        list = new ArrayList<Song>();
//
//        list.add(new Song(1, "Survival","Drake",R.drawable.drake,R.raw.survival));
//        list.add(new Song(2, "Bad Guy","Billie Eilish",R.drawable.bad_guy,R.raw.bad_guy));
//        list.add(new Song(3, "Comethru","Drake",R.drawable.bi,R.raw.comethru));
//        list.add(new Song(4, "Ấn nút nhớ thả giấc mơ", "Sơn Tùng MTP", R.drawable.sontung2, R.raw.annutnhothagiacmo));
//        list.add(new Song(5, "Chắc ai đó sẽ về", "Sơn Tùng MTP", R.drawable.sontung1, R.raw.chacaidoseve));
//        list.add(new Song(6, "Muộn rồi mà sao còn", "Sơn Tùng MTP", R.drawable.sontung3, R.raw.muonroimasaocon));
//        list.add(new Song(7, "Có chàng trai viết trên cây", "Phan Mạnh Quỳnh", R.drawable.manhquynh, R.raw.cochangtraiviettrencay));
//        list.add(new Song(8, "Khi người mình yêu khóc", "Phan Mạnh Quỳnh", R.drawable.manhquynh2, R.raw.khinguoiminhyeukhoc));
//        list.add(new Song(9, "Thật bất ngờ", "Trúc Nhân", R.drawable.trucnhan, R.raw.thatbatngo));
//        list.add(new Song(10, "Tình yêu màu nắng", "Trúc Nhân", R.drawable.trucnhan2, R.raw.tinhyeumaunang));
//        list.add(new Song(11, "Sao cha không", "Phan Mạnh Quỳnh", R.drawable.manhquynh3, R.raw.saochakhong));
//        list.add(new Song(12, "Có không giữ mất đừng tìm", "Trúc Nhân", R.drawable.trucnhan1, R.raw.cokhonggiumatdungtim));

        listViewPlaylist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Song song = list.get(i);
                Intent intent = new Intent(getContext(), MusicActivity.class);
                 intent.putExtra("song_id", song.getId());
                 getActivity().startActivity(intent);
            }
        });

//        MusicAdapter adapter = new MusicAdapter(list,getContext());
//        listViewPlaylist.setAdapter(adapter);
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

    @Override
    public void onResume() {
        readData();
        super.onResume();
    }

    private void readData(){
//        final String[] nameSinger = new String[1];
//        final String[] idSinger = new String[1];
//        db.collection("singer")
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            ArrayList<Song> list = new ArrayList<>();
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                Map<String, Object> map = document.getData();
//                                nameSinger[0] = map.get("name").toString();
//                                idSinger[0] = map.get("id_singer").toString();
//                            }
//                        }
//                        else {
//                            Log.w(">>>>>>>>>>>TAG", "Error getting documents.", task.getException());
//                        }
//                    }
//                });
    //fill data to fragment
    db.collection("song")
            .get()
            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        ArrayList<Song> list = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Map<String, Object> map = document.getData();
                            String title = map.get("name").toString();
//                            String id_singer = map.get("id_singer").toString();
//              if(idSinger == id_singer){
//
//              }
//                            String singer = map.get("singer").toString();
//                            String image = map.get("image").toString();
                            String resource = map.get("audio").toString();

                            Song course = new Song(-1, title, resource);
                            course.setTitle(document.getId());
                            list.add(course);
                            MusicAdapter musicAdapter= new MusicAdapter(list, getContext());
                            listViewPlaylist.setAdapter(musicAdapter);
                        }
                        }
                        else {
                        Log.w(">>>>>>>>>>>TAG", "Error getting documents.", task.getException());
                    }
                }
            });
}
}

