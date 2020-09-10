package com.example.warshw2020c;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.warshw2020c.Fragments.Activity_TopNBoard;
import com.example.warshw2020c.Utilities.MySPV3;
import com.example.warshw2020c.Utilities.MySignalV2;

public class Activity_Menu extends AppCompatActivity {
    Button btn_menu_PlayGame;
    Button btn_menu_ScoreBoard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__menu);
        associateViews();

    }

    private void associateViews() {
        btn_menu_ScoreBoard = findViewById(R.id.btn_menu_ScoreBoard);
        btn_menu_ScoreBoard.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
           Intent intentTop10_FromMenu = new Intent(Activity_Menu.this, Activity_TopNBoard.class);
           startActivity(intentTop10_FromMenu);
           }
        });

        btn_menu_PlayGame = findViewById(R.id.btn_menu_PlayGame);
        btn_menu_PlayGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MySignalV2.getInstance().vibrate(300);
                Intent intentBattle = new Intent(Activity_Menu.this,Activity_Battle.class);
                startActivity(intentBattle);
            }
        });
    }
}