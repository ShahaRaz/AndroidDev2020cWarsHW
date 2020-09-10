package com.example.warshw2020c.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.warshw2020c.Board.TopNListData;
import com.example.warshw2020c.R;
import com.example.warshw2020c.TopScore;
import com.example.warshw2020c.Utilities.MySPV3;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.LinkedList;


public class Fragment_List extends Fragment {

    protected View view;





    // callback for the 2TopNBoard2
    private CallBack_List callBack_TopNBoard;
    public void setTopBoardActivityCallBack(CallBack_List callBack_list){
        this.callBack_TopNBoard = callBack_list;
    }

    public Fragment_List() {
        // Required empty public constructor
    }

    public static Fragment_List newInstance() {
        Fragment_List fragment = new Fragment_List();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(view==null) {
            view = inflater.inflate(R.layout.fragment__list, container, false);
        }

        findViews(view);

        return view;
    }

    private void findViews(View view) {

        btn_topN_numN.add((MaterialButton)view.findViewById(R.id.btn_topN_n1));
        btn_topN_numN.add((MaterialButton)view.findViewById(R.id.btn_topN_n2));
        btn_topN_numN.add((MaterialButton)view.findViewById(R.id.btn_topN_n3));
        btn_topN_numN.add((MaterialButton)view.findViewById(R.id.btn_topN_n4));
        btn_topN_numN.add((MaterialButton)view.findViewById(R.id.btn_topN_n5));
        btn_topN_numN.add((MaterialButton)view.findViewById(R.id.btn_topN_n6));
        btn_topN_numN.add((MaterialButton)view.findViewById(R.id.btn_topN_n7));
        btn_topN_numN.add((MaterialButton)view.findViewById(R.id.btn_topN_n8));
        btn_topN_numN.add((MaterialButton)view.findViewById(R.id.btn_topN_n9));
        btn_topN_numN.add((MaterialButton)view.findViewById(R.id.btn_topN_n10));
        for(MaterialButton btn : btn_topN_numN) btn.setOnClickListener(buttonClickeListener);
        // set on action for these buttons

    }
    private View.OnClickListener buttonClickeListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int playerRank = Integer.parseInt(((MaterialButton) view).getTag().toString());
            Log.d("pttt","  " + playerRank);
            callBack_TopNBoard.listChoosePlayerNumberN(topNList.getArr().get(playerRank-1)); //index from 0
        }
    };


    // _______________________________________________________
    // _______________________________________________________
    // _______________________________________________________
    // ______________FROM Activity TopNBoard__________________
    // _______________________________________________________
    // _______________________________________________________


    private ArrayList<MaterialButton> btn_topN_numN = new ArrayList<MaterialButton>();
    private Button btn_top10_backButton;
    public final static String TOP_10_PLAYERS = "ObjArrlistOfScores";
    public final static String LAST_GAME_PLAYED = "ObjScore";

    private TopNListData topNList;
    private TopScore lastScore;


    private void addTextToButtons() {
        for(int i=0;i<TopNListData.NUMBER_OF_TOP_SCORES_RECORDED;i++){
            btn_topN_numN.get(i).setText((i+1) + ") " + topNList.getArr().get(i).toString());
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        getDataFromSP();
        addTextToButtons();
    }


    @Override
    public void onPause() {
        super.onPause();
        saveDataToSP();
    }

    private void saveDataToSP() {
        Gson json = new Gson();
        String boardAsJson =  json.toJson(this.topNList);
        MySPV3.getInstance().putString(MySPV3.KEYS.TOP_N_LIST_OBJ ,boardAsJson);
    }

    private void getDataFromSP() {
        /* get data from Shared Pref */
        getTopNList();
        boolean isLastScoreAvailable = getLastScore();
        if(isLastScoreAvailable) {
            topNList.findPlaceOfScore(lastScore);
            MySPV3.getInstance().putString(MySPV3.KEYS.LAST_GAME,"NA"); // prevent from reading again same score
        }

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
        else {
            Gson gson = new Gson();
            topNList = gson.fromJson(topListAsJson, TopNListData.class);
        }
    }



    private LinkedList<TopScore> createNewEmptyList() {
        LinkedList<TopScore> tempList = new LinkedList<>();
        for(int i=0;i<10;i++){
            tempList.add(createNewScore(i));
        }
        return tempList;
    }

    private TopScore createNewScore(int PlayerIndexInName){
        return new TopScore(0.0,0.0,TopScore.NOT_REGISTERED_VALUE,TopScore.NOT_REGISTERED_VALUE,"player" + PlayerIndexInName);
    }

}