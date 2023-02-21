package com.example.vivapain_nopainnogain;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class Food_Diet extends AppCompatActivity {
    Button checkBreakfast, checkSnacks3, checkLunch, checkDinner;
    ImageButton backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_diet);
        Objects.requireNonNull(getSupportActionBar()).hide();
        checkBreakfast = findViewById(R.id.CheckBreakFastBtn);
        checkDinner = findViewById(R.id.CheckDinnerBtn);
        checkLunch = findViewById(R.id.CheckLunchBtn);
        checkSnacks3 = findViewById(R.id.CheckSnacksBtn3);
        backBtn = findViewById(R.id.BackImageBtn);
        initCheckBreakfast();
        initCheckDinner();
        initCheckLunch();
        initCheckSnacks3();
        initBackBtn();
    }

    private void initBackBtn() {
        backBtn.setOnClickListener(v -> {
            Intent intent = new Intent(Food_Diet.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
    }

    private void initCheckSnacks3() {
        checkSnacks3.setOnClickListener(v -> {
            Intent intent = new Intent(Food_Diet.this, Snacks3.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
    }


    private void initCheckLunch() {
        checkLunch.setOnClickListener(v -> {
            Intent intent = new Intent(Food_Diet.this, Lunch.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });

    }

    private void initCheckDinner() {
        checkDinner.setOnClickListener(v -> {
            Intent intent = new Intent(Food_Diet.this, Dinner.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
    }

    private void initCheckBreakfast() {
        checkBreakfast.setOnClickListener(v -> {
            Intent intent = new Intent(Food_Diet.this, Breakfast.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
    }
}