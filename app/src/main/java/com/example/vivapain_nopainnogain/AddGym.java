package com.example.vivapain_nopainnogain;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddGym extends AppCompatActivity {

    Button cancelBtn, requestBtn;
    EditText gName, gAddress, gNumber, gGenderAllowed, gMPlan;

    private RetrofitInterface retrofitInterface;

    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat sdf = new SimpleDateFormat("y:M");

    SharedPreferences UserPrefs;
    private static final String USER_PREFS = "signedIn";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_gym);

        Objects.requireNonNull(getSupportActionBar()).hide();

        String baseURL = "http://10.0.2.2:3000";
        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseURL).addConverterFactory(GsonConverterFactory.create()).build();
        retrofitInterface = retrofit.create(RetrofitInterface.class);

        cancelBtn = findViewById(R.id.cancelBtn);
        requestBtn = findViewById(R.id.requestBtn);

        gName = findViewById(R.id.editTextTextPersonName);
        gAddress = findViewById(R.id.editTextTextPersonName2);
        gNumber = findViewById(R.id.editTextTextPersonName3);
        gGenderAllowed = findViewById(R.id.editTextTextPersonName4);
        gMPlan = findViewById(R.id.editTextTextPersonName5);

        initCancel();
        initRequest();
    }

    private void initRequest() {
        requestBtn.setOnClickListener(v -> {

            UserPrefs = getSharedPreferences(USER_PREFS, Context.MODE_PRIVATE);
            String UserName = UserPrefs.getString("userName", "username");
            String UserEmail = UserPrefs.getString("userEmail", "userEmail");


            HashMap<String, String> map = new HashMap<>();
            String date = sdf.format(new Date(new Date().getTime()));
            map.put("RequestedAt", date);
            map.put("UserName", UserName);
            map.put("UserEmail", UserEmail);
            map.put("GymName", gName.getText().toString());
            map.put("GymAddress", gAddress.getText().toString());
            map.put("GymPhoneNumber", gNumber.getText().toString());
            map.put("GymAllowedGender", gGenderAllowed.getText().toString());
            map.put("GymMonthlyPlan", gMPlan.getText().toString());
            Call<Void> call = retrofitInterface.executeRequestG(map);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                    if (response.code() == 200) {
                        Toast.makeText(AddGym.this, "Request Sent", Toast.LENGTH_LONG).show();
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(AddGym.this);

                        builder1.setTitle("We have your request");
                        builder1.setMessage("Our team will check your gym details and call you later time to verify and validate your data. \n\nThank you for your request - VIVA PAIN DEV TEAM");
                        builder1.setPositiveButton("Dismiss", (dialog, which) -> toGymDetails());

                        builder1.show();
                    } else if (response.code() == 400) {
                        Toast.makeText(AddGym.this, "ALREADY REQUESTED!", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                    Toast.makeText(AddGym.this, t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        });
    }

    private void toGymDetails() {
        Intent intent = new Intent(AddGym.this, gym_detailed.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    private void initCancel() {

        Call<ArrayList<gymDetailsModule>> call = retrofitInterface.executeGymDetails();
        call.enqueue(new Callback<ArrayList<gymDetailsModule>>() {
            @Override
            public void onResponse(@NonNull Call<ArrayList<gymDetailsModule>> call, @NonNull Response<ArrayList<gymDetailsModule>> response) {
                if (response.code() == 200) {
                    ArrayList<gymDetailsModule> details = response.body();
                    PrefConfig.writeListInPref(getApplicationContext(), details);
                    Log.d("gyms detailed retrieve", "Gyms retrieval was a success.");
                } else if (response.code() == 400) {
                    Log.d("gyms detailed retrieve", "Gyms retrieval failed.");
                }
            }

            @Override
            public void onFailure(@NonNull Call<ArrayList<gymDetailsModule>> call, @NonNull Throwable t) {
                Log.w("gyms detailed retrieve", "Gyms retrieval failed" + t.getMessage());
            }
        });


        cancelBtn.setOnClickListener(v -> {
            Intent intent = new Intent(AddGym.this, gym_detailed.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        });
    }
}