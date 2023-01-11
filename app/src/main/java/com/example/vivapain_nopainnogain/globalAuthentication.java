package com.example.vivapain_nopainnogain;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class globalAuthentication extends AppCompatActivity {

    Button traineeBtn, gymOwnerBtn;
    TextView continueWithoutSignup;
    boolean WifiConnected = false;
    boolean MobileDataConnected = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_global_authentication);

        Objects.requireNonNull(getSupportActionBar()).hide();

        traineeBtn = findViewById(R.id.login);
        gymOwnerBtn = findViewById(R.id.signup);

        initTraineeBtn();
        initGymOwnerBtn();

        SharedPreferences settingsPrefs = getSharedPreferences("switch_prefs", Context.MODE_PRIVATE);
        boolean isChecked = settingsPrefs.getBoolean("switch_status", true);
        if (isChecked) {
            initCheckWifi();
        }

        continueWithoutSignup = findViewById(R.id.continueWithoutSignup);
        init_continueWithoutSignup();
    }

    private void initGymOwnerBtn() {
        gymOwnerBtn.setOnClickListener(v -> {
            Intent intent = new Intent(globalAuthentication.this, GymOwnerAuthentication.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        });
    }

    private void initTraineeBtn() {
        traineeBtn.setOnClickListener(v -> {
            Intent intent = new Intent(globalAuthentication.this, userAuthentication.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        });
    }

    private void init_continueWithoutSignup() {
        continueWithoutSignup.setOnClickListener(v -> {
            Intent intent = new Intent(globalAuthentication.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        });
    }

    private void initCheckWifi() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            WifiConnected = networkInfo.getType() == ConnectivityManager.TYPE_WIFI;
            MobileDataConnected = networkInfo.getType() == ConnectivityManager.TYPE_MOBILE;
            if (!WifiConnected && MobileDataConnected) {
                Toast.makeText(globalAuthentication.this, "You are using mobile data connection", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(globalAuthentication.this, "NO CONNECTION!", Toast.LENGTH_SHORT).show();
            noConnectionDialog();
        }
    }

    private void noConnectionDialog() {
        new AlertDialog.Builder(this)
                .setTitle("ALERT!")
                .setMessage("NO CONNECTION!")
                .setPositiveButton("Dismiss", (dialog, which) -> dialog.dismiss())
                .create().show();
    }

}