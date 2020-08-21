package com.example.warshw2020c;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final int NUMBER_OF_ATTACKS = 3;
    private static final int MAX_HP = 200;
    private static final int HP_RED_BAR_VALUE = 60;
    ProgressBar pBar_main_Player1ProgressBar, pBar_main_Player2ProgressBar;
    ArrayList<Button> btnsPlayer_1_Attack = new ArrayList<Button>();
    ArrayList<Button> btnsPlayer_2_Attack = new ArrayList<Button>();

    TextView lbl_main_announceWinnerName;
    ImageView img_main_winnerPic;
    RelativeLayout lay_main_winnerAnnouncement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
//        getSupportActionBar().setDisplayShowHomeEnabled(false);

        setProgressBars();
        associateButtons();
        chooseFirstAttacker();
        associateWinnerTag();

    }

    private void associateWinnerTag() {
        lbl_main_announceWinnerName = findViewById(R.id.lbl_main_announceWinnerName);
        img_main_winnerPic = findViewById(R.id.img_main_winnerPic);
        lay_main_winnerAnnouncement = findViewById(R.id.lay_main_winnerAnnouncement);

    }

    private void chooseFirstAttacker() {
        for(Button btn : btnsPlayer_2_Attack) btn.setEnabled(false);

    }


    private void associateButtons() {
        //maybe use an array instead of seperated elements?
        btnsPlayer_1_Attack.add((Button)findViewById(R.id.btn_main_atk1));
        btnsPlayer_1_Attack.add((Button)findViewById(R.id.btn_main_atk2));
        btnsPlayer_1_Attack.add((Button)findViewById(R.id.btn_main_atk3));
        for(Button btn : btnsPlayer_1_Attack) btn.setOnClickListener(bottomClickeListener);

        btnsPlayer_2_Attack.add((Button)findViewById(R.id.btn_main_atk4));
        btnsPlayer_2_Attack.add((Button)findViewById(R.id.btn_main_atk5));
        btnsPlayer_2_Attack.add((Button)findViewById(R.id.btn_main_atk6));
        for(Button btn : btnsPlayer_2_Attack) btn.setOnClickListener(bottomClickeListener);

    }


    private View.OnClickListener bottomClickeListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
//            System.out.println("button clicked id: " + view.getId());
            bottomClicked(view);
        }
    };

    public void setProgressBars() {
        pBar_main_Player1ProgressBar = findViewById(R.id.prgrsBar_main_Player1ProgressBar);
        pBar_main_Player2ProgressBar = findViewById(R.id.prgrsBar_main_Player2ProgressBar);
        pBar_main_Player1ProgressBar.setBackgroundColor(getColor(R.color.lifeBarGreen));
        pBar_main_Player2ProgressBar.setBackgroundColor(getColor(R.color.lifeBarGreen));
        pBar_main_Player1ProgressBar.setMax(MAX_HP);
        pBar_main_Player2ProgressBar.setMax(MAX_HP);
        pBar_main_Player1ProgressBar.setProgress(MAX_HP);
        pBar_main_Player2ProgressBar.setProgress(MAX_HP);
    }

    private void bottomClicked(View view) {

        int damagedHP = reduceDamageFromOpp(view);
        changeColor(damagedHP,view);
        toggleGroup();
        checkWinner(damagedHP, view);


    }

    private void changeColor(int damagedHP,View view) {
        if(damagedHP<HP_RED_BAR_VALUE){
            int idOfBtn = Integer.parseInt(((Button) view).getTag().toString());
            if (idOfBtn / NUMBER_OF_ATTACKS == 0) // player 1
                pBar_main_Player2ProgressBar.setBackgroundColor(getColor(R.color.lifeBarRed));
            else
                pBar_main_Player1ProgressBar.setBackgroundColor(getColor(R.color.lifeBarRed));


        }

    }

    private void checkWinner(int damagedHP, View view) {
        if (damagedHP < 1) {
            int idOfBtn = Integer.parseInt(((Button) view).getTag().toString());
            if (idOfBtn / NUMBER_OF_ATTACKS == 0)
                announceWinner(getString(R.string.Player1Name)); // change to variable String
            else
                announceWinner(getString(R.string.Player2Name)); // change to variable String

            for(Button btn : btnsPlayer_1_Attack) btn.setEnabled(false);
            for(Button btn : btnsPlayer_2_Attack) btn.setEnabled(false);

        }

    }

    private void announceWinner(String playerName) {
        //Toast.makeText(MainActivity.this, playerName + " is the winner! ", Toast.LENGTH_LONG).show();
        lay_main_winnerAnnouncement.setTranslationY(0);
        if(playerName==getString(R.string.Player2Name)){
            img_main_winnerPic.setImageDrawable(getDrawable(R.drawable.ic_superman));
            lbl_main_announceWinnerName.setText(getText(R.string.Player2Name) + " " + getText(R.string.winnerAnnouncement));
        }
        else{
            img_main_winnerPic.setImageDrawable(getDrawable(R.drawable.ic_batman));
            lbl_main_announceWinnerName.setText(getText(R.string.Player1Name) + " " + getText(R.string.winnerAnnouncement));

        }
        img_main_winnerPic.animate().rotation(720).alpha(100).setDuration(1000);
        lbl_main_announceWinnerName.animate().rotation(720).alpha(100).setDuration(1000);



    }

    private void toggleGroup() {
        if (btnsPlayer_1_Attack.get(0).isEnabled()) { //
            for(Button btn : btnsPlayer_1_Attack) btn.setEnabled(false);
            for(Button btn : btnsPlayer_2_Attack) btn.setEnabled(true);

        } else {
            for(Button btn : btnsPlayer_1_Attack) btn.setEnabled(true);
            for(Button btn : btnsPlayer_2_Attack) btn.setEnabled(false);

        }

    }


    public int reduceDamageFromOpp(View view) {
        int idOfBtn = Integer.parseInt(((Button) view).getTag().toString());
        Log.i("id in button  ", idOfBtn + " ");
        int currentHP;
        if (idOfBtn / NUMBER_OF_ATTACKS == 0) { // player 1 is attacking
            currentHP = pBar_main_Player2ProgressBar.getProgress();
            if (idOfBtn % NUMBER_OF_ATTACKS == 0)
                currentHP -= 10;
            else if (idOfBtn % NUMBER_OF_ATTACKS == 1)
                currentHP -= 20;
            else //(idOfBtn%NUMBER_OF_ATTACKS==2)
               currentHP -= 50;

            pBar_main_Player2ProgressBar.setProgress(currentHP);
            return currentHP; // updated after damage reduced

        } else { // player 2 is attacking
            currentHP = pBar_main_Player1ProgressBar.getProgress();
            if (idOfBtn % NUMBER_OF_ATTACKS == 0)
                currentHP -= 10;
            else if (idOfBtn % NUMBER_OF_ATTACKS == 1)
                currentHP -= 20;
            else // (idOfBtn%NUMBER_OF_ATTACKS==2)
                currentHP -= 50;

            pBar_main_Player1ProgressBar.setProgress(currentHP);
            return currentHP; // updated after damage reduced
        }
    }
}





