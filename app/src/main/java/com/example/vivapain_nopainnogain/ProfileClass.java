package com.example.vivapain_nopainnogain;

import android.graphics.Bitmap;

public class ProfileClass {
    private int PID;
    private String pName;
    private int pAge;
    private int weightLost;
    private int trainedHours;
    private Bitmap picture;

    public ProfileClass() {
        PID = -1;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public int getpAge() {
        return pAge;
    }

    public void setpAge(int pAge) {
        this.pAge = pAge;
    }

    public int getWeightLost() {
        return weightLost;
    }

    public void setWeightLost(int weightLost) {
        this.weightLost = weightLost;
    }

    public int getTrainedHours() {
        return trainedHours;
    }

    public void setTrainedHours(int trainedHours) {
        this.trainedHours = trainedHours;
    }

    public int getPID() {
        return PID;
    }

    public void setPID(int PID) {
        this.PID = PID;
    }

    public Bitmap getPicture() {
        return picture;
    }

    public void setPicture(Bitmap p) {
        picture = p;
    }
}
