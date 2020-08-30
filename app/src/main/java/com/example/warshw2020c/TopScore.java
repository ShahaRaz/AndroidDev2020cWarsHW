package com.example.warshw2020c;

import com.example.warshw2020c.Utilities.MyDate;

import java.text.DecimalFormat;

public class TopScore {
   private String nameOfPlayer ;
   private MyDate dateRecorded;
   private double lat ;
   private double lon ;
   private long timeStamp;
   private int numOfMoves;
   public static DecimalFormat df = new DecimalFormat("#.##");

   public static final int NOT_REGISTERED_VALUE = 999999; // very high score, when comparing - every battle will beat it

    public TopScore() {
        long currentTimeStamp =  System.currentTimeMillis() / 1000L;
        nameOfPlayer = "unRegistered " +  NOT_REGISTERED_VALUE;
        this.dateRecorded = new MyDate(currentTimeStamp);
        lat = 0.0;
        lon = 0.0;
        timeStamp=0;
        numOfMoves=NOT_REGISTERED_VALUE;
    }

    public TopScore(double lat, double lon, long timeStamp, int numOfMoves, String nameOfPlayer) {
        long currentTimeStamp =  System.currentTimeMillis() / 1000L;
        this.nameOfPlayer = nameOfPlayer;
        this.lat = lat;
        this.lon = lon;
        this.timeStamp = timeStamp;
        this.numOfMoves = numOfMoves;
        this.dateRecorded = new MyDate(currentTimeStamp);

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

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
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
