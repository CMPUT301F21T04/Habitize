package com.example.habitize;

public class Record {
    private String date;
    private String description;
    // also, image here
    // also, location of the recorded habit here


    public Record(String date,String description){
        this.date = date;
        this.description = description;
    }

    public String getDate() {
        return date;
    }
    public String getDescription(){
        return description;
    }

    public void setDate(String date){
        this.date = date;
    }
    public void setDescription(String description){
        this.description = description;
    }


    // also getters and setters for the images
}
