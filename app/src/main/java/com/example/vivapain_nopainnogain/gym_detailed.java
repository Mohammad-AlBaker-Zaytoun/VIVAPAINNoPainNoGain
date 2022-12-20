package com.example.vivapain_nopainnogain;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class gym_detailed extends AppCompatActivity {

    Button backBtn, addGymBtn;
    ArrayList<gymDetailsModule> details = new ArrayList<>();
    GymDetailsAdapter adapter;

    private RetrofitInterface retrofitInterface;

    SharedPreferences UserPrefs;
    SharedPreferences myPreferences;
    SharedPreferences.Editor myEditor;
    private static final String USER_PREFS = "signedIn";
    private static final String USER_CHOICE = "Selection";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gym_detailed);
        Objects.requireNonNull(getSupportActionBar()).hide();

        addGymBtn = findViewById(R.id.addGymBTN);

        String baseURL = "http://10.0.2.2:3000";
        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseURL).addConverterFactory(GsonConverterFactory.create()).build();
        retrofitInterface = retrofit.create(RetrofitInterface.class);

        UserPrefs = getSharedPreferences(USER_PREFS, Context.MODE_PRIVATE);
        String userType = UserPrefs.getString("userType", "userType");
        if (userType.equals("GymOwner")) {
            addGymBtn.setVisibility(View.VISIBLE);
        } else if (userType.equals("normalUser")) {
            addGymBtn.setVisibility(View.INVISIBLE);
        }

        initAddGymBtn();


        backBtn = findViewById(R.id.BackBtn);
        initBackBtn();
    }

    private void initAddGymBtn() {
        addGymBtn.setOnClickListener(v -> {
            Intent intent = new Intent(gym_detailed.this, AddGym.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
    }

    private void getGymsList() {
        Call<ArrayList<gymDetailsModule>> call = retrofitInterface.executeGymDetails();
        call.enqueue(new Callback<ArrayList<gymDetailsModule>>() {
            @Override
            public void onResponse(@NonNull Call<ArrayList<gymDetailsModule>> call, @NonNull Response<ArrayList<gymDetailsModule>> response) {
                if (response.code() == 200) {
                    details = response.body();
                } else if (response.code() == 400) {
                    Toast.makeText(gym_detailed.this, "server error - 400", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ArrayList<gymDetailsModule>> call, @NonNull Throwable t) {
                Toast.makeText(gym_detailed.this, "FAILED    " + t.getMessage(), Toast.LENGTH_LONG).show();
                Log.w("error failed list", t.getMessage());
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getGymsList();
        details = PrefConfig.readListFromPref(this);
        if (details.size() > 0) {
            RecyclerView recyclerView = findViewById(R.id.mRecyclerView);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);
            adapter = new GymDetailsAdapter(details);
            adapter.setOnItemClickListener(onItemClickListener);
            recyclerView.setAdapter(adapter);
        } else {
            Toast.makeText(gym_detailed.this, "SERVER ERROR!", Toast.LENGTH_SHORT).show();
        }
    }

    private final View.OnClickListener onItemClickListener = view -> {
        RecyclerView.ViewHolder vh = (RecyclerView.ViewHolder) view.getTag();
        int position = vh.getAbsoluteAdapterPosition();

        myPreferences = getSharedPreferences(USER_CHOICE, Context.MODE_PRIVATE);
        myEditor = myPreferences.edit();
        myEditor.putInt("userChoice", position);
        myEditor.apply();

        Intent intent = new Intent(gym_detailed.this, SpecificGymDetailed.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    };

    private void initBackBtn() {
        backBtn.setOnClickListener(v -> {
            Intent intent = new Intent(gym_detailed.this, Gym_Near_By.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
    }
}