package com.workshops.onlinemusicplayer.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.workshops.onlinemusicplayer.R;
import com.workshops.onlinemusicplayer.adapter.PlayListMusicAdapter;
import com.workshops.onlinemusicplayer.adapter.SongsAdapter;
import com.workshops.onlinemusicplayer.helper.MusicLibraryHelper;
import com.workshops.onlinemusicplayer.listener.CallBackDatabase;
import com.workshops.onlinemusicplayer.listener.MusicSelectListener;
import com.workshops.onlinemusicplayer.listener.PlayListListener;
import com.workshops.onlinemusicplayer.model.Singer;
import com.workshops.onlinemusicplayer.model.Music;
import com.workshops.onlinemusicplayer.view.LoginActivity;
import com.workshops.onlinemusicplayer.view.ResetPasswordActivity;

import java.util.ArrayList;
import java.util.List;

public class UserFragment extends Fragment implements PlayListListener {
    public static MusicSelectListener listener;
    private ImageView menu_btn;
    private FirebaseAuth mAuth;
    private FirebaseFirestore fStore;
    private GoogleSignInClient mGoogleSignInClient;
    private TextView etEmail, etName;
    private String userID;
    private AlertDialog.Builder reset_alert;
    private LayoutInflater inflater2;
    private static final String TAG = "Read data from firebase";
    private ArrayList<Music> list = new ArrayList<Music>();
    private PlayListMusicAdapter adapter;
    public static ArrayList<Singer> singers = new ArrayList<>();
    private final List<Music> unChangedList = new ArrayList<>();

    public UserFragment() {
    }

    public static UserFragment newInstance(MusicSelectListener selectListener) {
        UserFragment.listener = selectListener;
        return new UserFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);

        etEmail = view.findViewById(R.id.etEmail);
        etName = view.findViewById(R.id.etName);
        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userID = mAuth.getCurrentUser().getUid();
        reset_alert =  new AlertDialog.Builder(getContext());
        inflater2 = this.getLayoutInflater();

        DocumentReference documentReference = fStore.collection("users").document(userID);
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                etEmail.setText(documentSnapshot.getString("email"));
                etName.setText(documentSnapshot.getString("Name"));
            }
        });

        menu_btn = view.findViewById(R.id.menu_btn);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);

        menu_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Initializing the popup menu and giving the reference as current context
                PopupMenu popupMenu = new PopupMenu(getActivity(), menu_btn);

                // Inflating popup menu from popup_menu.xml file
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        int id = menuItem.getItemId();
                        switch (id) {
                            case R.id.reset_password:
                                getActivity().startActivity(new Intent(getActivity(), ResetPasswordActivity.class));
                                return true;
                            case R.id.log_out:
                                signOut(getActivity());
                                getActivity().startActivity(new Intent(getActivity(), LoginActivity.class));
                                getActivity().finish();
                                return true;
                            default:
                                return false;
                        }
                    }
                });
                // Showing the popup menu
                popupMenu.show();
            }
        });

        RecyclerView recyclerViewSong = view.findViewById(R.id.recycler_view_playlist_user);
        recyclerViewSong.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new PlayListMusicAdapter(listener, this, list);

        MusicLibraryHelper.fetchMusicLibrary(view.getContext(), new CallBackDatabase() {
            @Override
            public List<Music> onCallback(List<Music> result) {
                unChangedList.addAll(result);
                list.clear();
                list.addAll(unChangedList);
                recyclerViewSong.setAdapter(adapter);
                return result;
            }
        });

        return view;
    }

    public void signOut(Activity activity) {
        if (mAuth == null)
            mAuth = FirebaseAuth.getInstance();

        //Firebase SignOut
        mAuth.signOut();

        //Google SignOut
        mGoogleSignInClient.signOut().addOnCompleteListener(getActivity(),
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });

        //Facebook SignOut
        LoginManager.getInstance().logOut();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void option(Context context, Music music) {

    }
}