package com.example.habitize;


import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
public class User implements Serializable {
    private String userName;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private List<String> following;
    private List<String> followers;
    private long progress;
    private List<Habit> userHabits;
    private long points;


    public User(String UserName,String Password,String FirstName,
         String LastName,ArrayList<String> following,ArrayList<String> followers, long progress,
         ArrayList<Habit> userHabits,long points,String email){
        this.userName = UserName;
        this.password = Password;
        this.firstName = FirstName;
        this.lastName = LastName;
        this.following = following; // followingList
        this.followers = followers; // followerList
        this.progress = progress; // we start with zero progress
        this.userHabits = userHabits; // new arraylist for user habits
        this.points = points; // start with zero progress
        this.email = email;
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

    public List<String> getFollowing() {
        return this.following;
    }

    public void setFollowing(ArrayList<String> following) {
        this.following = following;
    }

    public List<String> getFollowers() {
        return followers;
    }

    public void setFollowers(ArrayList<String> followers) {
        this.followers = followers;
    }

    public long getProgress() {
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

    public long getPoints() {
        return this.points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getEmail(){return this.email; }

    public void setEmail(String email){
        this.email = email;
    }


}
