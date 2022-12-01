package com.workshops.onlinemusicplayer.fragment.homeTabLayout;

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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.workshops.onlinemusicplayer.R;
import com.workshops.onlinemusicplayer.adapter.AlbumAdapter2;
import com.workshops.onlinemusicplayer.adapter.MusicAdapter;
import com.workshops.onlinemusicplayer.fragment.HomeFragment;
import com.workshops.onlinemusicplayer.listener.MusicSelectListener;
import com.workshops.onlinemusicplayer.model.Albums;
import com.workshops.onlinemusicplayer.model.Music;
import com.workshops.onlinemusicplayer.model.RecyclerViewInterface;
import com.workshops.onlinemusicplayer.model.Singer;
import com.workshops.onlinemusicplayer.view.PlayListAlbumActivity;

import java.util.ArrayList;

public class PodcastsFragment extends Fragment implements RecyclerViewInterface {

    private static final String TAG = "Read data from firebase";
    ArrayList<Music> list = new ArrayList<Music>();
    ListView listViewPlaylist, listViewAlBums;
    MusicAdapter adapter;
    AlbumAdapter2 adapter1;
    int i;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public static ArrayList<Singer> singers = new ArrayList<>();
    ArrayList<Albums> albums = new ArrayList<>();
    private RecyclerView recyclerViewAlbum;
    private LinearLayoutManager layoutManagerAlbum;

    public PodcastsFragment() {
    }

    public static PodcastsFragment newInstance(MusicSelectListener selectListener) {
        HomeFragment.listener = selectListener;
        return new PodcastsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_podcasts, container, false);
        recyclerViewAlbum = view.findViewById(R.id.playListPopular);

        // albums
        layoutManagerAlbum = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL, false);
        adapter1 = new AlbumAdapter2(albums,getActivity(),this);
        recyclerViewAlbum.setLayoutManager(layoutManagerAlbum);
        recyclerViewAlbum.setAdapter(adapter1);

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
                            adapter1 = new AlbumAdapter2(albums, getContext(), PodcastsFragment.this);
                            recyclerViewAlbum.setAdapter(adapter1);
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

        //Toast.makeText(getActivity(), "Title"+ds.get(position).getName(), Toast.LENGTH_SHORT).show();
    }
}