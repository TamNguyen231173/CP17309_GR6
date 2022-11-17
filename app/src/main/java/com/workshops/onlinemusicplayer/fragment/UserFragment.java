package com.workshops.onlinemusicplayer.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.workshops.onlinemusicplayer.R;
import com.workshops.onlinemusicplayer.adapter.MusicAdapter;
import com.workshops.onlinemusicplayer.view.LoginActivity;

public class UserFragment extends Fragment {

    private ImageView menu_btn;
    FirebaseAuth mAuth;
    FirebaseFirestore fStore;
    private GoogleSignInClient mGoogleSignInClient;
    TextView etEmail, etName;
    String userID;
    MusicAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_user, container, false);
        etEmail = view.findViewById(R.id.etEmail);
        etName = view.findViewById(R.id.etName);
        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userID = mAuth.getCurrentUser().getUid();

        DocumentReference documentReference = fStore.collection("users").document(userID);
        documentReference.addSnapshotListener((Activity) getContext(), new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                etEmail.setText(value.getString("email"));
                etName.setText(value.getString("name"));
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
}