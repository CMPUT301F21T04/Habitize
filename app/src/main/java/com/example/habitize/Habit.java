package com.example.habitize;

import java.io.Serializable;
import java.util.Date;

public class Habit implements Serializable {
    private String name;
    private String description;
    private String comments;
    private String startDate;
    private String mondayR;
    private String tuesdayR;
    private String wednesdayR;
    private String thursdayR;
    private String fridayR;
    private String saturdayR;
    private String sundayR;

//    private photos?
//    private location
    private Integer completion; //If the habit was completed today = 1, if not = 0
    private int totalComplete; // total number of times the habit was checked
    private int totalMissed; //total number of times habit was not checked

    // TODO: Add image + public vs private function

    public Habit(){};

    public Habit(String name, String description, String startDate, String mondayR, String tuesdayR,
                 String wednesdayR, String thursdayR, String fridayR, String saturdayR, String sundayR){
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.mondayR = mondayR;
        this.tuesdayR = tuesdayR;
        this.wednesdayR = wednesdayR;
        this.thursdayR = thursdayR;
        this.fridayR = fridayR;
        this.saturdayR = saturdayR;
        this.sundayR = sundayR;
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

    public String getStartDate(){
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getMondayR() {
        return mondayR;
    }

    public void setMondayR(String mondayR) {
        this.mondayR = mondayR;
    }

    public String getTuesdayR() {
        return tuesdayR;
    }

    public void setTuesdayR(String tuesdayR) {
        this.tuesdayR = tuesdayR;
    }

    public String getWednesdayR() {
        return wednesdayR;
    }

    public void setWednesdayR(String wednesdayR) {
        this.wednesdayR = wednesdayR;
    }

    public String getThursdayR() {
        return thursdayR;
    }

    public void setThursdayR(String thursdayR) {
        this.thursdayR = thursdayR;
    }

    public String getFridayR() {
        return fridayR;
    }

    public void setFridayR(String fridayR) {
        this.fridayR = fridayR;
    }

    public String getSaturdayR() {
        return saturdayR;
    }

    public void setSaturdayR(String saturdayR) {
        this.saturdayR = saturdayR;
    }

    public String getSundayR() {
        return sundayR;
    }

    public void setSundayR(String sundayR) {
        this.sundayR = sundayR;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
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

