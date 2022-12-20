package com.example.vivapain_nopainnogain;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class Profile extends AppCompatActivity {

    Button changeData;
    TextView name, age, weight, weightLoss, weightGained, trainedHours;
    TextView rating;
    Button backBtn, sign_inBtn, graphBtn;

    SharedPreferences UserPrefs;
    private static final String USER_PREFS = "signedIn";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Objects.requireNonNull(getSupportActionBar()).hide();

        sign_inBtn = findViewById(R.id.signinBtn);
        graphBtn = findViewById(R.id.graphBtn);
        age = findViewById(R.id.AgetxtView);
        name = findViewById(R.id.NametxtView);
        weight = findViewById(R.id.weightTxtView);
        weightLoss = findViewById(R.id.weightLossTxtView);
        weightGained = findViewById(R.id.GainedWeightTxt);
        trainedHours = findViewById(R.id.TrainedHoursTxtView);
        rating = findViewById(R.id.RatingTxtView);
        changeData = findViewById(R.id.ChangeBtn);
        backBtn = findViewById(R.id.BackBtn);
        initGraphBtn();
        initSignInBtn();
        initBackBtn();
        LoadLastProfile();
        try {
            initChangeData();
        } catch (Exception e) {
            Log.d("Error while intent", e.getMessage());
        }

        initRate();
    }

    private void initGraphBtn() {
        graphBtn.setOnClickListener(v -> {
            Intent intent = new Intent(Profile.this, graphC.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
    }

    @SuppressLint("SetTextI18n")
    private void LoadLastProfile() { //Get user data from shared preference

        UserPrefs = getSharedPreferences(USER_PREFS, Context.MODE_PRIVATE);

        String UserName = UserPrefs.getString("userName", "username");
        int UserWeight = UserPrefs.getInt("userWeight", 0);
        int UserAge = UserPrefs.getInt("userAge", 0);
        int UserLostWeight = UserPrefs.getInt("userLostWeight", 0);
        int UserGainedWeight = UserPrefs.getInt("userGainedWeight", 0);
        int UserTrainedHrs = UserPrefs.getInt("userTrainedHrs", 0);


        name.setText(UserName);
        age.setText(Integer.toString(UserAge));
        weight.setText(Integer.toString(UserWeight));
        weightLoss.setText(Integer.toString(UserLostWeight));
        weightGained.setText(Integer.toString(UserGainedWeight));
        trainedHours.setText(Integer.toString(UserTrainedHrs));
    }

    private void initSignInBtn() {
        sign_inBtn.setOnClickListener(v -> {
            Intent intent = new Intent(Profile.this, globalAuthentication.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        });
    }

    private void initBackBtn() {
        backBtn.setOnClickListener(v -> {
            Intent intent = new Intent(Profile.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
    }

    private void initChangeData() {
        changeData.setOnClickListener(v -> {
            Intent intent = new Intent(Profile.this, ChangeData.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
    }

    @SuppressLint("SetTextI18n")
    private void initRate() {
        String weightLossText = weightLoss.getText().toString();
        String TrainedHoursText = trainedHours.getText().toString();
        double weightLoss = Double.parseDouble(weightLossText);
        double TrainedHours = Double.parseDouble(TrainedHoursText);
        if (weightLoss > 7 && TrainedHours > 30) {
            rating.setText("VIVA PAIN! Excellent work!");
        } else if (weightLoss > 7) {
            rating.setText("Train More!");
        } else if (TrainedHours > 30) {
            rating.setText("Manage your Diet!");
        } else {
            rating.setText("Your not doing well at all!");
        }
    }

}