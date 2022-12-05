package com.workshops.onlinemusicplayer.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SearchView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.workshops.onlinemusicplayer.R;
import com.workshops.onlinemusicplayer.adapter.AlbumAdapter;
import com.workshops.onlinemusicplayer.adapter.MusicAdapter;
import com.workshops.onlinemusicplayer.adapter.SongsAdapter;
import com.workshops.onlinemusicplayer.helper.MusicLibraryHelper;
import com.workshops.onlinemusicplayer.listener.CallBackDatabase;
import com.workshops.onlinemusicplayer.listener.MusicSelectListener;
import com.workshops.onlinemusicplayer.listener.PlayListListener;
import com.workshops.onlinemusicplayer.model.Albums;
import com.workshops.onlinemusicplayer.model.RecyclerViewInterface;
import com.workshops.onlinemusicplayer.model.Singer;
import com.workshops.onlinemusicplayer.model.Music;
import com.workshops.onlinemusicplayer.view.PlayListAlbumActivity;

import java.util.ArrayList;
import java.util.List;

public class LikeFragment extends Fragment implements RecyclerViewInterface, SearchView.OnQueryTextListener, PlayListListener {
    private static final String TAG = "Read data from firebase";
    public static MusicSelectListener listener;
    private final List<Music> musicList = new ArrayList<>();
    private List<Music> unChangedList = new ArrayList<>();
    private ArrayList<Music> list = new ArrayList<Music>();
    private ListView listViewPlaylist;
    private MusicAdapter musicAdapter;
    private AlbumAdapter albumAdapter;
    private SongsAdapter songAdapter;
    private int i;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static ArrayList<Singer> singers = new ArrayList<>();
    private ArrayList<Albums> albums = new ArrayList<>();
    private RecyclerView recyclerViewAlbum;
    private LinearLayoutManager layoutManagerAlbum;

    public LikeFragment() {
    }

    public static LikeFragment newInstance(MusicSelectListener selectListener) {
        LikeFragment.listener = selectListener;
        return new LikeFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_like, container, false);
        recyclerViewAlbum = view.findViewById(R.id.list_view_albums);

        RecyclerView recyclerViewSong = view.findViewById(R.id.listViewPlaylist);
        recyclerViewSong.setLayoutManager(new LinearLayoutManager(getActivity()));
        songAdapter = new SongsAdapter(listener, this, musicList);

        MusicLibraryHelper.fetchMusicLibrary(view.getContext(), new CallBackDatabase() {
            @Override
            public List<Music> onCallback(List<Music> result) {
                unChangedList.addAll(result);
                musicList.clear();
                musicList.addAll(unChangedList);
                recyclerViewSong.setAdapter(songAdapter);
                return result;
            }
        });

        // albums
        layoutManagerAlbum = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL, false);
        albumAdapter = new AlbumAdapter(albums,getActivity(),this);
        recyclerViewAlbum.setLayoutManager(layoutManagerAlbum);
        recyclerViewAlbum.setAdapter(albumAdapter);

        return view;
    }

    private void readDataAlbums() {
        db.collection("category")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String id = (String) document.getData().get("id");
                                String name = (String) document.getData().get("name");
                                String image = (String) document.getData().get("image");
                                albums.add(new Albums(id, name, image));
                            }
                            albumAdapter = new AlbumAdapter(albums, getContext(),LikeFragment.this);
                            recyclerViewAlbum.setAdapter(albumAdapter);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
    @Override
    public void onResume() {
        super.onResume();
        readDataAlbums();
    }

    @Override
    public void onClickItem(int position) {
        Intent intent = new Intent(getActivity(), PlayListAlbumActivity.class);

        intent.putExtra("id",albums.get(position).getId());
        intent.putExtra("name",albums.get(position).getName());
        intent.putExtra("image",albums.get(position).getImage());

        startActivity(intent);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }

    @Override
    public void option(Context context, Music music) {

    }
}