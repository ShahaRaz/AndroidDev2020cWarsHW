package com.example.warshw2020c;

import androidx.appcompat.app.AppCompatActivity;

import android.icu.text.DateFormat;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int NUMBER_OF_ATTACKS = 3;
    ProgressBar pBar_main_Player1ProgressBar, pBar_main_Player2ProgressBar;
    Button btn_main_atk1, btn_main_atk2, btn_main_atk3, btn_main_atk4, btn_main_atk5, btn_main_atk6;
    ArrayList<Button> btnsAttack = new ArrayList<Button>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayShowHomeEnabled(false);

        associateButtons();
        setProgressBars();
        disableOneSide(); // one side starts, the other waits

    }

    private void disableOneSide() {
        btn_main_atk1.setEnabled(false);
        btn_main_atk2.setEnabled(false);
        btn_main_atk3.setEnabled(false);
    }

    private void associateButtons() {
        //maybe use an array instead of seperated elements?
        btnsAttack.add((Button)findViewById(R.id.btn_main_atk1));
        btnsAttack.add((Button)findViewById(R.id.btn_main_atk2));
        btnsAttack.add((Button)findViewById(R.id.btn_main_atk3));
        btnsAttack.add((Button)findViewById(R.id.btn_main_atk4));
        btnsAttack.add((Button)findViewById(R.id.btn_main_atk5));
        btnsAttack.add((Button)findViewById(R.id.btn_main_atk6));
//        btn_main_atk1 = findViewById(R.id.btn_main_atk1);
//        btn_main_atk2 = findViewById(R.id.btn_main_atk2);
//        btn_main_atk3 = findViewById(R.id.btn_main_atk3);
//        btn_main_atk4 = findViewById(R.id.btn_main_atk4);
//        btn_main_atk5 = findViewById(R.id.btn_main_atk5);
//        btn_main_atk6 = findViewById(R.id.btn_main_atk6);

        btn_main_atk1.setOnClickListener(bottomClickeListener);
        btn_main_atk2.setOnClickListener(bottomClickeListener);
        btn_main_atk3.setOnClickListener(bottomClickeListener);
        btn_main_atk4.setOnClickListener(bottomClickeListener);
        btn_main_atk5.setOnClickListener(bottomClickeListener);
        btn_main_atk6.setOnClickListener(bottomClickeListener);


    }

    public void setProgressBars() {
        pBar_main_Player1ProgressBar = findViewById(R.id.prgrsBar_main_Player1ProgressBar);
        pBar_main_Player2ProgressBar =  findViewById(R.id.prgrsBar_main_Player2ProgressBar);
        pBar_main_Player1ProgressBar.setMax(200);
        pBar_main_Player2ProgressBar.setMax(200);
        pBar_main_Player1ProgressBar.setProgress(200);
        pBar_main_Player2ProgressBar.setProgress(200);
    }


    private View.OnClickListener bottomClickeListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            System.out.println("button clicked id: " + view.getId());
            Toast.makeText(MainActivity.this, view.getTag() + "was pressed", Toast.LENGTH_SHORT).show();
            bottomClicked(view);
        }
    };


    private void bottomClicked(View view) {

        int damagedHP = reduceDamageFromOpp(view);
        toggleGroup();
        checkWinner(damagedHP, view);


    }

    private void checkWinner(int damagedHP, View view) {
        if (damagedHP < 1) {
            int idOfBtn = Integer.parseInt(((Button) view).getTag().toString());
            if (idOfBtn / 3 == 0)
                announceWinner("Player1"); // change to variable String
            else
                announceWinner("Player2"); // change to variable String

            btn_main_atk1.setEnabled(false);
            btn_main_atk2.setEnabled(false);
            btn_main_atk3.setEnabled(false);
            btn_main_atk4.setEnabled(false);
            btn_main_atk5.setEnabled(false);
            btn_main_atk6.setEnabled(false);
        }

    }

    private void announceWinner(String playerName) {
        Toast.makeText(MainActivity.this, playerName + " is the winner! ", Toast.LENGTH_LONG).show();

    }

    private void toggleGroup() {
        if (btn_main_atk1.isEnabled()) { // TODO: 18/08/2020 use a Toggle Group
            btn_main_atk1.setEnabled(false);
            btn_main_atk2.setEnabled(false);
            btn_main_atk3.setEnabled(false);
            btn_main_atk4.setEnabled(true);
            btn_main_atk5.setEnabled(true);
            btn_main_atk6.setEnabled(true);
        } else {
            btn_main_atk1.setEnabled(true);
            btn_main_atk2.setEnabled(true);
            btn_main_atk3.setEnabled(true);
            btn_main_atk4.setEnabled(false);
            btn_main_atk5.setEnabled(false);
            btn_main_atk6.setEnabled(false);
        }

    }


    public int reduceDamageFromOpp(View view) {
        int idOfBtn = Integer.parseInt(((Button) view).getTag().toString());
        Log.i("id in button  ", idOfBtn + " ");
        int currentHP;
        if (idOfBtn / NUMBER_OF_ATTACKS == 0) { // player 1 is attacking
            currentHP = pBar_main_Player2ProgressBar.getProgress();
            if (idOfBtn % NUMBER_OF_ATTACKS == 0)
                pBar_main_Player2ProgressBar.setProgress(currentHP - 10);
            else if (idOfBtn % NUMBER_OF_ATTACKS == 1)
                pBar_main_Player2ProgressBar.setProgress(currentHP - 20);
            else //(idOfBtn%NUMBER_OF_ATTACKS==2)
                pBar_main_Player2ProgressBar.setProgress(currentHP - 50);

            return pBar_main_Player2ProgressBar.getProgress(); // updated after damage reduced

        } else { // player 2 is attacking
            currentHP = pBar_main_Player1ProgressBar.getProgress();
            if (idOfBtn % NUMBER_OF_ATTACKS == 0)
                pBar_main_Player1ProgressBar.setProgress(currentHP - 10);
            else if (idOfBtn % NUMBER_OF_ATTACKS == 1)
                pBar_main_Player1ProgressBar.setProgress(currentHP - 20);
            else // (idOfBtn%NUMBER_OF_ATTACKS==2)
                pBar_main_Player1ProgressBar.setProgress(currentHP - 50);

            return pBar_main_Player1ProgressBar.getProgress(); // updated after damage reduced
        }
    }
}





