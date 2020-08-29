package com.example.warshw2020c;


/*for now. this class is not in use.. KEEPING IT FOR FUTURE IMPLEMENTATION */
import java.util.ArrayList;

public class SuperHero {
    private String name;
    private boolean isGifted;
    private int healPointsHP;
    private String atk1;
    private String atk2;
    private String atk3;
    private ArrayList<TopScore> scoresTop10Arrlist;


    public SuperHero() { }

    public SuperHero(String name, boolean isGifted, int healPointsHP, String atk1, String atk2, String atk3) {
        this.name = name;
        this.isGifted = isGifted;
        this.healPointsHP = healPointsHP;
        this.atk1 = atk1;
        this.atk2 = atk2;
        this.atk3 = atk3;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isGifted() {
        return isGifted;
    }

    public void setGifted(boolean gifted) {
        isGifted = gifted;
    }

    public int getHealPointsHP() {
        return healPointsHP;
    }

    public void setHealPointsHP(int healPointsHP) {
        this.healPointsHP = healPointsHP;
    }

    public String getAtk1() {
        return atk1;
    }

    public void setAtk1(String atk1) {
        this.atk1 = atk1;
    }

    public String getAtk2() {
        return atk2;
    }

    public void setAtk2(String atk2) {
        this.atk2 = atk2;
    }

    public String getAtk3() {
        return atk3;
    }

    public void setAtk3(String atk3) {
        this.atk3 = atk3;
    }

    public ArrayList<TopScore> getScoresTop10Arrlist() {
        return scoresTop10Arrlist;
    }

    public void setScoresTop10Arrlist(ArrayList<TopScore> scoresTop10Arrlist) {
        this.scoresTop10Arrlist = scoresTop10Arrlist;
    }
}
