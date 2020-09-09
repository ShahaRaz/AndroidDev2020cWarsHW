package com.example.warshw2020c.Board;

import android.util.Log;

import com.example.warshw2020c.TopScore;

import java.util.LinkedList;
import java.util.ListIterator;

public class TopNListData { // so we can save to SP w/o the need of saving arraylist

    private LinkedList<TopScore> scoreTopNLinkedList;
    private long unixTimeCreated;
    public static final int NUMBER_OF_TOP_SCORES_RECORDED = 10;



    public TopNListData() { }


    public TopNListData(LinkedList<TopScore> scoresTop10LinkedList) {
        this.scoreTopNLinkedList = scoresTop10LinkedList;
        unixTimeCreated = System.currentTimeMillis() / 1000L;
    }

    public LinkedList<TopScore> getArr() {
        return scoreTopNLinkedList;
    }




    public void setScoresTop10Linkedlist(LinkedList<TopScore> scoresTop10List) {
        this.scoreTopNLinkedList = scoresTop10List;
    }

    public long getUnixTimeCreated() {
        return unixTimeCreated;
    }


    public boolean findPlaceOfScore(TopScore lastScore) { // return true if new score made it into topN
        if((lastScore.getNumOfMoves()) > (scoreTopNLinkedList.get(NUMBER_OF_TOP_SCORES_RECORDED-1).getNumOfMoves()))
            return false; // not making into the board
        else{
            InsertNewScore(lastScore);
            return true;
        }
    }




    private void InsertNewScore(TopScore lastScore) {
        int pos = 0;
        ListIterator itr = scoreTopNLinkedList.listIterator();
        while(itr.hasNext()){
            if(lastScore.getNumOfMoves()<((TopScore)itr.next()).getNumOfMoves())
                break;
            pos++;
        }
        Log.d("________afterBinarySearch" , "position is  " + pos );
        scoreTopNLinkedList.add((pos), lastScore);
        scoreTopNLinkedList.removeLast(); // keep it in order
    }

/*USING ARRAYLIST WITH BINARY SEARCH : [USE IF DECIDE TO GO BACK TO LINKED LIST IMPELEMTATION ] */
  /*
    private void InsertNewScore(TopScore lastScore) { // In linked list implementation
        int pos = Collections.binarySearch(scoreTopNLinkedList,lastScore, compareByAtkCount);

        Log.d("________afterBinarySearch" , "position is  " + pos );
        if(pos<0) {
            scoreTopNLinkedList.add((-(pos)-1), lastScore);
        }
    }

    // Inner Comparators for Sorts //
    public static Comparator<TopScore> compareByAtkCount = new Comparator<TopScore>() {
        @Override
        public int compare(TopScore o1, TopScore o2) {
            Log.d("__number Of Moves__:" ,"[ "+ o1.getNameOfPlayer() + " : " + o1.getNumOfMoves() +" ],[ " + o2.getNameOfPlayer()+ ":  " + o2.getNumOfMoves()+" ]");
            if (o1.getNumOfMoves()>o2.getNumOfMoves()) {
                return 1;
            }
            else if (o1.getNumOfMoves()==o2.getNumOfMoves())
                return 0;
            else return -1;
        }
    };
*/


}
