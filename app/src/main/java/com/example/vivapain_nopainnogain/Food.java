package com.example.vivapain_nopainnogain;

public class Food {
    private int FID;
    private long Calories;
    private String FoodName;
    private String FoodType;

    public Food() {
        FID = -1;
    }

    public Food(long calories, String foodName, String foodType) {
        FID = -1;
        Calories = calories;
        FoodName = foodName;
        FoodType = foodType;
    }

    public int getFID() {
        return FID;
    }

    public void setFID(int FID) {
        this.FID = FID;
    }

    public long getCalories() {
        return Calories;
    }

    public void setCalories(long calories) {
        Calories = calories;
    }

    public String getFoodName() {
        return FoodName;
    }

    public void setFoodName(String foodName) {
        FoodName = foodName;
    }

    public String getFoodType() {
        return FoodType;
    }

    public void setFoodType(String foodType) {
        FoodType = foodType;
    }
}
