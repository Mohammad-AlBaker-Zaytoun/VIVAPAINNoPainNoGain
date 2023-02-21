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

public class Snacks3 extends AppCompatActivity {
    Button backBtn, nextBtn;

    ImageButton exitBtn;

    TextView SnackNumberTxt, stepsTxt, caloriesTxt;

    private RetrofitInterface retrofitInterface;

    int SnacksNum;

    boolean WifiConnected = false;
    boolean MobileDataConnected = false;

    SharedPreferences SnacksPreferences;
    SharedPreferences.Editor myEditor;
    private static final String USER_PREFS = "SnacksNum";

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snacks3);
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

        SnacksPreferences = getSharedPreferences(USER_PREFS, Context.MODE_PRIVATE);
        SnacksNum = SnacksPreferences.getInt("SnacksNum", 0);

        SnackNumberTxt = findViewById(R.id.bNumTxt);
        SnackNumberTxt.setText(Integer.toString(SnacksNum));

        stepsTxt = findViewById(R.id.stepsTxt1);
        caloriesTxt = findViewById(R.id.caloriesTxt1);

        initData(SnacksNum);
    }

    private void initExitBtn() {
        exitBtn.setOnClickListener(v -> {
            Intent intent = new Intent(Snacks3.this, Food_Diet.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
    }

    private void initNextBtn() {
        nextBtn.setOnClickListener(v -> {
            SnacksNum++;
            if (SnacksNum > 31) {
                SnacksNum = 1;
            }
            SnacksPreferences = getSharedPreferences(USER_PREFS, Context.MODE_PRIVATE);
            myEditor = SnacksPreferences.edit();
            myEditor.putInt("SnacksNum", SnacksNum);
            myEditor.apply();

            Intent intent = new Intent(Snacks3.this, Snacks3.class);
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
                Toast.makeText(Snacks3.this, "You are using mobile data connection", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(Snacks3.this, "NO CONNECTION!", Toast.LENGTH_SHORT).show();
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
        Call<snacksResult> call = retrofitInterface.executeSnacks(map);
        call.enqueue(new Callback<snacksResult>() {
            @Override
            public void onResponse(@NonNull Call<snacksResult> call, @NonNull Response<snacksResult> response) {
                if (response.code() == 200) {
                    snacksResult result = response.body();
                    assert result != null;
                    String steps = result.getSteps();
                    String calories = result.getCalories();

                    stepsTxt.setText(steps);
                    caloriesTxt.setText(calories);

                } else if (response.code() == 400) {
                    Toast.makeText(Snacks3.this, "server error", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(@NonNull Call<snacksResult> call, @NonNull Throwable t) {
                Toast.makeText(Snacks3.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void initBackBtn() {
        backBtn.setOnClickListener(v -> {
            SnacksNum--;
            if (SnacksNum < 1) {
                SnacksNum = 31;
            }
            SnacksPreferences = getSharedPreferences(USER_PREFS, Context.MODE_PRIVATE);
            myEditor = SnacksPreferences.edit();
            myEditor.putInt("SnacksNum", SnacksNum);
            myEditor.apply();

            Intent intent = new Intent(Snacks3.this, Snacks3.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
    }
}