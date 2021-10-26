package com.example.habitize;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class User {
    private String UserName;
    private String Password;
    private String FirstName;
    private String LastName;
    private ArrayList<User> Following;
    private ArrayList<User> Followers;
    private int Progress;
    private ArrayList<Habit> UserHabits;
    private int Points;


    User(String UserName,String Password,String FirstName,
         String LastName){
        this.UserName = UserName;
        this.Password = Password;
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.Following = new ArrayList<>(); // followingList
        this.Followers = new ArrayList<>(); // followerList
        this.Progress = 0; // we start with zero progress
        this.UserHabits = new ArrayList<>(); // new arraylist for user habits
        this.Points = 0; // start with zero progress
    }

    //Getters and Setters

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public ArrayList<User> getFollowing() {
        return Following;
    }

    public void setFollowing(ArrayList<User> following) {
        this.Following = following;
    }

    public ArrayList<User> getFollowers() {
        return Followers;
    }

    public void setFollowers(ArrayList<User> followers) {
        this.Followers = followers;
    }

    public int getProgress() {
        return this.Progress;
    }

    public void setProgress(int progress) {
        this.Progress = progress;
    }

    public ArrayList<Habit> getUserHabits() {
        return this.UserHabits;
    }

    public void addUserHabit(Habit newHabit) {
        this.UserHabits.add(newHabit);
    }

    public int getPoints() {
        return this.Points;
    }

    public void setPoints(int points) {
        this.Points = points;
    }

}
