package com.workshops.onlinemusicplayer.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.workshops.onlinemusicplayer.R;
import com.workshops.onlinemusicplayer.adapter.PlayListPopularAdapter;
import com.workshops.onlinemusicplayer.adapter.PlayListSingerAdapter;
import com.workshops.onlinemusicplayer.model.PlayListPopular;
import com.workshops.onlinemusicplayer.model.PlayListSinger;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExploreFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExploreFragment extends Fragment {

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

    private RecyclerView recyclerViewSinger;
    private RecyclerView recyclerViewPopular;
    private PlayListSingerAdapter adapterSinger;
    private PlayListPopularAdapter adapterPopular;
    private LinearLayoutManager layoutManagerSinger;
    private LinearLayoutManager layoutManagerPopular;
    ArrayList<PlayListSinger> listSinger = new ArrayList<>();
    ArrayList<PlayListPopular> listPopular = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_explore, container, false);
        recyclerViewSinger = view.findViewById(R.id.playListSinger);
        recyclerViewPopular = view.findViewById(R.id.playListPopular);

        getListSinger();
        getListPopular();

        // singer
        layoutManagerSinger = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL, false);
        adapterSinger = new PlayListSingerAdapter(listSinger,getActivity());
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
        listPopular.add(new PlayListPopular("Ðông Tây Nam Bắc","Ái Phương",R.drawable.ai_phuong_dongtaynambac));
        listPopular.add(new PlayListPopular("Gone (Da Da Da)","Imanbek, Jay",R.drawable.gone_dadada));
        listPopular.add(new PlayListPopular("Cô Ðơn Trên Sofa","Hồ Ngọc Hà",R.drawable.ho_ngoc_ha_codontrensofa));
        listPopular.add(new PlayListPopular("906090","Tóc Tiên",R.drawable.toc_tien_906090));
        listPopular.add(new PlayListPopular("Element","David Guetta",R.drawable.element));
        listPopular.add(new PlayListPopular("dongvui harmony","Ðen Vâu",R.drawable.dongvui_denvau));

    }

    private void getListSinger(){
        listSinger.add(new PlayListSinger("Sơn Tùng MTP", R.drawable.son_tung));
        listSinger.add(new PlayListSinger("Hương Tràm", R.drawable.huong_tram));
        listSinger.add(new PlayListSinger("Phan Mạnh Quỳnh", R.drawable.phan_manh_quynh));
        listSinger.add(new PlayListSinger("Tóc Tiên", R.drawable.toc_tien));
        listSinger.add(new PlayListSinger("JustaTee", R.drawable.justatee));
        listSinger.add(new PlayListSinger("BLACKPINK", R.drawable.blackpink));
        listSinger.add(new PlayListSinger("Sam Smith", R.drawable.sam_smith));
    }
}