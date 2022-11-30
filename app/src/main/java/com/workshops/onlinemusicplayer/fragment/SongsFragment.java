package com.workshops.onlinemusicplayer.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.workshops.onlinemusicplayer.listener.CallBackDatabase;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.workshops.onlinemusicplayer.R;
import com.workshops.onlinemusicplayer.adapter.SongsAdapter;
import com.workshops.onlinemusicplayer.helper.ListHelper;
import com.workshops.onlinemusicplayer.helper.MusicLibraryHelper;
import com.workshops.onlinemusicplayer.listener.MusicSelectListener;
import com.workshops.onlinemusicplayer.listener.PlayListListener;
import com.workshops.onlinemusicplayer.model.Music;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.List;

public class SongsFragment extends Fragment implements SearchView.OnQueryTextListener, PlayListListener {

    public static MusicSelectListener listener;
    private final List<Music> musicList = new ArrayList<>();
    private SongsAdapter songAdapter;
    private List<Music> unChangedList = new ArrayList<>();

    private MaterialToolbar toolbar;
    private SearchView searchView;

    public SongsFragment() {
    }

    public static SongsFragment newInstance(MusicSelectListener selectListener) {
        SongsFragment.listener = selectListener;
        return new SongsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_songs, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.songs_layout);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        songAdapter = new SongsAdapter(listener, this, musicList);

        MusicLibraryHelper.fetchMusicLibrary(view.getContext(), new CallBackDatabase() {
            @Override
            public List<Music> onCallback(List<Music> result) {
                for (Music music : result) {
                    unChangedList.add(music);
                }
                musicList.clear();
                musicList.addAll(unChangedList);
                recyclerView.setAdapter(songAdapter);
                return result;
            }
        });

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
}