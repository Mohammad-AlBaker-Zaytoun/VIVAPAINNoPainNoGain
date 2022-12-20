package com.example.vivapain_nopainnogain;

public class LoginResult {  //For gym owners
    private final String name;

    private final String email;

    private final String password;

    private final String Created_At;

    private final int age;

    private final int weight;

    private final int lostWeight;

    private final int GainedWeight;

    private final int trainedHrs;

    public LoginResult(String name, String email, String password, String created_at, int age, int weight, int lostWeight, int gainedWeight, int trainedHrs) {
        this.name = name;
        this.email = email;
        this.password = password;
        Created_At = created_at;
        this.age = age;
        this.weight = weight;
        this.lostWeight = lostWeight;
        GainedWeight = gainedWeight;
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
        return GainedWeight;
    }

    public String getCreated_At() {
        return Created_At;
    }
}
