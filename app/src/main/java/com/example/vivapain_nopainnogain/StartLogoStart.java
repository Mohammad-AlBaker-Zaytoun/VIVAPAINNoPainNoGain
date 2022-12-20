package com.example.vivapain_nopainnogain;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class StartLogoStart extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_logo_start);
        Objects.requireNonNull(getSupportActionBar()).hide();
        new Handler().postDelayed(() -> {
            startActivity(new Intent(StartLogoStart.this, MainActivity.class));
            finish();
        }, 2500);
    }
}