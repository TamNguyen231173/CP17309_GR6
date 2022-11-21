package com.workshops.onlinemusicplayer.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.workshops.onlinemusicplayer.R;

public class ResetPasswordActivity extends AppCompatActivity {
    EditText UserPassword,ConfirmPassword;
    Button ResetPassword;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        UserPassword = findViewById(R.id.edt_new_UserPassword);
        ConfirmPassword = findViewById(R.id.edt_new_ConfirmPassword);
        ResetPassword = findViewById(R.id.btn_ResetPassword);
        user = FirebaseAuth.getInstance().getCurrentUser();

        ResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UserPassword.getText().toString().isEmpty()) {
                    UserPassword.setError("Required Field");
                    return;
                }
                if (ConfirmPassword.getText().toString().isEmpty()) {
                    ConfirmPassword.setError("Required Field");
                    return;
                }
                if (!UserPassword.getText().toString().equals(ConfirmPassword.getText().toString())) {
                    ConfirmPassword.setError("Password Do not Match");
                    return;
                }

                user.updatePassword(UserPassword.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(ResetPasswordActivity.this, "Password updated", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ResetPasswordActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}