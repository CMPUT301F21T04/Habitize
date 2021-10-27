package com.example.habitize;

import java.io.Serializable;

public class Habit implements Serializable {
    private String name;
    private String description;

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



}
