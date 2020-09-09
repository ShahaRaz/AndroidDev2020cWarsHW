package com.example.warshw2020c;

import android.app.Activity;
import android.util.Log;

import com.example.warshw2020c.Utilities.MyDate;
import com.example.warshw2020c.Utilities.MyLocation;

import java.text.DecimalFormat;

public class TopScore {
   private String nameOfPlayer ;
   private MyDate dateRecorded;
   private double latitude;
   private double longitude;
   private long timeStamp;
   private int numOfMoves;
   public static DecimalFormat df = new DecimalFormat("#.##");

   public static final int NOT_REGISTERED_VALUE = 999999; // very high score, when comparing - every battle will beat it

    public TopScore() {
        long currentTimeStamp =  System.currentTimeMillis() / 1000L;
        nameOfPlayer = "unRegistered " +  NOT_REGISTERED_VALUE;
        this.dateRecorded = new MyDate(currentTimeStamp);
        latitude = 0.0;
        longitude = 0.0;
        timeStamp=0;
        numOfMoves=NOT_REGISTERED_VALUE;
    }

    public TopScore(double lat, double lon, long timeStamp, int numOfMoves, String nameOfPlayer) {
        long currentTimeStamp =  System.currentTimeMillis() / 1000L;
        this.latitude = lat;
        this.longitude = lon;
        this.timeStamp = timeStamp;
        this.numOfMoves = numOfMoves;
        this.nameOfPlayer = nameOfPlayer;
        this.dateRecorded = new MyDate(currentTimeStamp);
    }

    public void setLocationCoordinates(double lat,double lon){
        this.latitude =lat;
        this.longitude =lon;
    }


    public String getNameOfPlayer() {
        return nameOfPlayer;
    }

    public void setNameOfPlayer(String nameOfPlayer) {
        this.nameOfPlayer = nameOfPlayer;
    }

    public MyDate getDateRecorded() {
        return dateRecorded;
    }

    public void setDateRecorded(MyDate dateRecorded) {
        this.dateRecorded = dateRecorded;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public int getNumOfMoves() {
        return numOfMoves;
    }


    @Override
    public String toString() {
        return
                this.numOfMoves +
                "-" + this.nameOfPlayer ;
//               + "-" + dateRecorded.toString();
    }
}
