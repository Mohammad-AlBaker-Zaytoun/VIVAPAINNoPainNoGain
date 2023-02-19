package com.example.vivapain_nopainnogain;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.ParseException;
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

@SuppressLint("UseSwitchCompatOrMaterialCode")
public class graphC extends AppCompatActivity {

    GraphView graph;
    Button backBtn;
    Switch switchGraph;
    SharedPreferences switchGraphStat;
    SharedPreferences.Editor myEditor;
    String key = "GraphSwitch";
    boolean STATS = false;

    int weight1 = 0;
    int weight2 = 0;
    int weight3 = 0;
    int weight4 = 0;

    int TrainingHr1 = 0;
    int TrainingHr2 = 0;
    int TrainingHr3 = 0;
    int TrainingHr4 = 0;

    String date1S = "";
    String date2S = "";
    String date3S = "";
    String date4S = "";

    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat sdf = new SimpleDateFormat("y:M");

    private RetrofitInterface retrofitInterface;

    ArrayList<UserHistoryModule> details = new ArrayList<>();

    SharedPreferences UserPrefs;
    private static final String USER_PREFS = "signedIn";


    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_c);
        Objects.requireNonNull(getSupportActionBar()).hide();

        String baseURL = "http://10.0.2.2:3000";
        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseURL).addConverterFactory(GsonConverterFactory.create()).build();
        retrofitInterface = retrofit.create(RetrofitInterface.class);

        details = PrefConfig_History.readListFromPref(this);

        initWeight_TrainedHrs();

        backBtn = findViewById(R.id.backBtn);
        switchGraph = findViewById(R.id.switch2);

        switchGraphStat = getSharedPreferences(key, MODE_PRIVATE);
        STATS = switchGraphStat.getBoolean("stats", false);
        switchGraph.setChecked(STATS);

        try {
            initGraph();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        initSwitch();
        initBackBtn();
        initSwitch();

        getHistory();

    }

    private void initWeight_TrainedHrs() {
        weight1 = details.get(0).getLostWeight();
        weight2 = details.get(1).getLostWeight();
        weight3 = details.get(2).getLostWeight();
        weight4 = details.get(3).getLostWeight();

        TrainingHr1 = details.get(0).getTrainedHrs();
        TrainingHr2 = details.get(1).getTrainedHrs();
        TrainingHr3 = details.get(2).getTrainedHrs();
        TrainingHr4 = details.get(3).getTrainedHrs();

        date1S = details.get(3).getDate();
        date2S = details.get(2).getDate();
        date3S = details.get(1).getDate();
        date4S = details.get(0).getDate();

        Log.d("GRAPH DATA POINT NS 1", String.valueOf(date1S));
        Log.d("GRAPH DATA POINT NS 2", String.valueOf(date2S));
        Log.d("GRAPH DATA POINT NS 3", String.valueOf(date3S));
        Log.d("GRAPH DATA POINT NS 4", String.valueOf(date4S));
    }

    private void initGraph() throws ParseException {
        if (STATS) {
            initGraphTrainedHours();
        } else {
            initGraphWeight();
        }
    }

    private void initSwitch() {
        switchGraph.setOnCheckedChangeListener((buttonView, isChecked) -> {
            STATS = switchGraph.isChecked();
            switchGraphStat = getSharedPreferences(key, MODE_PRIVATE);
            myEditor = switchGraphStat.edit();
            myEditor.putBoolean("stats", STATS);
            myEditor.apply();
            Refresh();
        });
    }

    private void Refresh() {
        Intent intent = new Intent(graphC.this, graphC.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void delay() {
        final Handler handler = new Handler();
        handler.postDelayed(() -> {
            //just a delay
        }, 20); // delay time in milliseconds


    }

    private void initGraphTrainedHours() throws ParseException {
        graph = findViewById(R.id.graph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>();

        Date date1 = sdf.parse(date1S);
        Date date2 = sdf.parse(date2S);
        Date date3 = sdf.parse(date3S);
        Date date4 = sdf.parse(date4S);
        Log.d("GRAPH DATA POINT 1", String.valueOf(date1));
        Log.d("GRAPH DATA POINT 2", String.valueOf(date2));
        Log.d("GRAPH DATA POINT 3", String.valueOf(date3));
        Log.d("GRAPH DATA POINT 4", String.valueOf(date4));

        assert date1 != null;
        series.appendData(new DataPoint(date1, TrainingHr1), true, 4);
        delay();
        assert date2 != null;
        series.appendData(new DataPoint(date2, TrainingHr2), true, 4);
        delay();
        assert date3 != null;
        series.appendData(new DataPoint(date3, TrainingHr3), true, 4);
        delay();
        assert date4 != null;
        series.appendData(new DataPoint(date4, TrainingHr4), true, 4);

        graph.addSeries(series);
        series.setAnimated(true);
        series.setThickness(7);
        series.setTitle("TRAINED HRS");
        graph.setTitle("PROGRESS");
        graph.setTitleTextSize(50);

        graph.getLegendRenderer().setVisible(true);
        graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);

        graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
            @Override
            public String formatLabel(double value, boolean isValueX) {
                if (isValueX) {
                    return sdf.format(new Date((long) value));
                } else {
                    return super.formatLabel(value, false);
                }

            }
        });

        GridLabelRenderer gridLabel = graph.getGridLabelRenderer();
        gridLabel.setHorizontalAxisTitleTextSize(25);
        gridLabel.setVerticalAxisTitle("TRAINED HRS");
        gridLabel.setVerticalAxisTitleTextSize(25);
    }

    private void initGraphWeight() throws ParseException {
        graph = findViewById(R.id.graph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>();

        Date date1 = sdf.parse(date1S);
        Date date2 = sdf.parse(date2S);
        Date date3 = sdf.parse(date3S);
        Date date4 = sdf.parse(date4S);
        Log.d("GRAPH DATA POINT 1", String.valueOf(date1));
        Log.d("GRAPH DATA POINT 2", String.valueOf(date2));
        Log.d("GRAPH DATA POINT 3", String.valueOf(date3));
        Log.d("GRAPH DATA POINT 4", String.valueOf(date4));

        assert date1 != null;
        series.appendData(new DataPoint(date1, weight1), false, 4);
        delay();
        assert date2 != null;
        series.appendData(new DataPoint(date2, weight2), false, 4);
        delay();
        assert date3 != null;
        series.appendData(new DataPoint(date3, weight3), false, 4);
        delay();
        assert date4 != null;
        series.appendData(new DataPoint(date4, weight4), false, 4);

        graph.addSeries(series);
        series.setAnimated(true);
        series.setThickness(7);
        series.setTitle("LOST WEIGHT");
        graph.setTitle("PROGRESS");
        graph.setTitleTextSize(50);

        graph.getLegendRenderer().setVisible(true);
        graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);

        graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
            @Override
            public String formatLabel(double value, boolean isValueX) {
                if (isValueX) {
                    return sdf.format(new Date((long) value));
                } else {
                    return super.formatLabel(value, false);
                }

            }
        });

        GridLabelRenderer gridLabel = graph.getGridLabelRenderer();
        gridLabel.setHorizontalAxisTitleTextSize(25);
        gridLabel.setVerticalAxisTitle("LOST WEIGHT");
        gridLabel.setVerticalAxisTitleTextSize(25);
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
                        Log.d("GymOwner History Retrieve", "GymOwner history retrieval was a success.");
                    } else if (response.code() == 400) {
                        Toast.makeText(graphC.this, "SERVER ERROR!", Toast.LENGTH_SHORT).show();
                        Log.d("GymOwner History Retrieve", "GymOwner history retrieval failed.");
                    }
                }

                @SuppressLint("LongLogTag")
                @Override
                public void onFailure(@NonNull Call<ArrayList<UserHistoryModule>> call, @NonNull Throwable t) {
                    Toast.makeText(graphC.this, "SERVER ERROR!", Toast.LENGTH_SHORT).show();
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
                        Log.d("User History Retrieve", "User history retrieval was a success.");
                    } else if (response.code() == 400) {
                        Toast.makeText(graphC.this, "SERVER ERROR!", Toast.LENGTH_SHORT).show();
                        Log.d("User History Retrieve", "User history retrieval failed.");
                    }
                }

                @SuppressLint("LongLogTag")
                @Override
                public void onFailure(@NonNull Call<ArrayList<UserHistoryModule>> call, @NonNull Throwable t) {
                    Toast.makeText(graphC.this, "SERVER ERROR!", Toast.LENGTH_SHORT).show();
                    Log.w("User History Retrieve", "User history retrieval failed" + t.getMessage());
                }
            });
        }
    }

    private void initBackBtn() {
        backBtn.setOnClickListener(v -> {
            Intent intent = new Intent(graphC.this, Profile.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
    }
}