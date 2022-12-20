package com.example.vivapain_nopainnogain;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

public class VivaDataSource {
    private SQLiteDatabase database;
    private final VivaDBHelper dbHelper;

    public VivaDataSource(Context context) {
        dbHelper = new VivaDBHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
        Log.w(VivaDataSource.class.getName(),
                "Database opened!");
    }

    public void close() {
        dbHelper.close();
    }

    public ArrayList<ActivityClass> getActivities() {
        ArrayList<ActivityClass> Activity = new ArrayList<>();
        try {
            String query = "Select * from Activity";
            Cursor cursor = database.rawQuery(query, null);
            ActivityClass newActivity;
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                newActivity = new ActivityClass();
                newActivity.setActivityId(cursor.getInt(0));
                newActivity.setActivityName(cursor.getString(1));
                Activity.add(newActivity);
                cursor.moveToNext();
            }
            cursor.close();
        } catch (Exception e) {
            Activity = new ArrayList<>();
        }
        return Activity;
    }


}
