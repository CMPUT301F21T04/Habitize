package com.example.habitize;

import java.io.Serializable;
import java.util.Date;

public class Habit implements Serializable {
    private String name;
    private String description;
    private String comments;
    private String recurrence;
    private Date startDate;


    
//    private photos?
//    private location
    private Integer completion; //If the habit was completed today = 1, if not = 0
    private int totalComplete; // total number of times the habit was checked
    private int totalMissed; //total number of times habit was not checked

    // TODO: We need image + date functionality

    // TODO: Add image + date constructors
    public Habit(){};
    public Habit(String name, String description){
        this.name = name;
        this.description = description;
    }
    public String getName(){
        return this.name;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getDescription(){
        return this.description;
    }
    public void setDescription(String description){
        this.description = description;
    }





    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getRecurrence() {
        return recurrence;
    }

    public void setRecurrence(String recurrence) {
        this.recurrence = recurrence;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }


    public Integer getCompletion() {
        return completion;
    }

    public void setCompletion(Integer completion) {
        this.completion = completion;
    }

    public int getTotalComplete() {
        return totalComplete;
    }

    public void setTotalComplete(int totalComplete) {
        this.totalComplete = totalComplete;
    }

    public int getTotalMissed() {
        return totalMissed;
    }

    public void setTotalMissed(int totalMissed) {
        this.totalMissed = totalMissed;
    }




}
