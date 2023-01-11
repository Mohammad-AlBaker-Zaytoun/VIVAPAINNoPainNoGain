package com.example.vivapain_nopainnogain;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChangeData extends AppCompatActivity {

    Button saveBTN, backBtn;
    EditText age, weightLost, trainedHours, weightGained;
    private RetrofitInterface retrofitInterface;

    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat sdf = new SimpleDateFormat("y:M");


    SharedPreferences.Editor myEditor;
    SharedPreferences UserPrefs;
    SharedPreferences DATEe;
    private static final String USER_PREFS = "signedIn";
    private static final String DATE = "DATE";

    LocalDate todayDate;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_data);
        Objects.requireNonNull(getSupportActionBar()).hide();

        age = findViewById(R.id.AgeET);
        weightLost = findViewById(R.id.WeightET);
        weightGained = findViewById(R.id.weightGainedEditTxt);
        trainedHours = findViewById(R.id.TrainingHET);
        saveBTN = findViewById(R.id.SaveBTN);
        backBtn = findViewById(R.id.BackBtn);


        String baseURL = "http://10.0.2.2:3000";
        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseURL).addConverterFactory(GsonConverterFactory.create()).build();
        retrofitInterface = retrofit.create(RetrofitInterface.class);

        initSaveBtn();
        initBackBtn();
    }

    private void initSaveBtn() {
        saveBTN.setOnClickListener(v -> {
            UserPrefs = getSharedPreferences(USER_PREFS, Context.MODE_PRIVATE);
            DATEe = getSharedPreferences(DATE, Context.MODE_PRIVATE);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                todayDate = LocalDate.now();
            }


            String UName = UserPrefs.getString("userName", "Username");
            int UAge = Integer.parseInt(age.getText().toString());
            int UWeightLost = Integer.parseInt(weightLost.getText().toString());
            int UWeightGained = Integer.parseInt(weightGained.getText().toString());
            int UTrainedHrz = Integer.parseInt(trainedHours.getText().toString());
            int oldUWeight = UserPrefs.getInt("userWeight", 1);
            int UWeight = oldUWeight - UWeightLost + UWeightGained;
            String CreatedAt = UserPrefs.getString("userCreatedAt", "2022:12");
            myEditor = UserPrefs.edit();
            myEditor.putInt("userAge", UAge);
            myEditor.putInt("userWeight", UWeight);
            myEditor.putInt("userLostWeight", UWeightLost);
            myEditor.putInt("userGainedWeight", UWeightGained);
            myEditor.putInt("userTrainedHrs", UTrainedHrz);
            myEditor.apply();

            myEditor = DATEe.edit();
            myEditor.putString("DATE", String.valueOf(todayDate));
            myEditor.apply();

            int UTrainedHrs = Integer.parseInt(trainedHours.getText().toString());


            String userType = UserPrefs.getString("userType", "userType");
            if (userType.equals("GymOwner")) {
                HashMap<String, String> map = new HashMap<>();
                String date = sdf.format(new Date(new Date().getTime()));
                map.put("date", date);
                map.put("lastUpdated", String.valueOf(todayDate));
                map.put("CreatedAt", CreatedAt);
                map.put("email", UserPrefs.getString("userEmail", "email"));
                map.put("password", UserPrefs.getString("userPassword", "password"));
                map.put("name", UName);
                map.put("age", String.valueOf(UAge));
                map.put("weight", String.valueOf(UWeight));
                map.put("lostWeight", String.valueOf(UWeightLost));
                map.put("gainedWeight", String.valueOf(UWeightGained));
                map.put("trainedHrs", String.valueOf(UTrainedHrs));

                Call<Void> call = retrofitInterface.executeUpdate(map);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                        if (response.code() == 200) {
                            Toast.makeText(ChangeData.this, "Updated successfully!", Toast.LENGTH_LONG).show();
                        } else if (response.code() == 400) {
                            Toast.makeText(ChangeData.this, "Error updating collection!", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                        Toast.makeText(ChangeData.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            } else if (userType.equals("normalUser")) {
                HashMap<String, String> map = new HashMap<>();
                String date = sdf.format(new Date(new Date().getTime()));
                map.put("date", date);
                map.put("lastUpdated", String.valueOf(todayDate));
                map.put("CreatedAt", CreatedAt);
                map.put("email", UserPrefs.getString("userEmail", "email"));
                map.put("password", UserPrefs.getString("userPassword", "password"));
                map.put("name", UName);
                map.put("age", String.valueOf(UAge));
                map.put("weight", String.valueOf(UWeight));
                map.put("gainedWeight", String.valueOf(UWeightGained));
                map.put("lostWeight", String.valueOf(UWeightLost));
                map.put("trainedHrs", String.valueOf(UTrainedHrs));

                Call<Void> call = retrofitInterface.executeUpdate1(map);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                        if (response.code() == 200) {
                            Toast.makeText(ChangeData.this, "Updated successfully!", Toast.LENGTH_LONG).show();
                        } else if (response.code() == 400) {
                            Toast.makeText(ChangeData.this, "Error updating collection!", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                        Toast.makeText(ChangeData.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

            }

            Intent intent = new Intent(ChangeData.this, Profile.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        });
    }

    private void initBackBtn() {
        backBtn.setOnClickListener(v -> {
            Intent intent = new Intent(ChangeData.this, Profile.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        });
    }
}