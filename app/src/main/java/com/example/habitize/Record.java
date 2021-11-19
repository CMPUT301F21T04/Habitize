package com.example.habitize;

public class Record {
    private String date;
    private String description;
    private byte[] byteArr; // the image is stored here
    // also, image here
    // also, location of the recorded habit here


    public Record(String date,String description, byte[] byteArr){
        this.date = date;
        this.description = description;
        this.byteArr = byteArr;
    }

    public String getDate() {
        return date;
    }
    public String getDescription(){
        return description;
    }
    public byte[] getByteArr(){return byteArr;}

    public void setDate(String date){
        this.date = date;
    }
    public void setDescription(String description){
        this.description = description;
    }
    public void setByteArr(byte[] byteArr){
        this.byteArr = byteArr;
    }


    // also getters and setters for the images
}
