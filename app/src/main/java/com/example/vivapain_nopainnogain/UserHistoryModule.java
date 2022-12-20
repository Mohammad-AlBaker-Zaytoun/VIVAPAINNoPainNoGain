package com.example.vivapain_nopainnogain;

public class UserHistoryModule {

    private final String name;

    private final String email;

    private final String password;

    private final String Created_At;

    private final String date;

    private final int age;

    private final int weight;

    private final int lostWeight;

    private final int gainedWeight;

    private final int trainedHrs;

    public UserHistoryModule(String name, String email, String password, String created_at, String date, int age, int weight, int lostWeight, int gainedWeight, int trainedHrs) {
        this.name = name;
        this.email = email;
        this.password = password;
        Created_At = created_at;
        this.date = date;
        this.age = age;
        this.weight = weight;
        this.lostWeight = lostWeight;
        this.gainedWeight = gainedWeight;
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

    public int getGainedWeight() {
        return gainedWeight;
    }

    public String getCreated_At() {
        return Created_At;
    }

    public String getDate() {
        return date;
    }
}
