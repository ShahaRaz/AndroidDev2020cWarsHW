package com.example.warshw2020c.Fragments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;

import com.example.warshw2020c.R;
import com.example.warshw2020c.TopScore;
import com.google.android.material.button.MaterialButton;

public class Activity_TopNBoard extends AppCompatActivity {
    private MaterialButton btn_topN_backButton;
    private Fragment_Map fragment_map;
    private Fragment_List fragment_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_n_board);
        associateViews();
        initFragments();

    }
    private void associateViews() {
        btn_topN_backButton = findViewById(R.id.btn_topN_backButton);
        btn_topN_backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

        private void initFragments() {

        fragment_list = Fragment_List.newInstance();
        fragment_list.setTopBoardActivityCallBack(callbackList);
        FragmentTransaction transaction1 = getSupportFragmentManager().beginTransaction();
        transaction1.replace(R.id.board_LAY_list,fragment_list);
        transaction1.commit();


        fragment_map = Fragment_Map.newInstance();
        fragment_map.setTopBoardActivityCallBack(callbackMap);
        FragmentTransaction transaction2 = getSupportFragmentManager().beginTransaction();
        transaction2.replace(R.id.board_LAY_map,fragment_map);
        transaction2.commit();

    }
    // implement the interfaces directly
    CallBack_Map callbackMap = new CallBack_Map() {
        @Override
        public void mapMarkedNewLocation(double lat, double lon) {
            //implement me
        }
    };

    CallBack_List callbackList = new CallBack_List() {
        @Override
        public void listChoosePlayerNumberN(TopScore scoreSelected) {
           showPlayerNInMap(scoreSelected);
        }
    };


    private void showPlayerNInMap(TopScore scoreSelected) {
        double lat = scoreSelected.getLatitude();
        double lon = scoreSelected.getLongitude();
        String lblOverMarker = scoreSelected.getNameOfPlayer() + " @ " +scoreSelected.getDateRecorded();
        fragment_map.markNewPoint(lat,lon,lblOverMarker);

    }

}