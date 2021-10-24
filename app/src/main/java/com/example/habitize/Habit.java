package com.example.habitize;

public class Habit {
    private String name;
    private String description;
    private int streak;
    private Boolean isDone;
    // TODO: We need image + date functionality

    // TODO: Add image + date constructors
    public void Habit(String name, String description){
        this.name = name;
        this.description = description;
    }
    public void incrementStreak(){
        this.streak++;
    }
    public String getName(){
        return this.name;
    }
    public String getDescription(){
        return this.description;
    }

    // TODO: should this trigger automatically if a day goes by without incrementstreak?
    public void endStreak(){
        this.streak = 0;
    }
    public void resetTask(){
        this.isDone = false;
    }
    public void doTask(){
        this.isDone = true;
    }


}
