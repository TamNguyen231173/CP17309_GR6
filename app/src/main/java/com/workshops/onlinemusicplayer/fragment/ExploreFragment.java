package com.workshops.onlinemusicplayer.fragment;

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
import com.workshops.onlinemusicplayer.adapter.PlayListPopularAdapter;
import com.workshops.onlinemusicplayer.adapter.PlayListSingerAdapter;
import com.workshops.onlinemusicplayer.listener.MusicSelectListener;
import com.workshops.onlinemusicplayer.model.PlayListPopular;
import com.workshops.onlinemusicplayer.model.PlayListSinger;
import com.workshops.onlinemusicplayer.model.RecyclerViewInterface;
import com.workshops.onlinemusicplayer.view.PlayListSingerActivity;

import java.util.ArrayList;

public class ExploreFragment extends Fragment implements RecyclerViewInterface {
    int i;

    private static final String TAG = "Read data from firebase";
    private RecyclerView recyclerViewSinger;
    private RecyclerView recyclerViewPopular;
    private PlayListSingerAdapter adapterSinger;
    private PlayListPopularAdapter adapterPopular;
    private LinearLayoutManager layoutManagerSinger;
    private LinearLayoutManager layoutManagerPopular;
    ArrayList<PlayListPopular> listPopular = new ArrayList<>();
    ArrayList<PlayListSinger> ds = new ArrayList<>();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public ExploreFragment() {
    }

    public static ExploreFragment newInstance(MusicSelectListener selectListener) {
        HomeFragment.listener = selectListener;
        return new ExploreFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_explore, container, false);
        recyclerViewSinger = view.findViewById(R.id.playListSinger);
        recyclerViewPopular = view.findViewById(R.id.playListPopular);


        getListSinger();
        getListPopular();
//        readData();

        // singer
        layoutManagerSinger = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL, false);
        adapterSinger = new PlayListSingerAdapter(ds,getActivity(),this);
        recyclerViewSinger.setLayoutManager(layoutManagerSinger);
        recyclerViewSinger.setAdapter(adapterSinger);

        //popular
        adapterPopular = new PlayListPopularAdapter(listPopular,getActivity());
        layoutManagerPopular = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL, false);
        recyclerViewPopular.setLayoutManager(layoutManagerPopular);
        recyclerViewPopular.setAdapter(adapterPopular);

        return view;

    }


    private void getListPopular() {
        db.collection("popular")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                i++;
                                String name = (String) document.getData().get("name");
                                String image = (String) document.getData().get("image");
                                String singer = (String) document.getData().get("singer");
                                listPopular.add(new PlayListPopular(i, name, image, singer));
                            }
                            adapterPopular = new PlayListPopularAdapter(listPopular, getContext());
                            recyclerViewPopular.setAdapter(adapterPopular);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
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
                            adapterSinger = new PlayListSingerAdapter(ds, getContext(),ExploreFragment.this);
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

        //Toast.makeText(getActivity(), "Title"+ds.get(position).getName(), Toast.LENGTH_SHORT).show();
    }
}