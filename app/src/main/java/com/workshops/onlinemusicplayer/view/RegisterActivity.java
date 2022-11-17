package com.workshops.onlinemusicplayer.view;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.workshops.onlinemusicplayer.R;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    EditText edtName, edtEmail, edtPassword;
    Button btn_register;
    TextView btn_login;

    FirebaseAuth mAuth;
    FirebaseFirestore fStore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        hideSystemBars();

        edtName = findViewById(R.id.edt_register_name);
        edtEmail = findViewById(R.id.edt_register_email);
        edtPassword = findViewById(R.id.edt_register_password);
        btn_register = findViewById(R.id.btn_register);
        btn_login = findViewById(R.id.btn_move_login);

        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        btn_register.setOnClickListener(view -> {
            createUser();
        });

        btn_login.setOnClickListener(view -> {
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        });
    }

    public void Back(View view){
        Intent i = new Intent(RegisterActivity.this, LoginRegisterActivity.class);
        startActivity(i);
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private void createUser() {
        String name = edtName.getText().toString();
        String email = edtEmail.getText().toString();
        String password = edtPassword.getText().toString();

        //Validate if edit empty
        if(TextUtils.isEmpty(name)) {
            edtName.setError("Name cannot be empty");
        } else if(TextUtils.isEmpty(email)) {
            edtEmail.setError("Email cannot be empty");
        } else if(TextUtils.isEmpty(password)) {
            edtPassword.setError("Password cannot be empty");
        } else {
            //Create user
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(RegisterActivity.this, "User register successfully", Toast.LENGTH_SHORT).show();
                        userID = mAuth.getCurrentUser().getUid();
                        DocumentReference documentReference = fStore.collection("users").document(userID);
                        HashMap<String, Object> user = new HashMap<>();
                        user.put("name", name);
                        user.put("email", email);
                        documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Log.d(TAG, "onSuccess: user Profile is created for" + userID);
                            }
                        });
                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                        finish();
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    } else {
                        Toast.makeText(RegisterActivity.this, "Registration Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void hideSystemBars() {
        WindowInsetsControllerCompat windowInsetsController =
                ViewCompat.getWindowInsetsController(getWindow().getDecorView());
        if (windowInsetsController == null) {
            return;
        }
        // Configure the behavior of the hidden system bars
        windowInsetsController.setSystemBarsBehavior(
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        );
        // Hide both the status bar and the navigation bar
        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars());
    }
}
