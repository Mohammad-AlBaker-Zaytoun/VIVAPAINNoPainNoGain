package com.example.vivapain_nopainnogain;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class VivaDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "VIVA.db";
    private static final int DATABASE_VERSION = 1;

    private static final String Create_TABLE_ACTIVITY = "Create table Activity (" +
            "_id integer primary key autoincrement," +
            "ActivityName text not null);";

    private static final String Seed_TABLE_ACTIVITY1 = "INSERT INTO Activity (_id , ActivityName)" +
            "VALUES ('0','Chest');";

    private static final String Seed_TABLE_ACTIVITY2 = "INSERT INTO Activity (_id , ActivityName)" +
            "VALUES ('1','Legs');";

    private static final String Seed_TABLE_ACTIVITY3 = "INSERT INTO Activity (_id , ActivityName)" +
            "VALUES ('2','Abs');";

    private static final String Seed_TABLE_ACTIVITY4 = "INSERT INTO Activity (_id , ActivityName)" +
            "VALUES ('3','Bicep');";

    private static final String Seed_TABLE_ACTIVITY5 = "INSERT INTO Activity (_id , ActivityName)" +
            "VALUES ('4','Triceps');";

    public VivaDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Create_TABLE_ACTIVITY);
        db.execSQL(Seed_TABLE_ACTIVITY1);
        db.execSQL(Seed_TABLE_ACTIVITY2);
        db.execSQL(Seed_TABLE_ACTIVITY3);
        db.execSQL(Seed_TABLE_ACTIVITY4);
        db.execSQL(Seed_TABLE_ACTIVITY5);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Activity");
        onCreate(db);
    }
}
