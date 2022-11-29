package com.workshops.onlinemusicplayer.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.workshops.onlinemusicplayer.R;
import com.workshops.onlinemusicplayer.adapter.MusicAdapter;
import com.workshops.onlinemusicplayer.adapter.NewsAdapter;
import com.workshops.onlinemusicplayer.adapter.SongsAdapter;
import com.workshops.onlinemusicplayer.adapter.TrendAdapter;
import com.workshops.onlinemusicplayer.helper.ListHelper;
import com.workshops.onlinemusicplayer.helper.MusicLibraryHelper;
import com.workshops.onlinemusicplayer.listener.CallBackDatabase;
import com.workshops.onlinemusicplayer.listener.MusicSelectListener;
import com.workshops.onlinemusicplayer.listener.PlayListListener;
import com.workshops.onlinemusicplayer.model.MainViewModel;
import com.workshops.onlinemusicplayer.model.MainViewModelFactory;
import com.workshops.onlinemusicplayer.model.Music;
import com.workshops.onlinemusicplayer.model.Singer;
import com.workshops.onlinemusicplayer.view.MusicActivity;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements SearchView.OnQueryTextListener, PlayListListener {

    public static MusicSelectListener listener;
    private final List<Music> musicList = new ArrayList<>();
    private MainViewModel viewModel;
    private SongsAdapter songAdapter;
    private List<Music> unChangedList = new ArrayList<>();

    private MaterialToolbar toolbar;
    private SearchView searchView;

    ArrayList<Music> list = new ArrayList<Music>();
    ListView listViewPlaylist;
    MusicAdapter adapter;
    NewsAdapter newsAdapter;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public static ArrayList<Singer> singers = new ArrayList<>();

    public HomeFragment() {
    }

    public static HomeFragment newInstance(MusicSelectListener selectListener) {
        HomeFragment.listener = selectListener;
        return new HomeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity(),
                new MainViewModelFactory(requireActivity())).get(MainViewModel.class);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        RecyclerView recyclerViewSong = view.findViewById(R.id.songs_layout);
        recyclerViewSong.setLayoutManager(new LinearLayoutManager(getActivity()));
        songAdapter = new SongsAdapter(listener, this, musicList);

        MusicLibraryHelper.fetchMusicLibrary(view.getContext(), new CallBackDatabase() {
            @Override
            public List<Music> onCallback(List<Music> result) {
                for (Music music : result) {
                    unChangedList.add(music);
                }
                musicList.clear();
                musicList.addAll(unChangedList);
                recyclerViewSong.setAdapter(songAdapter);
                return result;
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

    private void setUpSearchView() {
        searchView.setOnQueryTextListener(this);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        updateAdapter(ListHelper.searchMusicByName(unChangedList, query.toLowerCase()));
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        updateAdapter(ListHelper.searchMusicByName(unChangedList, newText.toLowerCase()));
        return true;
    }

    private void updateAdapter(List<Music> list) {
        musicList.clear();
        musicList.addAll(list);
        songAdapter.notifyDataSetChanged();
    }

    @Override
    public void option(Context context, Music music) {
    }

//   private void getDataPlaylist() {
//        db.collection("singer")
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for (DocumentSnapshot document : task.getResult()) {
//                                singers.add(new Singer(document.get("name").toString(),document.getId()));
//                            }
//                            adapter = new MusicAdapter(list, getContext());
//                            listViewPlaylist.setAdapter(adapter);
//                        }
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.d("<<singer>>", "Lấy dữ liệu không thành công" );
//                    }
//                });
//   }

}



