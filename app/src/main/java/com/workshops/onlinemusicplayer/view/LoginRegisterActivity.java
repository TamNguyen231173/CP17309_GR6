package com.workshops.onlinemusicplayer.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.workshops.onlinemusicplayer.R;

public class LoginRegisterActivity extends AppCompatActivity {
    Button btn_register;
    TextView btn_sign_in;
    ImageView image_login_register, image_login_register2, image_login_register3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_regsiter);
        hideSystemBars();

        btn_register = findViewById(R.id.btn_navigate_register);
        btn_sign_in = findViewById(R.id.btn_navigate_sign_in);
        image_login_register = findViewById(R.id.image_login_register);
        image_login_register2 = findViewById(R.id.image_login_register2);
        image_login_register3 = findViewById(R.id.image_login_register3);

        Animation slide_in_right = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_in_right);
        Animation slide_in_left = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_in_left_and_fade);

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        image_login_register.startAnimation(slide_in_left);
                        image_login_register2.startAnimation(slide_in_right);
                        image_login_register3.startAnimation(slide_in_right);
                    }
                }, 200);


        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginRegisterActivity.this, RegisterActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        btn_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginRegisterActivity.this, LoginActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
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
        Intent i = new Intent(LoginRegisterActivity.this, ChooseModeActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}