package com.example.vivapain_nopainnogain;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Dinner extends AppCompatActivity {
    Button backBtn, nextBtn;

    ImageButton exitBtn;

    TextView DinnerNumberTxt, stepsTxt, caloriesTxt;

    private RetrofitInterface retrofitInterface;

    int DinnerNum;

    boolean WifiConnected = false;
    boolean MobileDataConnected = false;

    SharedPreferences DinnerPreferences;
    SharedPreferences.Editor myEditor;
    private static final String USER_PREFS = "DinnerNum";

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dinner);
        Objects.requireNonNull(getSupportActionBar()).hide();

        String baseURL = "http://10.0.2.2:3000";
        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseURL).addConverterFactory(GsonConverterFactory.create()).build();
        retrofitInterface = retrofit.create(RetrofitInterface.class);

        SharedPreferences settingsPrefs = getSharedPreferences("switch_prefs", Context.MODE_PRIVATE);
        boolean isChecked = settingsPrefs.getBoolean("switch_status", true);
        if (isChecked) {
            initCheckWifi();
        }

        exitBtn = findViewById(R.id.ExitIBtn);
        initExitBtn();


        backBtn = findViewById(R.id.BackBtn);
        initBackBtn();

        nextBtn = findViewById(R.id.nextBtn);
        initNextBtn();

        DinnerPreferences = getSharedPreferences(USER_PREFS, Context.MODE_PRIVATE);
        DinnerNum = DinnerPreferences.getInt("DinnerNum", 0);

        DinnerNumberTxt = findViewById(R.id.bNumTxt);
        DinnerNumberTxt.setText(Integer.toString(DinnerNum));

        stepsTxt = findViewById(R.id.stepsTxt1);
        caloriesTxt = findViewById(R.id.caloriesTxt1);

        initData(DinnerNum);
    }

    private void initExitBtn() {
        exitBtn.setOnClickListener(v -> {
            Intent intent = new Intent(Dinner.this, Food_Diet.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
    }

    private void initNextBtn() {
        nextBtn.setOnClickListener(v -> {
            DinnerNum++;
            if (DinnerNum > 31) {
                DinnerNum = 0;
            }
            DinnerPreferences = getSharedPreferences(USER_PREFS, Context.MODE_PRIVATE);
            myEditor = DinnerPreferences.edit();
            myEditor.putInt("DinnerNum", DinnerNum);
            myEditor.apply();

            Intent intent = new Intent(Dinner.this, Dinner.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
    }

    private void initCheckWifi() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            WifiConnected = networkInfo.getType() == ConnectivityManager.TYPE_WIFI;
            MobileDataConnected = networkInfo.getType() == ConnectivityManager.TYPE_MOBILE;
            if (!WifiConnected && MobileDataConnected) {
                Toast.makeText(Dinner.this, "You are using mobile data connection", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(Dinner.this, "NO CONNECTION!", Toast.LENGTH_SHORT).show();
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

    private void initData(int BNum) {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("num", BNum);
        Call<dinnerResult> call = retrofitInterface.executeDinner(map);
        call.enqueue(new Callback<dinnerResult>() {
            @Override
            public void onResponse(@NonNull Call<dinnerResult> call, @NonNull Response<dinnerResult> response) {
                if (response.code() == 200) {
                    dinnerResult result = response.body();
                    assert result != null;
                    String steps = result.getSteps();
                    String calories = result.getCalories();

                    stepsTxt.setText(steps);
                    caloriesTxt.setText(calories);

                } else if (response.code() == 400) {
                    Toast.makeText(Dinner.this, "server error", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(@NonNull Call<dinnerResult> call, @NonNull Throwable t) {
                Toast.makeText(Dinner.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void initBackBtn() {
        backBtn.setOnClickListener(v -> {
            DinnerNum--;
            if (DinnerNum < 0) {
                DinnerNum = 31;
            }
            DinnerPreferences = getSharedPreferences(USER_PREFS, Context.MODE_PRIVATE);
            myEditor = DinnerPreferences.edit();
            myEditor.putInt("DinnerNum", DinnerNum);
            myEditor.apply();

            Intent intent = new Intent(Dinner.this, Dinner.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
    }
}