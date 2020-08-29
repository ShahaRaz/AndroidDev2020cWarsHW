package com.example.warshw2020c.Board;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.warshw2020c.Utilities.MySPV3;
import com.example.warshw2020c.R;
import com.example.warshw2020c.TopScore;
import com.google.gson.Gson;

import java.util.ArrayList;

public class Activity_TopNBoard extends AppCompatActivity {
    private ArrayList<TextView> txt_top10_nN = new ArrayList<TextView>();
    private Button btn_top10_backButton;

    public final static String TOP_10_PLAYERS = "ObjArrlistOfScores";
    public final static String LAST_GAME_PLAYED = "ObjScore";

    private TopNListData topNList;
    private TopScore lastScore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__top10_board);
        /* get data from Shared Pref */
        getTopNList();
        boolean isLastScoreAvailable = getLastScore();
        if(isLastScoreAvailable)
            findPlaceOfLastScore(); // only if needed


        associateViews();


    }

    private void findPlaceOfLastScore() {
        boolean isLastScoreTop = topNList.findPlaceOfScore(lastScore);
    }

    private boolean getLastScore() {
        String lastScoreAsJson = MySPV3.getInstance().getString(MySPV3.KEYS.LAST_GAME,"NA");
        if(lastScoreAsJson.contains("NA")){
            return false;
        }
        else{
            Gson gson = new Gson();
            lastScore = gson.fromJson(lastScoreAsJson,TopScore.class);
            return true;
        }
    }

    private void getTopNList() {
        String topListAsJson = MySPV3.getInstance().getString(MySPV3.KEYS.TOP_N_LIST_OBJ,"NA");
        if(topListAsJson.equalsIgnoreCase("NA")){
             topNList = new TopNListData( createNewEmptyList());
        }
        else{
            Gson gson = new Gson();
            topNList = gson.fromJson(topListAsJson, TopNListData.class);
        }

    }


    private void associateViews() {
        btn_top10_backButton = findViewById(R.id.btn_top10_backButton);
        btn_top10_backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        txt_top10_nN.add((TextView)findViewById(R.id.txt_top10_n1));
        txt_top10_nN.add((TextView)findViewById(R.id.txt_top10_n2));
        txt_top10_nN.add((TextView)findViewById(R.id.txt_top10_n3));
        txt_top10_nN.add((TextView)findViewById(R.id.txt_top10_n4));
        txt_top10_nN.add((TextView)findViewById(R.id.txt_top10_n5));
        txt_top10_nN.add((TextView)findViewById(R.id.txt_top10_n6));
        txt_top10_nN.add((TextView)findViewById(R.id.txt_top10_n7));
        txt_top10_nN.add((TextView)findViewById(R.id.txt_top10_n8));
        txt_top10_nN.add((TextView)findViewById(R.id.txt_top10_n9));
        txt_top10_nN.add((TextView)findViewById(R.id.txt_top10_n10));

        for(int i=0;i<10;i++){
            txt_top10_nN.get(i).setText((i+1) + topNList.getArr().get(i).toString());
        }
    }



    private ArrayList<TopScore> createNewEmptyList() {
        ArrayList<TopScore> tempArr = new ArrayList<TopScore>();
        for(int i=0;i<10;i++){
            tempArr.add(createNewScore(i));
        }
        return tempArr;
    }

    private TopScore createNewScore(int PlayerIndexInName){
        return new TopScore(30.0,30.2,TopScore.NOT_REGISTERED_VALUE,TopScore.NOT_REGISTERED_VALUE,"player" + PlayerIndexInName);
    }

}