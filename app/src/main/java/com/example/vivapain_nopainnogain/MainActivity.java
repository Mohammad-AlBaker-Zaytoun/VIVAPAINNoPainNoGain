package com.example.vivapain_nopainnogain;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

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

public class MainActivity extends AppCompatActivity {

    Button trainMuscle, diet;
    ImageButton settings, about, contactUs, profile;
    ImageButton homeRedirect, mapRedirect;
    boolean WifiConnected = false;
    boolean MobileDataConnected = false;
    boolean isLogged = false;

    private RetrofitInterface retrofitInterface;

    SharedPreferences myPreferences;
    SharedPreferences.Editor myEditor;

    SharedPreferences UserPrefs;
    private static final String USER_PREFS = "signedIn";
    private static final String DATE = "DATE";

    LocalDate todayDate;
    long daysDiff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).hide();

        String baseURL = "http://10.0.2.2:3000";
        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseURL).addConverterFactory(GsonConverterFactory.create()).build();
        retrofitInterface = retrofit.create(RetrofitInterface.class);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            todayDate = LocalDate.now();
            myPreferences = getSharedPreferences(DATE, Context.MODE_PRIVATE);
            myEditor = myPreferences.edit();
            myEditor.putString("DATE", String.valueOf(todayDate));
            myEditor.apply();
        }


        trainMuscle = findViewById(R.id.Activities);
        diet = findViewById(R.id.Get_Your_Diet);
        settings = findViewById(R.id.Settings);
        about = findViewById(R.id.AboutUs);
        contactUs = findViewById(R.id.ContactUs);
        homeRedirect = findViewById(R.id.Home);
        mapRedirect = findViewById(R.id.Map);
        profile = findViewById(R.id.profileImageBtn);
        SharedPreferences prefs = getSharedPreferences("prefs", Context.MODE_PRIVATE);
        boolean firstStart = prefs.getBoolean("firstStart", true);

        getGymsList();

        getHistory();

        getLastUpdated();

        UserPrefs = getSharedPreferences(USER_PREFS, Context.MODE_PRIVATE);
        isLogged = UserPrefs.getBoolean("isLogged", false);

        if (isLogged) {
            initDateDifference();
        } else {
            NONE();
        }

        if (firstStart) {
            showStartDialog();
        }
        initProfile();
        initTrainMuscle();
        initDiet();
        initSettings();
        initAbout();
        initContactUs();
        initHomeRedirect();
        initMapRedirect();
        SharedPreferences settingsPrefs = getSharedPreferences("switch_prefs", Context.MODE_PRIVATE);
        boolean isChecked = settingsPrefs.getBoolean("switch_status", true);
        if (isChecked) {
            initCheckWifi();
        }
    }

    private void NONE() {

    }

    private void initDateDifference() {
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

    private void getHistory() {
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
                        ArrayList<UserHistoryModule> details = response.body();
                        PrefConfig_History.writeListInPref(getApplicationContext(), details);
                        Log.d("GymOwner History Retrieve", "GymOwner history retrieval was a success.");
                        assert details != null;
                        Log.d("GymOwner History Amount", String.valueOf(details.size()));
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
                        ArrayList<UserHistoryModule> details = response.body();
                        PrefConfig_History.writeListInPref(getApplicationContext(), details);
                        Log.d("User History Retrieve", "User history retrieval was a success.");
                        assert details != null;
                        Log.d("User History Amount", String.valueOf(details.size()));
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

    private void getGymsList() {
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
    }

    private void initProfile() {
        profile.setOnClickListener(v -> {
            UserPrefs = getSharedPreferences(USER_PREFS, Context.MODE_PRIVATE);
            isLogged = UserPrefs.getBoolean("isLogged", false);
            Intent intent;
            if (isLogged) {
                intent = new Intent(MainActivity.this, Profile.class);
            } else {
                intent = new Intent(MainActivity.this, globalAuthentication.class);
            }
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);


        });
    }

    private void noConnectionDialog() {
        new AlertDialog.Builder(this)
                .setTitle("ALERT!")
                .setMessage("NO CONNECTION!")
                .setPositiveButton("Dismiss", (dialog, which) -> dialog.dismiss())
                .create().show();
    }

    private void showStartDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Welcome!")
                .setMessage("Thank you for downloading VIVA PAIN. \n Check activities page and VIVA THE PAIN!")
                .setPositiveButton("Dismiss", (dialog, which) -> dialog.dismiss())
                .create().show();

        SharedPreferences prefs = getSharedPreferences("prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("firstStart", false);
        editor.apply();
    }

    private void initCheckWifi() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            WifiConnected = networkInfo.getType() == ConnectivityManager.TYPE_WIFI;
            MobileDataConnected = networkInfo.getType() == ConnectivityManager.TYPE_MOBILE;
            if (!WifiConnected && MobileDataConnected) {
                Toast.makeText(MainActivity.this, "You are using mobile data connection", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(MainActivity.this, "NO CONNECTION!", Toast.LENGTH_SHORT).show();
            noConnectionDialog();
        }
    }

    private void initMapRedirect() {
        mapRedirect.setOnClickListener(v -> {
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                WifiConnected = networkInfo.getType() == ConnectivityManager.TYPE_WIFI;
                MobileDataConnected = networkInfo.getType() == ConnectivityManager.TYPE_MOBILE;
                if (!WifiConnected && MobileDataConnected) {
                    Toast.makeText(MainActivity.this, "You are using mobile data connection", Toast.LENGTH_LONG).show();
                }
                Intent intent = new Intent(MainActivity.this, Gym_Near_By.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            } else {
                Toast.makeText(MainActivity.this, "NO CONNECTION!", Toast.LENGTH_SHORT).show();
                noConnectionDialog();
            }
        });
    }

    private void initHomeRedirect() {
        homeRedirect.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
    }

    private void initContactUs() {
        contactUs.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Contact.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
    }

    private void initAbout() {
        about.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, About.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
    }

    private void initSettings() {
        settings.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Settings.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
    }

    private void initDiet() {
        diet.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Food_Diet.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
    }

    private void initTrainMuscle() {
        trainMuscle.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Activities.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
    }
}