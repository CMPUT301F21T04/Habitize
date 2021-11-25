package com.example.habitize.Structural;

import java.io.Serializable;

public class Record implements Serializable {
    private String date;
    private String description;
    private byte[] byteArr; // the image is stored here
    private String recordIdentifier;
    private Double lat;
    private Double lon;
    // also, image here
    // also, location of the recorded habit here


    public Record(String date,String description, byte[] byteArr,String recordIdentifier,Double lat,Double lon){
        this.date = date;
        this.description = description;
        this.byteArr = byteArr;
        this.recordIdentifier = recordIdentifier;
        this.lat = lat;
        this.lon = lon;

    }
    public Double getLat(){return this.lat;};
    public Double getLon(){return this.lon;};
    public String getDate() {
        return date;
    }
    public String getDescription(){
        return description;
    }
    public String getRecordIdentifier(){return this.recordIdentifier;}
    public void setRecordIdentifier(String identifier){
        this.recordIdentifier = identifier;
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
