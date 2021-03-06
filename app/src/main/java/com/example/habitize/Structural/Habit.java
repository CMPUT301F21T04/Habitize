package com.example.habitize.Structural;

import java.io.Serializable;
import java.util.ArrayList;

public class Habit implements Serializable {
    private String name;
    private String description;
    private String comments;
    private String startDate;
    private boolean mondayR;
    private boolean tuesdayR;
    private boolean wednesdayR;
    private boolean thursdayR;
    private boolean fridayR;
    private boolean saturdayR;
    private boolean sundayR;
    private boolean visible;
    private ArrayList<Record> recordList;
    private ArrayList<String> recurrenceArray;
    private Integer completion; //If the habit was completed today = 1, if not = 0
    private Long streak;
    private int totalComplete; // total number of times the habit was checked
    private int totalMissed; //total number of times habit was not checked
    private String identifier;

    public Habit(){};

    public Habit(String name, String description, String startDate, boolean mondayR, boolean tuesdayR,
                 boolean wednesdayR, boolean thursdayR, boolean fridayR, boolean saturdayR, boolean sundayR,ArrayList<Record> passedList
    ,String identifier,boolean visible){
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
        this.recordList = passedList;
        this.identifier = identifier;
        this.streak = 0L;
        this.visible = visible;
    }
    public Habit(String name, String description, String startDate, boolean mondayR, boolean tuesdayR,
                 boolean wednesdayR, boolean thursdayR, boolean fridayR, boolean saturdayR, boolean sundayR,ArrayList<Record> passedList
            ,String identifier,Long streak,boolean visible){
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
        this.recordList = passedList;
        this.identifier = identifier;
        this.streak = streak;
        this.visible = visible;
    }

    public void setVisible(){this.visible = true;}
    public void setInvisible(){this.visible = false;}
    public boolean getVisibility(){return this.visible;}
    public void incrementStreak(){
        this.streak++;
    }
    public void resetStreak(){
        this.streak = 0L;
    }

    public Long getStreak(){
        return this.streak;
    }

    public String getRecordAddress(){
        return identifier; // we create an address for the UUID
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

    public boolean getMondayR() {
        return mondayR;
    }

    public void setMondayR(boolean mondayR) {
        this.mondayR = mondayR;
    }

    public boolean getTuesdayR() {
        return tuesdayR;
    }

    public void setTuesdayR(boolean tuesdayR) {
        this.tuesdayR = tuesdayR;
    }

    public boolean getWednesdayR() {
        return wednesdayR;
    }

    public void setWednesdayR(boolean wednesdayR) {
        this.wednesdayR = wednesdayR;
    }

    public boolean getThursdayR() {
        return thursdayR;
    }

    public void setThursdayR(boolean thursdayR) {
        this.thursdayR = thursdayR;
    }

    public boolean getFridayR() {
        return fridayR;
    }

    public void setFridayR(boolean fridayR) {
        this.fridayR = fridayR;
    }

    public boolean getSaturdayR() {
        return saturdayR;
    }

    public void setSaturdayR(boolean saturdayR) {
        this.saturdayR = saturdayR;
    }

    public boolean getSundayR() {
        return sundayR;
    }

    public void setSundayR(boolean sundayR) {
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

    public ArrayList<String> computeRecurrence() {
        recurrenceArray = new ArrayList<String>();
        if (mondayR) {
            recurrenceArray.add("Monday");
        }
        if (tuesdayR) {
            recurrenceArray.add("Tuesday");
        }
        if (wednesdayR) {
            recurrenceArray.add("Wednesday");
        }
        if (thursdayR) {
            recurrenceArray.add("Thursday");
        }
        if (fridayR) {
            recurrenceArray.add("Friday");
        }
        if (saturdayR) {
            recurrenceArray.add("Saturday");
        }
        if (sundayR) {
            recurrenceArray.add("Sunday");
        }
        return recurrenceArray;
    }
}

