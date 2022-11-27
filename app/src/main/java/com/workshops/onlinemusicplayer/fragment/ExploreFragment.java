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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.workshops.onlinemusicplayer.R;
import com.workshops.onlinemusicplayer.adapter.PlayListMusicAdapter;
import com.workshops.onlinemusicplayer.adapter.PlayListPopularAdapter;
import com.workshops.onlinemusicplayer.adapter.PlayListSingerAdapter;
import com.workshops.onlinemusicplayer.model.PlayListPopular;
import com.workshops.onlinemusicplayer.model.PlayListSinger;
import com.workshops.onlinemusicplayer.model.RecyclerViewInterface;
import com.workshops.onlinemusicplayer.model.Singer;
import com.workshops.onlinemusicplayer.model.Song;
import com.workshops.onlinemusicplayer.view.MusicActivity;
import com.workshops.onlinemusicplayer.view.PlayListSingerActivity;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExploreFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExploreFragment extends Fragment implements RecyclerViewInterface {
    int i;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ExploreFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ExploreFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ExploreFragment newInstance(String param1, String param2) {
        ExploreFragment fragment = new ExploreFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


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