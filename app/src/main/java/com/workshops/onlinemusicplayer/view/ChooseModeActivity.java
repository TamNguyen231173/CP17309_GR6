package com.workshops.onlinemusicplayer.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.Button;

import com.workshops.onlinemusicplayer.R;

public class ChooseModeActivity extends AppCompatActivity {
    Button btn_continue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_mode);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getRealSize(size);

        btn_continue = findViewById(R.id.btn_continue_choose_mode);
        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ChooseModeActivity.this, LoginRegisterActivity.class));
                finish();
            }
        });
    }

}