package com.example.vivapain_nopainnogain;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Gym_Near_By extends FragmentActivity implements OnMapReadyCallback {

    GoogleMap map;
    Button dismiss, gym_details;
    boolean WifiConnected = false;
    boolean MobileDataConnected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gym_near_by);
        dismiss = findViewById(R.id.dismiss);
        gym_details = findViewById(R.id.gym_details);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
        initDismissBtn();
        initGymDetails();
        SharedPreferences settingsPrefs = getSharedPreferences("switch_prefs", Context.MODE_PRIVATE);
        boolean isChecked = settingsPrefs.getBoolean("switch_status", true);
        if (isChecked) {
            initCheckWifi();
        }
    }

    private void initGymDetails() {
        gym_details.setOnClickListener(v -> {
            Intent intent = new Intent(Gym_Near_By.this, gym_detailed.class);
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
                Toast.makeText(Gym_Near_By.this, "You are using mobile data connection", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(Gym_Near_By.this, "NO CONNECTION!", Toast.LENGTH_SHORT).show();
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

    private void initDismissBtn() {
        dismiss.setOnClickListener(v -> {
            Intent intent = new Intent(Gym_Near_By.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        //33.8938 , 35.5018 Beirut Latitude and Longitude
        map = googleMap;
        float zoomLevel = 16.0f; //This goes up to 21
        LatLng Beirut = new LatLng(33.8938, 35.5018);
        map.addMarker(new MarkerOptions().position(Beirut).title("Beirut"));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(Beirut, zoomLevel));
    }
}