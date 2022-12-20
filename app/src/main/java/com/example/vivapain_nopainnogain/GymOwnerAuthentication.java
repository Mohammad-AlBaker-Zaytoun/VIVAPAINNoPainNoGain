package com.example.vivapain_nopainnogain;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GymOwnerAuthentication extends AppCompatActivity {

    private RetrofitInterface retrofitInterface;

    TextView continueWithoutSignup;
    Button backBtn;

    boolean WifiConnected = false;
    boolean MobileDataConnected = false;

    SharedPreferences myPreferences;
    SharedPreferences.Editor myEditor;
    private static final String USER_PREFS = "signedIn";

    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat sdf = new SimpleDateFormat("y:M");


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gym_authentication);
        Objects.requireNonNull(getSupportActionBar()).hide();

        backBtn = findViewById(R.id.BackBtn);
        initBackBtn();

        SharedPreferences settingsPrefs = getSharedPreferences("switch_prefs", Context.MODE_PRIVATE);
        boolean isChecked = settingsPrefs.getBoolean("switch_status", true);
        if (isChecked) {
            initCheckWifi();
        }

        continueWithoutSignup = findViewById(R.id.continueWithoutSignup);
        init_continueWithoutSignup();

        String baseURL = "http://10.0.2.2:3000";
        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseURL).addConverterFactory(GsonConverterFactory.create()).build();
        retrofitInterface = retrofit.create(RetrofitInterface.class);

        findViewById(R.id.login).setOnClickListener(view -> handleLoginDialog());

        findViewById(R.id.signup).setOnClickListener(view -> handleSignupDialog());

    }

    private void initBackBtn() {
        backBtn.setOnClickListener(v -> {
            Intent intent = new Intent(GymOwnerAuthentication.this, globalAuthentication.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
    }

    private void init_continueWithoutSignup() {
        continueWithoutSignup.setOnClickListener(v -> {
            Intent intent = new Intent(GymOwnerAuthentication.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        });
    }

    private void initCheckWifi() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            WifiConnected = networkInfo.getType() == ConnectivityManager.TYPE_WIFI;
            MobileDataConnected = networkInfo.getType() == ConnectivityManager.TYPE_MOBILE;
            if (!WifiConnected && MobileDataConnected) {
                Toast.makeText(GymOwnerAuthentication.this, "You are using mobile data connection", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(GymOwnerAuthentication.this, "NO CONNECTION!", Toast.LENGTH_SHORT).show();
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


    private void handleSignupDialog() {
        View view = getLayoutInflater().inflate(R.layout.signup_dialog, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view).show();

        Button signupBtn = view.findViewById(R.id.signup);
        EditText nameEdit = view.findViewById(R.id.nameEdit);
        EditText emailEdit = view.findViewById(R.id.emailEdit);
        EditText passwordEdit = view.findViewById(R.id.passwordEdit);
        EditText ageEdit = view.findViewById(R.id.ageEdit);
        EditText weightEdit = view.findViewById(R.id.weightEdit);

        signupBtn.setOnClickListener(view1 -> {
            HashMap<String, String> map = new HashMap<>();
            String date = sdf.format(new Date(new Date().getTime()));
            map.put("date", date);
            map.put("CreatedAt", date);
            map.put("name", nameEdit.getText().toString());
            map.put("email", emailEdit.getText().toString());
            map.put("password", passwordEdit.getText().toString());
            map.put("age", ageEdit.getText().toString());
            map.put("weight", weightEdit.getText().toString());

            Call<Void> call = retrofitInterface.executeSignup(map);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                    if (response.code() == 200) {
                        Toast.makeText(GymOwnerAuthentication.this, "Signed up successfully", Toast.LENGTH_LONG).show();
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(GymOwnerAuthentication.this);

                        builder1.setTitle("Hello " + nameEdit.getText().toString());
                        builder1.setMessage(" Email: " + emailEdit.getText().toString() + "\n License: Gym Owner");
                        builder1.setPositiveButton("Dismiss", (dialog, which) -> toProfile());

                        builder1.show();

                        myPreferences = getSharedPreferences(USER_PREFS, Context.MODE_PRIVATE);
                        myEditor = myPreferences.edit();
                        myEditor.putString("userType", "GymOwner");
                        myEditor.putString("userCreatedAt", date);
                        myEditor.putString("userName", nameEdit.getText().toString());
                        myEditor.putString("userEmail", emailEdit.getText().toString());
                        myEditor.putString("userPassword", passwordEdit.getText().toString());
                        myEditor.putInt("userAge", Integer.parseInt((ageEdit.getText().toString())));
                        myEditor.putInt("userWeight", Integer.parseInt((weightEdit.getText().toString())));
                        myEditor.putInt("userLostWeight", 0);
                        myEditor.putInt("userGainedWeight", 0);
                        myEditor.putInt("userTrainedHrs", 0);
                        myEditor.apply();


                    } else if (response.code() == 400) {
                        Toast.makeText(GymOwnerAuthentication.this, "Already registered", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                    Toast.makeText(GymOwnerAuthentication.this, t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });

        });

    }

    private void toProfile() {
        Intent intent = new Intent(GymOwnerAuthentication.this, Profile.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    private void handleLoginDialog() {
        View view = getLayoutInflater().inflate(R.layout.login_dialog, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view).show();

        Button loginBtn = view.findViewById(R.id.login);
        EditText emailEdit = view.findViewById(R.id.emailEdit);
        EditText passwordEdit = view.findViewById(R.id.passwordEdit);

        loginBtn.setOnClickListener(view1 -> {
            HashMap<String, String> map = new HashMap<>();

            map.put("email", emailEdit.getText().toString());
            map.put("password", passwordEdit.getText().toString());

            Call<LoginResult> call = retrofitInterface.executeLogin(map);
            call.enqueue(new Callback<LoginResult>() {
                @Override
                public void onResponse(@NonNull Call<LoginResult> call, @NonNull Response<LoginResult> response) {
                    if (response.code() == 200) {
                        LoginResult result = response.body();
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(GymOwnerAuthentication.this);
                        assert result != null;
                        builder1.setTitle("Hello " + result.getName());
                        builder1.setMessage(" Email: " + result.getEmail() + "\n License: Gym Owner");
                        builder1.setPositiveButton("Dismiss", (dialog, which) -> toProfile());

                        builder1.show();

                        myPreferences = getSharedPreferences(USER_PREFS, Context.MODE_PRIVATE);
                        myEditor = myPreferences.edit();
                        myEditor.putString("userType", "GymOwner");
                        myEditor.putString("userName", result.getName());
                        myEditor.putString("userEmail", result.getEmail());
                        myEditor.putString("userPassword", result.getPassword());
                        myEditor.putInt("userAge", result.getAge());
                        myEditor.putInt("userWeight", result.getWeight());
                        myEditor.putInt("userLostWeight", result.getLostWeight());
                        myEditor.putInt("userGainedWeight", result.getLostWeight());
                        myEditor.putInt("userTrainedHrs", result.getTrainedHrs());
                        myEditor.apply();
                    } else if (response.code() == 400) {
                        Toast.makeText(GymOwnerAuthentication.this, "Wrong Credentials", Toast.LENGTH_LONG).show();
                    }

                }

                @Override
                public void onFailure(@NonNull Call<LoginResult> call, @NonNull Throwable t) {
                    Toast.makeText(GymOwnerAuthentication.this, t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        });

    }
}