package com.example.vivapain_nopainnogain;

public class gymDetailsModule {
    private String name;
    private final String location;
    private final int rating;
    private final int number;
    private final String genderAllowed;
    private final String workingTimes;
    private final int dailyEntryPrice;
    private final int monthPlanPrice;

    public gymDetailsModule(String location, int rating, int number, String genderAllowed, String workingTimes, int dailyEntryPrice, int monthPlanPrice) {
        this.location = location;
        this.rating = rating;
        this.number = number;
        this.genderAllowed = genderAllowed;
        this.workingTimes = workingTimes;
        this.dailyEntryPrice = dailyEntryPrice;
        this.monthPlanPrice = monthPlanPrice;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public int getNumber() {
        return number;
    }

    public void setName(String name) {
        this.name = name;
    }


    public int getRating() {
        return rating;
    }

    public String getGenderAllowed() {
        return genderAllowed;
    }

    public int getDailyEntryPrice() {
        return dailyEntryPrice;
    }

    public int getMonthPlanPrice() {
        return monthPlanPrice;
    }

    public String getWorkingTimes() {
        return workingTimes;
    }

}
