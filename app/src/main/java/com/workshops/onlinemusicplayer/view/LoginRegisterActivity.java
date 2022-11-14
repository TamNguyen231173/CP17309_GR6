package com.workshops.onlinemusicplayer.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.workshops.onlinemusicplayer.R;

public class LoginRegisterActivity extends AppCompatActivity {
    Button btn_register;
    TextView btn_sign_in;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_regsiter);
        hideSystemBars();

        btn_register = findViewById(R.id.btn_navigate_register);
        btn_sign_in = findViewById(R.id.btn_navigate_sign_in);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginRegisterActivity.this, RegisterActivity.class));
            }
        });

        btn_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginRegisterActivity.this, LoginActivity.class));
            }
        });
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

    public void NextBack(View view){
        Intent i = new Intent(LoginRegisterActivity.this, GetStartedActivity.class);
        startActivity(i);
    }
}