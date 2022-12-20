package com.example.vivapain_nopainnogain;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RetrofitInterface {

    @POST("/login")
    Call<LoginResult> executeLogin(@Body HashMap<String, String> map); // For gym owner

    @POST("/signup")
    Call<Void> executeSignup(@Body HashMap<String, String> map); // For gym owner

    @POST("/update")
    Call<Void> executeUpdate(@Body HashMap<String, String> map); // For gym owner

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @POST("/login1")
    Call<LoginResult1> executeLogin1(@Body HashMap<String, String> map); // For users

    @POST("/signup1")
    Call<Void> executeSignup1(@Body HashMap<String, String> map); // For users

    @POST("/update1")
    Call<Void> executeUpdate1(@Body HashMap<String, String> map); // For users

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @POST("/breakfast")
    Call<breakfastResult> executeBreakfast(@Body HashMap<String, Integer> map); // Get breakfast data

    @POST("/snacks")
    Call<snacksResult> executeSnacks(@Body HashMap<String, Integer> map); // Get Snacks data

    @POST("/lunch")
    Call<lunchResult> executeLunch(@Body HashMap<String, Integer> map); // Get Lunch data

    @POST("/dinner")
    Call<dinnerResult> executeDinner(@Body HashMap<String, Integer> map); // Get Dinner data

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GET("/GymData")
    Call<ArrayList<gymDetailsModule>> executeGymDetails(); // Get gym details

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @POST("/requestG")
    Call<Void> executeRequestG(@Body HashMap<String, String> map); // Requested gym post

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @POST("/GetUserHistory")
    Call<ArrayList<UserHistoryModule>> executeUserHistory(@Body HashMap<String, String> map); // Request User last 4 records from MongoDB

    @POST("/GetGymOwnerHistory")
    Call<ArrayList<UserHistoryModule>> executeGymOwnerHistory(@Body HashMap<String, String> map); // Request GymOwner last 4 records from MongoDB

}
