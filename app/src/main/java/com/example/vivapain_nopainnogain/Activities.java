package com.example.vivapain_nopainnogain;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Objects;

public class Activities extends AppCompatActivity {

    ArrayList<ActivityClass> activity;
    ActivityAdapter adapter;
    ImageButton backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activities);
        Objects.requireNonNull(getSupportActionBar()).hide();
        backBtn = findViewById(R.id.BackImageBtn);
        initBackBtn();
    }

    @Override
    public void onResume() {
        super.onResume();
        VivaDataSource ds = new VivaDataSource(this);
        try {
            ds.open();
            activity = ds.getActivities();
            ds.close();
            if (activity.size() > 0) {
                RecyclerView recyclerView = findViewById(R.id.ActRV);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
                recyclerView.setLayoutManager(layoutManager);
                adapter = new ActivityAdapter(activity);
                adapter.setOnItemClickListener(onItemClickListener);
                recyclerView.setAdapter(adapter);
            } else {
                Intent intent = new Intent(Activities.this, MainActivity.class);
                startActivity(intent);
                finish();
            }

        } catch (Exception e) {
            Toast.makeText(this, "Error retrieving Activities",
                    Toast.LENGTH_LONG).show();
        }
    }

    private void initBackBtn() {
        backBtn.setOnClickListener(v -> {
            Intent intent = new Intent(Activities.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
    }

    private final View.OnClickListener onItemClickListener = view -> {
        Intent intent = null;
        RecyclerView.ViewHolder vh = (RecyclerView.ViewHolder) view.getTag();
        int position = vh.getAbsoluteAdapterPosition();
        if (position == 0) {
            intent = new Intent(Activities.this, ChestWorkout.class);
        }
        if (position == 1) {
            intent = new Intent(Activities.this, LegsWorkout.class);
        }
        if (position == 2) {
            intent = new Intent(Activities.this, AbsWorkout.class);
        }
        if (position == 3) {
            intent = new Intent(Activities.this, BicepWorkout.class);
        }
        if (position == 4) {
            intent = new Intent(Activities.this, TricepsWorkout.class);
        }
        startActivity(intent);
    };
}