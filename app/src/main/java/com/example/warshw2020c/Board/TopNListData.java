package com.example.warshw2020c.Board;

import android.util.Log;

import com.example.warshw2020c.TopScore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class TopNListData { // so we can save to SP w/o the need of saving arraylist

    private ArrayList<TopScore> scoresTop10Arrlist;
    private long unixTimeCreated;
    public static final int NUMBER_OF_TOP_SCORES_RECORDED = 10;



    public TopNListData() { }

    public TopNListData(ArrayList<TopScore> scoresTop10Arrlist) {
        this.scoresTop10Arrlist = scoresTop10Arrlist;
        unixTimeCreated = System.currentTimeMillis() / 1000L;
    }

    public ArrayList<TopScore> getArr() {
        return scoresTop10Arrlist;
    }

    public void setScoresTop10Arrlist(ArrayList<TopScore> scoresTop10Arrlist) {
        this.scoresTop10Arrlist = scoresTop10Arrlist;
    }

    public long getUnixTimeCreated() {
        return unixTimeCreated;
    }


    public boolean findPlaceOfScore(TopScore lastScore) { // return true if new score made it into topN
        if((lastScore.getNumOfMoves()) > (scoresTop10Arrlist.get(NUMBER_OF_TOP_SCORES_RECORDED-1).getNumOfMoves()))
            return false; // not making into the board
        else{
            InsertNewScore(lastScore);
            return true;
        }
    }

    private void InsertNewScore(TopScore lastScore) {
        int pos = Collections.binarySearch(scoresTop10Arrlist,lastScore, compareByAtkCount);
        Log.d("afterBinarySearch" , "position is  " + pos );
        scoresTop10Arrlist.add(-(pos) - 1,lastScore);
        /*in order to make this process (move all elements) more efficient O(1) i would switch the data-structure to linked list*/

        }

    // Inner Comparators for Sorts //
    public static Comparator<TopScore> compareByAtkCount = new Comparator<TopScore>() {
        @Override
        public int compare(TopScore o1, TopScore o2) {
            if (o1.getNumOfMoves()>o2.getNumOfMoves())
                return 1;
            else if (o1.getNumOfMoves()==o2.getNumOfMoves())
                return 0;
            else return -1;
        }
    };



}
