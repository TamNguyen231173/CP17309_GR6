package com.workshops.onlinemusicplayer.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;
import com.workshops.onlinemusicplayer.R;
import com.workshops.onlinemusicplayer.adapter.MusicAdapter;
import com.workshops.onlinemusicplayer.adapter.TrendAdapter;
import com.workshops.onlinemusicplayer.model.Song;
import com.workshops.onlinemusicplayer.view.MusicActivity;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    private static final String TAG = "Read data from firebase";
    ArrayList<Song> list = new ArrayList<Song>();
    ListView listViewPlaylist;
    MusicAdapter adapter;
    int i;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        listViewPlaylist = view.findViewById(R.id.listViewPlaylist);

        listViewPlaylist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Song song = list.get(i);
                Intent intent = new Intent(getContext(), MusicActivity.class);
                intent.putExtra("song_id", song.getId());
                getActivity().startActivity(intent);
            }
        });

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

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        readData();
    }

    private void readData() {
        i = 0;
        db.collection("song")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                i++;
                                String title = (String) document.getData().get("name");
                                String singer = (String) document.getData().get("singer");
                                String image = (String) document.getData().get("image");

                                list.add(new Song(i, title, singer, image));
                            }
                            adapter = new MusicAdapter(list, getContext());
                            listViewPlaylist.setAdapter(adapter);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

    }
}

