package com.example.warshw2020c;

import com.example.warshw2020c.Utilities.MyDate;

import java.text.DecimalFormat;

public class TopScore {
   private String nameOfPlayer;
   private MyDate dateRecorded;
   private double lat = 0.0;
   private double lon = 0.0;
   private long timeStamp=0;
   private int numOfMoves=NOT_REGISTERED_VALUE;
   public static DecimalFormat df = new DecimalFormat("#.##");

   public static final int NOT_REGISTERED_VALUE = 999999; // very high score, when comparing - every battle will beat it

    public TopScore() {}

    public TopScore(double lat, double lon, long timeStamp, int numOfMoves, String nameOfPlayer) {
        this.nameOfPlayer = nameOfPlayer;
        this.lat = lat;
        this.lon = lon;
        this.timeStamp = timeStamp;
        this.numOfMoves = numOfMoves;
        this.dateRecorded = new MyDate(11,11,2011); // TODO: 25/08/2020 find a way to send the current date over here

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

    public void setNumOfMoves(int numOfMoves) {
        this.numOfMoves = numOfMoves;
    }





    @Override
    public String toString() {
        return
                getNumOfMoves() +
                "-" + getNameOfPlayer() ;
//               + "-" + dateRecorded.toString();
    }
}
