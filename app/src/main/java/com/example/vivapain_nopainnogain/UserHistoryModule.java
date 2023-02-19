package com.example.vivapain_nopainnogain;

public class UserHistoryModule {

    private final String name;

    private final String email;

    private final String password;

    private final String date;

    private final int age;

    private final int weight;

    private final int lostWeight;

    private final int trainedHrs;

    public UserHistoryModule(String name, String email, String password, String date, int age, int weight, int lostWeight, int trainedHrs) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.date = date;
        this.age = age;
        this.weight = weight;
        this.lostWeight = lostWeight;
        this.trainedHrs = trainedHrs;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public int getAge() {
        return age;
    }

    public int getWeight() {
        return weight;
    }

    public int getLostWeight() {
        return lostWeight;
    }

    public int getTrainedHrs() {
        return trainedHrs;
    }

    public String getPassword() {
        return password;
    }

    public String getDate() {
        return date;
    }
}
