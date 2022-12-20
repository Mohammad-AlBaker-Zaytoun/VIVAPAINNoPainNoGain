package com.example.vivapain_nopainnogain;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Objects;

public class SpecificGymDetailed extends AppCompatActivity {

    Button backBtn;
    TextView gName, gAddress, gNumber, gMonthlyPrice, gDailyPrice, gGenderAllowed, gWorkingTimes;
    RatingBar ratingBar;

    ArrayList<gymDetailsModule> details = new ArrayList<>();

    SharedPreferences myPreferences;
    private static final String USER_CHOICE = "Selection";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_gym_detailed);
        Objects.requireNonNull(getSupportActionBar()).hide();


        details = PrefConfig.readListFromPref(this);

        backBtn = findViewById(R.id.BackBtn);
        gName = findViewById(R.id.gymNameTxt);
        gAddress = findViewById(R.id.gLocationTxt);
        gNumber = findViewById(R.id.gPhoneNumberTxt);
        gMonthlyPrice = findViewById(R.id.gMonthlyPriceTxt);
        gDailyPrice = findViewById(R.id.gDailyEntryPriceTxt);
        gGenderAllowed = findViewById(R.id.gGenderAllowedTxt);
        gWorkingTimes = findViewById(R.id.gWorkingTimesTxt);

        ratingBar = findViewById(R.id.gymRatingBar);

        initBackBtn();

        initRatingBar();

        initData();

    }

    private void initRatingBar() {
        myPreferences = getSharedPreferences(USER_CHOICE, Context.MODE_PRIVATE);
        int gymProfile = myPreferences.getInt("userChoice", 0);
        int rating = details.get(gymProfile).getRating();
        ratingBar.setRating(rating);
        ratingBar.setEnabled(false);
    }

    @SuppressLint("SetTextI18n")
    private void initData() {
        myPreferences = getSharedPreferences(USER_CHOICE, Context.MODE_PRIVATE);
        int gymProfile = myPreferences.getInt("userChoice", 0);
        gName.setText(details.get(gymProfile).getName());
        gAddress.setText(details.get(gymProfile).getLocation());
        gNumber.setText(Integer.toString(details.get(gymProfile).getNumber()));
        gMonthlyPrice.setText(Integer.toString(details.get(gymProfile).getMonthPlanPrice()));
        gDailyPrice.setText(Integer.toString(details.get(gymProfile).getDailyEntryPrice()));
        gGenderAllowed.setText(details.get(gymProfile).getGenderAllowed());
        gWorkingTimes.setText(details.get(gymProfile).getWorkingTimes());
    }

    private void initBackBtn() {
        backBtn.setOnClickListener(v -> {
            Intent intent = new Intent(SpecificGymDetailed.this, gym_detailed.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
    }
}