package com.example.vivapain_nopainnogain;

public class LoginResult {  //For gym owners
    private final String name;

    private final String email;

    private final String password;

    private final String lastUpdated;

    private final int age;

    private final int weight;

    private final int lostWeight;

    private final int gainedWeight;

    private final int trainedHrs;

    public LoginResult(String name, String email, String password, String lastUpdated, int age, int weight, int lostWeight, int gainedWeight, int trainedHrs) {
        this.name = name;
        this.email = email;
        this.password = password;
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

}
