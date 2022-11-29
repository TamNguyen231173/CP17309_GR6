package com.workshops.onlinemusicplayer.fragment.homeTabLayout;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.workshops.onlinemusicplayer.model.PlayListPopular;

import java.util.ArrayList;

public class PodcastsFragment extends Fragment {

    public static final String TITLE = "title";

    public PodcastsFragment() {
        // Required empty public constructor
    }
    private RecyclerView recyclerViewPopular;
    private PlayListPopularAdapter adapterPopular;
    private LinearLayoutManager layoutManagerPopular;
    ArrayList<PlayListPopular> listPopular = new ArrayList<>();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String TAG = "Read data from firebase";
    int i;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_podcasts, container, false);
        recyclerViewPopular = view.findViewById(R.id.playListPopular);


        getListPopular();

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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}