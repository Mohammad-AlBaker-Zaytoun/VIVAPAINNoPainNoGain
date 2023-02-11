package com.example.vivapain_nopainnogain;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class Contact extends AppCompatActivity {

    Button email, whatsapp;
    Button backBtn;
    ImageButton homeRedirect, mapRedirect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        Objects.requireNonNull(getSupportActionBar()).hide();
        homeRedirect = findViewById(R.id.Home);
        mapRedirect = findViewById(R.id.Map);
        email = findViewById(R.id.EmailUs);
        whatsapp = findViewById(R.id.Whatsapp);
        backBtn = findViewById(R.id.BackBtn);
        initBackBtn();
        initEmail();
        initWhatsapp();
        initHomeRedirect();
        initMapRedirect();
    }

    private void initBackBtn() {
        backBtn.setOnClickListener(v -> {
            Intent intent = new Intent(Contact.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
    }

    private void initWhatsapp() {
        whatsapp.setOnClickListener(v -> sendWhats());
    }

    private void sendWhats() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        String phoneNO = "+96171480594";
        String whatsappMessage = "Hello, I recently used your application VIVA PAIN. May I ask a question?";
        intent.setData(Uri.parse("http://api.whatsapp.com/send?phone=" + phoneNO + "&text=" + whatsappMessage));
        startActivity(intent);
    }

    private void initEmail() {
        email.setOnClickListener(v -> sendEmail());
    }

    @SuppressLint("IntentReset")
    private void sendEmail() {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"vivapain033@gmail.com"});
        try {
            startActivity(Intent.createChooser(emailIntent, "Choose email client"));
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void initHomeRedirect() {
        homeRedirect.setOnClickListener(v -> {
            Intent intent = new Intent(Contact.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
    }

    private void initMapRedirect() {
        mapRedirect.setOnClickListener(v -> {
            Intent intent = new Intent(Contact.this, Gym_Near_By.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
    }

}