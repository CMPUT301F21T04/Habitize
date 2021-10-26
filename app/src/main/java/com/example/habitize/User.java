package com.example.habitize;

import com.google.gson.annotations.JsonAdapter;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
public class User implements Serializable {
    private String userName;
    private String password;
    private String firstName;
    private String lastName;
    private List<User> following;
    private List<User> followers;
    private int progress;
    private List<Habit> userHabits;
    private int points;


    public User(String UserName,String Password,String FirstName,
         String LastName,ArrayList<User> following,ArrayList<User> followers, int progress,
         ArrayList<Habit> userHabits,int points){
        this.userName = UserName;
        this.password = Password;
        this.firstName = FirstName;
        this.lastName = LastName;
        this.following = following; // followingList
        this.followers = followers; // followerList
        this.progress = progress; // we start with zero progress
        this.userHabits = userHabits; // new arraylist for user habits
        this.points = points; // start with zero progress
    }
    public User(){}; // for firebase




    //Getters and Setters

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<User> getFollowing() {
        return this.following;
    }

    public void setFollowing(ArrayList<User> following) {
        this.following = following;
    }

    public List<User> getFollowers() {
        return followers;
    }

    public void setFollowers(ArrayList<User> followers) {
        this.followers = followers;
    }

    public int getProgress() {
        return this.progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public List<Habit> getUserHabits() {
        return this.userHabits;
    }

    public void setUserHabits(ArrayList<Habit> userHabits){this.userHabits = userHabits;}

    public void addUserHabit(Habit newHabit) {
        this.userHabits.add(newHabit);
    }

    public int getPoints() {
        return this.points;
    }

    public void setPoints(int points) {
        this.points = points;
    }


}
