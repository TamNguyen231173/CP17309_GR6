package com.workshops.onlinemusicplayer.fragment.homeTabLayout;

import android.annotation.SuppressLint;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.workshops.onlinemusicplayer.R;
import com.workshops.onlinemusicplayer.adapter.PlayListSingerAdapter2;
import com.workshops.onlinemusicplayer.fragment.HomeFragment;
import com.workshops.onlinemusicplayer.listener.MusicSelectListener;
import com.workshops.onlinemusicplayer.model.PlayListSinger;
import com.workshops.onlinemusicplayer.model.RecyclerViewInterface;
import com.workshops.onlinemusicplayer.view.PlayListSingerActivity;

import java.util.ArrayList;

public class ArtistsFragment extends Fragment implements RecyclerViewInterface {


    private static final String TAG = "Read data from firebase";
    private RecyclerView recyclerViewSinger;
    private PlayListSingerAdapter2 adapterSinger;
    private LinearLayoutManager layoutManagerSinger;
    ArrayList<PlayListSinger> ds = new ArrayList<>();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public ArtistsFragment() {
    }

    public static ArtistsFragment newInstance(MusicSelectListener selectListener) {
        HomeFragment.listener = selectListener;
        return new ArtistsFragment();
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_artists, container, false);
        recyclerViewSinger = view.findViewById(R.id.playListSinger);


        getListSinger();
//        readData();

        // singer
        layoutManagerSinger = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL, false);
        adapterSinger = new PlayListSingerAdapter2(ds,getActivity(),this);
        recyclerViewSinger.setLayoutManager(layoutManagerSinger);
        recyclerViewSinger.setAdapter(adapterSinger);

        return view;

    }

    private void getListSinger() {
        db.collection("singer")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String id = (String) document.getData().get("id");
                                String name = (String) document.getData().get("name");
                                String image = (String) document.getData().get("image");
                                ds.add(new PlayListSinger(id, name, image));
                            }
                            adapterSinger = new PlayListSingerAdapter2(ds, getContext(),ArtistsFragment.this);
                            recyclerViewSinger.setAdapter(adapterSinger);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

    }


    @Override
    public void onClickItem(int position) {
        Intent intent = new Intent(getActivity(), PlayListSingerActivity.class);

        intent.putExtra("id",ds.get(position).getId());
        intent.putExtra("name",ds.get(position).getName());
        intent.putExtra("image",ds.get(position).getImage());

        startActivity(intent);
    }
}