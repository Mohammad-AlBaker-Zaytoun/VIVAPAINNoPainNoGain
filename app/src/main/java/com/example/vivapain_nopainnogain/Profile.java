package com.example.vivapain_nopainnogain;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Profile extends AppCompatActivity {

    Button changeData;
    TextView name, age, weight, weightLoss, weightGained, trainedHours;
    TextView rating;
    Button backBtn, sign_inBtn, graphBtn;
    ImageView lockImageView;

    long daysDiff;

    ArrayList<UserHistoryModule> details = new ArrayList<>();

    SharedPreferences UserPrefs;
    SharedPreferences myPreferences;
    SharedPreferences.Editor myEditor;

    private static final String USER_PREFS = "signedIn";
    private static final String DATE = "DATE";

    boolean isLogged = false;

    private RetrofitInterface retrofitInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Objects.requireNonNull(getSupportActionBar()).hide();

        String baseURL = "http://10.0.2.2:3000";
        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseURL).addConverterFactory(GsonConverterFactory.create()).build();
        retrofitInterface = retrofit.create(RetrofitInterface.class);

        myPreferences = getSharedPreferences(DATE, Context.MODE_PRIVATE);
        daysDiff = myPreferences.getLong("DATE_DIFF", 0);


        lockImageView = findViewById(R.id.lockImageView);
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

        initLock();

        initDateDifference();

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

    private void initLock() {
        if (daysDiff < 30) {
            lockImageView.setVisibility(View.VISIBLE);
        } else {
            lockImageView.setVisibility(View.INVISIBLE);
        }
    }

    private void initDateDifference() {
        getLastUpdated();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            myPreferences = getSharedPreferences(DATE, Context.MODE_PRIVATE);
            String lastUpdated = myPreferences.getString("LAST_DATE", String.valueOf(LocalDate.now()));
            String NOW = myPreferences.getString("DATE", String.valueOf(LocalDate.now()));

            LocalDate dateBefore = LocalDate.parse(lastUpdated);
            LocalDate dateAfter = LocalDate.parse(NOW);
            daysDiff = dateBefore.until(dateAfter, ChronoUnit.DAYS);
            Log.d("DAYS DIF", String.valueOf(daysDiff));

            myPreferences = getSharedPreferences(DATE, Context.MODE_PRIVATE);
            myEditor = myPreferences.edit();
            myEditor.putLong("DATE_DIFF", daysDiff);
            myEditor.apply();
        }

    }

    private void getLastUpdated() {
        UserPrefs = getSharedPreferences(USER_PREFS, Context.MODE_PRIVATE);
        String userType = UserPrefs.getString("userType", "userType");
        String name = UserPrefs.getString("userName", "userName");
        Log.d("USERNAME", name);
        Log.d("USER TYPE", userType);
        String userEmail = UserPrefs.getString("userEmail", "userEmail");
        String userPassword = UserPrefs.getString("userPassword", "userPassword");

        if (userType.equals("GymOwner")) {
            HashMap<String, String> map = new HashMap<>();

            map.put("email", userEmail);
            map.put("password", userPassword);

            Call<LoginResult> call = retrofitInterface.executeLogin(map);
            call.enqueue(new Callback<LoginResult>() {
                @Override
                public void onResponse(@NonNull Call<LoginResult> call, @NonNull Response<LoginResult> response) {
                    if (response.code() == 200) {
                        LoginResult result = response.body();
                        assert result != null;
                        if (result.getLastUpdated() == null || result.getLastUpdated().equals("0")) {
                            myPreferences = getSharedPreferences(DATE, Context.MODE_PRIVATE);
                            myEditor = myPreferences.edit();
                            myEditor.putString("LAST_DATE", "2001-1-1");
                            myEditor.apply();
                        } else {
                            myPreferences = getSharedPreferences(DATE, Context.MODE_PRIVATE);
                            myEditor = myPreferences.edit();
                            myEditor.putString("LAST_DATE", result.getLastUpdated());
                            myEditor.apply();
                        }
                    } else if (response.code() == 400) {
                        Log.d("ERROR 400", "UNKNOWN ERROR!");
                    }

                }

                @Override
                public void onFailure(@NonNull Call<LoginResult> call, @NonNull Throwable t) {
                    Log.d("ERROR", "UNKNOWN ERROR!");
                }
            });
        } else if (userType.equals("normalUser")) {
            HashMap<String, String> map = new HashMap<>();

            map.put("email", userEmail);
            map.put("password", userPassword);

            Call<LoginResult1> call = retrofitInterface.executeLogin1(map);
            call.enqueue(new Callback<LoginResult1>() {
                @Override
                public void onResponse(@NonNull Call<LoginResult1> call, @NonNull Response<LoginResult1> response) {
                    if (response.code() == 200) {
                        LoginResult1 result = response.body();
                        assert result != null;
                        if (result.getLastUpdated() == null || result.getLastUpdated().equals("0")) {
                            myPreferences = getSharedPreferences(DATE, Context.MODE_PRIVATE);
                            myEditor = myPreferences.edit();
                            myEditor.putString("LAST_DATE", "2001-1-1");
                            myEditor.apply();
                        } else {
                            myPreferences = getSharedPreferences(DATE, Context.MODE_PRIVATE);
                            myEditor = myPreferences.edit();
                            myEditor.putString("LAST_DATE", result.getLastUpdated());
                            myEditor.apply();
                        }
                    } else if (response.code() == 400) {
                        Log.d("ERROR 400", "UNKNOWN ERROR!");
                    }

                }

                @Override
                public void onFailure(@NonNull Call<LoginResult1> call, @NonNull Throwable t) {
                    Log.d("ERROR", "UNKNOWN ERROR!");
                }
            });
        }

    }

    private void getHistorySilent() {
        UserPrefs = getSharedPreferences(USER_PREFS, Context.MODE_PRIVATE);
        String userType = UserPrefs.getString("userType", "userType");
        String name = UserPrefs.getString("userName", "userName");
        Log.d("USERNAME", name);
        Log.d("USER TYPE", userType);
        if (userType.equals("GymOwner")) {
            HashMap<String, String> map = new HashMap<>();
            map.put("name", name);
            Call<ArrayList<UserHistoryModule>> call = retrofitInterface.executeGymOwnerHistory(map);
            call.enqueue(new Callback<ArrayList<UserHistoryModule>>() {
                @SuppressLint("LongLogTag")
                @Override
                public void onResponse(@NonNull Call<ArrayList<UserHistoryModule>> call, @NonNull Response<ArrayList<UserHistoryModule>> response) {
                    if (response.code() == 200) {
                        details = response.body();
                        PrefConfig_History.writeListInPref(getApplicationContext(), details);
                        Log.d("GymOwner History Amount", String.valueOf(details.size()));

                        assert details != null;
                        if (details.size() != 4) {
                            Log.d("DATA HIST", "HIST NOT ENOUGH");
                        }

                        Log.d("GymOwner History Retrieve", "GymOwner history retrieval was a success.");
                    } else if (response.code() == 400) {
                        Log.d("GymOwner History Retrieve", "GymOwner history retrieval failed.");
                    }
                }

                @SuppressLint("LongLogTag")
                @Override
                public void onFailure(@NonNull Call<ArrayList<UserHistoryModule>> call, @NonNull Throwable t) {
                    Log.w("GymOwner History Retrieve", "GymOwner history retrieval failed ---> " + t.getMessage());
                }
            });
        } else if (userType.equals("normalUser")) {
            HashMap<String, String> map = new HashMap<>();
            map.put("name", name);
            Call<ArrayList<UserHistoryModule>> call = retrofitInterface.executeUserHistory(map);
            call.enqueue(new Callback<ArrayList<UserHistoryModule>>() {
                @SuppressLint("LongLogTag")
                @Override
                public void onResponse(@NonNull Call<ArrayList<UserHistoryModule>> call, @NonNull Response<ArrayList<UserHistoryModule>> response) {
                    if (response.code() == 200) {
                        details = response.body();
                        PrefConfig_History.writeListInPref(getApplicationContext(), details);
                        Log.d("User History Amount", String.valueOf(details.size()));
                        assert details != null;
                        if (details.size() != 4) {
                            Log.d("DATA HIST", "HIST NOT ENOUGH");
                        }

                        Log.d("User History Retrieve", "User history retrieval was a success.");
                    } else if (response.code() == 400) {
                        Log.d("User History Retrieve", "User history retrieval failed.");
                    }
                }

                @SuppressLint("LongLogTag")
                @Override
                public void onFailure(@NonNull Call<ArrayList<UserHistoryModule>> call, @NonNull Throwable t) {
                    Log.w("User History Retrieve", "User history retrieval failed" + t.getMessage());
                }
            });
        }
    }


    private void initGraphBtn() {
        UserPrefs = getSharedPreferences(USER_PREFS, Context.MODE_PRIVATE);
        isLogged = UserPrefs.getBoolean("isLogged", false);
        getHistorySilent();

        if (isLogged) {
            graphBtn.setVisibility(View.VISIBLE);
        }
        graphBtn.setOnClickListener(v -> {

            if (details.size() != 4) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(Profile.this);

                builder1.setTitle("Not enough data");
                builder1.setMessage("To access graph progression, you need to use the app for at least 4 month. \nThis is necessary to generate your progress report. \n\nNOTE: YOU HAVE TO UPDATE YOUR DATA EVERY MONTH IN PROFILE PAGE.");
                builder1.setPositiveButton("Dismiss", (dialog, which) -> NONE());

                builder1.show();
            } else {
                Intent intent = new Intent(Profile.this, graphC.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                ProgressDialog progressDialog = new ProgressDialog(this);
                progressDialog.setMessage("Loading...");
                progressDialog.setCancelable(false);
                progressDialog.show();
                startActivity(intent);
                progressDialog.dismiss();


            }
        });
    }

    private void NONE() {
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
        getHistorySilent();
        changeData.setOnClickListener(v -> {
            if (daysDiff < 30) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(Profile.this);

                builder1.setTitle("Can't change now!");
                builder1.setMessage("You can only change your data once 30 days are passed.");
                builder1.setPositiveButton("Dismiss", (dialog, which) -> NONE());

                builder1.show();
            } else {
                Intent intent = new Intent(Profile.this, ChangeData.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
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