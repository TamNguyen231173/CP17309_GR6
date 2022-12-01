package com.workshops.onlinemusicplayer.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

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
import com.workshops.onlinemusicplayer.adapter.PlayListSingerAdapter;
import com.workshops.onlinemusicplayer.adapter.TrendAdapter;
import com.workshops.onlinemusicplayer.listener.MusicSelectListener;
import com.workshops.onlinemusicplayer.model.Albums;
import com.workshops.onlinemusicplayer.model.RecyclerViewInterface;
import com.workshops.onlinemusicplayer.model.Singer;
import com.workshops.onlinemusicplayer.model.Music;
import com.workshops.onlinemusicplayer.view.MusicActivity;
import com.workshops.onlinemusicplayer.view.PlayListAlbumActivity;

import java.util.ArrayList;

public class LikeFragment extends Fragment implements RecyclerViewInterface {
    private static final String TAG = "Read data from firebase";
    ArrayList<Music> list = new ArrayList<Music>();
    ListView listViewPlaylist;
    MusicAdapter adapter;
    AlbumAdapter adapter1;
    int i;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public static ArrayList<Singer> singers = new ArrayList<>();
    ArrayList<Albums> albums = new ArrayList<>();
    private RecyclerView recyclerViewAlbum;
    private LinearLayoutManager layoutManagerAlbum;

    public LikeFragment() {
    }

    public static LikeFragment newInstance(MusicSelectListener selectListener) {
        SongsFragment.listener = selectListener;
        return new LikeFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_like, container, false);
        recyclerViewAlbum = view.findViewById(R.id.list_view_albums);
        getDataPlaylist();
//        listViewAlBums = view.findViewById(R.id.list_view_albums);
        listViewPlaylist = view.findViewById(R.id.listViewPlaylist);

        // albums
        layoutManagerAlbum = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL, false);
        adapter1 = new AlbumAdapter(albums,getActivity(),this);
        recyclerViewAlbum.setLayoutManager(layoutManagerAlbum);
        recyclerViewAlbum.setAdapter(adapter1);

        listViewPlaylist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Music song = list.get(i);
                Intent intent = new Intent(getContext(), MusicActivity.class);
                intent.putExtra("song_id", song.getId());
                getActivity().startActivity(intent);
            }
        });

        return view;
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
                                boolean flag = (boolean) document.getData().get("flag");
                                list.add(new Music(i, title, singer, image, flag));
                            }
                            adapter = new MusicAdapter(list, getContext());
                            listViewPlaylist.setAdapter(adapter);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

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
                            adapter1 = new AlbumAdapter(albums, getContext(),LikeFragment.this);
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
        readData();
        readDataAlbums();
    }


    private void getDataPlaylist() {
        db.collection("singer")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {
                                singers.add(new Singer(document.get("name").toString(),document.getId()));
                            }
                            adapter = new MusicAdapter(list, getContext());
                            listViewPlaylist.setAdapter(adapter);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("<<singer>>", "Lấy dữ liệu không thành công" );
                    }
                });
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