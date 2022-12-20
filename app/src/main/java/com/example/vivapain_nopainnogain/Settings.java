package com.example.vivapain_nopainnogain;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class Settings extends AppCompatActivity {

    ImageButton homeRedirect, mapRedirect;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    Switch switch1;
    private static final String MY_PREFS = "switch_prefs";
    private static final String SWITCH_STATUS = "switch_status";
    SharedPreferences myPreferences;
    SharedPreferences.Editor myEditor;
    boolean switch_state;
    Button backBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Objects.requireNonNull(getSupportActionBar()).hide();
        homeRedirect = findViewById(R.id.Home);
        mapRedirect = findViewById(R.id.Map);
        switch1 = findViewById(R.id.switch1);
        backBtn = findViewById(R.id.BackBtn);
        initBackBtn();
        myPreferences = getSharedPreferences(MY_PREFS, Context.MODE_PRIVATE);
        myEditor = myPreferences.edit();
        switch_state = myPreferences.getBoolean(SWITCH_STATUS, true);
        switch1.setChecked(switch_state);
        initSwitch1();
        initHomeRedirect();
        initMapRedirect();
    }

    private void initBackBtn() {
        backBtn.setOnClickListener(v -> {
            Intent intent = new Intent(Settings.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
    }

    private void initSwitch1() {
        switch1.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (switch1.isChecked()) {
                myEditor.putBoolean(SWITCH_STATUS, true);
                myEditor.commit();
                switch1.setChecked(myPreferences.getBoolean(SWITCH_STATUS, true));
                Toast.makeText(Settings.this, "You will be alerted if you're not on wifi", Toast.LENGTH_SHORT).show();
            } else {
                myEditor.putBoolean(SWITCH_STATUS, false);
                myEditor.commit();
                switch1.setChecked(myPreferences.getBoolean("switch_status", false));
                Toast.makeText(Settings.this, "You won't be alerted if you're not on wifi", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initMapRedirect() {
        mapRedirect.setOnClickListener(v -> {
            Intent intent = new Intent(Settings.this, Gym_Near_By.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
    }

    private void initHomeRedirect() {
        homeRedirect.setOnClickListener(v -> {
            Intent intent = new Intent(Settings.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
    }
}