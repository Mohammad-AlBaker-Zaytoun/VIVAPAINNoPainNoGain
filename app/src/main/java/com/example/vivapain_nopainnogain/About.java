package com.example.vivapain_nopainnogain;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class About extends AppCompatActivity {
    ImageButton homeRedirect, mapRedirect;
    Button backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        Objects.requireNonNull(getSupportActionBar()).hide();
        homeRedirect = findViewById(R.id.Home);
        mapRedirect = findViewById(R.id.Map);
        backBtn = findViewById(R.id.BackBtn);
        initBackBtn();
        initHomeRedirect();
        initMapRedirect();
    }

    private void initBackBtn() {
        backBtn.setOnClickListener(v -> {
            Intent intent = new Intent(About.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
    }

    private void initMapRedirect() {
        mapRedirect.setOnClickListener(v -> {
            Intent intent = new Intent(About.this, Gym_Near_By.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
    }

    private void initHomeRedirect() {
        homeRedirect.setOnClickListener(v -> {
            Intent intent = new Intent(About.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
    }
}