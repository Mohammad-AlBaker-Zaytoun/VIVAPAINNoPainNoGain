package com.example.vivapain_nopainnogain;

public class LoginResult1 {         //For users

    private final String name;

    private final String email;

    private final String password;

    private final String Created_At;

    private final String lastUpdated;

    private final int age;

    private final int weight;

    private final int lostWeight;

    private final int gainedWeight;

    private final int trainedHrs;

    public LoginResult1(String name, String email, String password, String created_at, String lastUpdated, int age, int weight, int lostWeight, int gainedWeight, int trainedHrs) {
        this.name = name;
        this.email = email;
        this.password = password;
        Created_At = created_at;
        this.lastUpdated = lastUpdated;
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

    public String getLastUpdated() {
        return lastUpdated;
    }

    public String getCreated_At() {
        return Created_At;
    }
}
