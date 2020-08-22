// TODO: 21/08/2020 Repalace keyword _main_ with _Battle_ in all necessary places 
package com.example.warshw2020c;

import androidx.appcompat.app.AppCompatActivity;

import android.net.IpSecManager;
import android.os.Bundle;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class Activity_Battle extends AppCompatActivity {
    private static final int NUMBER_OF_ATTACKS = 3;
    private static final int MAX_HP = 200;
    private static final int HP_RED_BAR_VALUE = 60;
    private ProgressBar pBar_main_Player1ProgressBar, pBar_main_Player2ProgressBar;
    private ArrayList<Button> btnsPlayer_1_Attack = new ArrayList<Button>();
    private ArrayList<Button> btnsPlayer_2_Attack = new ArrayList<Button>();
    private ImageView btn_main_rollDice;
    private Random rng = new Random(); // random number generator
    private TextView lbl_main_announceWinnerName;
    private ImageView img_main_winnerPic;
    private LinearLayout lay_main_winnerAnnouncement;
    private LinearLayout lay_main_rollDice;
    private ImageView img_main_diceResultP1;
    private ImageView img_main_diceResultP2;
    private Button btn_main_startGame;
    private int player1Dice;
    private int player2Dice;
    private boolean isPlayer1PlayingNextTurn;
    private int atkNum; // 2 players , 3 atks for each => atkNum [0,5] (in range)
    private boolean isGameDone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_battle);
//      getSupportActionBar().setDisplayShowHomeEnabled(false); // uncomment if not using full screen
        InitGame();

    }

    private void InitGame() {
        associateButtons();
        isGameDone=false;
        isPlayer1PlayingNextTurn =true; // will be changed in lottery if needed
        StartDiceLottery();
        setProgressBars();
        setWinnerTags();
//        startGameWTimer(); // after lottery
    }

    private void startGameWTimer() {
        final Handler handler = new Handler();
        final int delay = 2000;
        final int count = 1; // TODO: 22/08/2020  delete me and stop the timer when announcing winner()
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(!isGameDone)
                    runNextTimedTurn();
                handler.postDelayed(this,delay);
            }
        },delay);



    }

    private void runNextTimedTurn() {
        int rndmAtkNum = (rng.nextInt(NUMBER_OF_ATTACKS)); // 0->2
        if(isPlayer1PlayingNextTurn)
            rndmAtkNum+=NUMBER_OF_ATTACKS; // player's 2 turn

        atkButtonClicked(rndmAtkNum);

    }

    private void StartDiceLottery() {
        btn_main_rollDice = findViewById(R.id.btn_main_rollDice);
        img_main_diceResultP1 = findViewById((R.id.img_main_diceResultP1));
        img_main_diceResultP2 = findViewById((R.id.img_main_diceResultP2));
        lay_main_rollDice = findViewById(R.id.lay_main_rollDice);

        btn_main_rollDice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rollDice();
            }
        });


        btn_main_startGame = findViewById(R.id.btn_main_startGame);
        //btn_main_startGame.setClickable(false);
        btn_main_startGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishedLottery();
            }
        });

    }

    private void finishedLottery() {
        if(player2Dice>player1Dice)
            isPlayer1PlayingNextTurn=false;
        toggleAtkGroup();// by default player1 starts
        lay_main_rollDice.setAlpha(0);
        pBar_main_Player1ProgressBar.setAlpha(1);
        pBar_main_Player2ProgressBar.setAlpha(1);
        startGameWTimer();

    }

    private void rollDice() {
        player1Dice = (rng.nextInt(6)+1); //
        showDiceToPlayer(player1Dice , img_main_diceResultP1);


        player2Dice = (rng.nextInt(6)+1); //
        showDiceToPlayer(player2Dice , img_main_diceResultP2);

        if(player1Dice==player2Dice)
            btn_main_startGame.setEnabled(false);
        else // not same result
            btn_main_startGame.setEnabled(true);


    }

    private void showDiceToPlayer(int diceResult, ImageView playerDice) {
        switch(diceResult){
            case 1:
                playerDice.setImageResource(R.drawable.ic_dice_1);
                break;
            case 2:
                playerDice.setImageResource(R.drawable.ic_dice_2);
                break;
            case 3:
                playerDice.setImageResource(R.drawable.ic_dice_3);
                break;
            case 4:
                playerDice.setImageResource(R.drawable.ic_dice_4);
                break;
            case 5:
                playerDice.setImageResource(R.drawable.ic_dice_5);
                break;
            case 6:
            playerDice.setImageResource(R.drawable.ic_dice_6);
            break;
        }

    }


    private void setWinnerTags() {
        lbl_main_announceWinnerName = findViewById(R.id.lbl_main_announceWinnerName);
        img_main_winnerPic = findViewById(R.id.img_main_winnerPic);
        lay_main_winnerAnnouncement = findViewById(R.id.lay_main_winnerAnnouncement);

    }

    private void setDefaultFirstAttacker() {
        //by default player 1 starts
        for(Button btn : btnsPlayer_2_Attack) btn.setEnabled(false);
        isPlayer1PlayingNextTurn =true;

    }


    private void associateButtons() {
        //Setting an arrayList (manual Toggle group)
        btnsPlayer_1_Attack.add((Button)findViewById(R.id.btn_main_atk1));
        btnsPlayer_1_Attack.add((Button)findViewById(R.id.btn_main_atk2));
        btnsPlayer_1_Attack.add((Button)findViewById(R.id.btn_main_atk3));
        for(Button btn : btnsPlayer_1_Attack) btn.setOnClickListener(bottomClickeListener);
        for(Button btn : btnsPlayer_1_Attack) btn.setEnabled(false);

        btnsPlayer_2_Attack.add((Button)findViewById(R.id.btn_main_atk4));
        btnsPlayer_2_Attack.add((Button)findViewById(R.id.btn_main_atk5));
        btnsPlayer_2_Attack.add((Button)findViewById(R.id.btn_main_atk6));
        for(Button btn : btnsPlayer_2_Attack) btn.setOnClickListener(bottomClickeListener);
        for(Button btn : btnsPlayer_2_Attack) btn.setEnabled(false);

    }

    private View.OnClickListener bottomClickeListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            atkNum = Integer.parseInt(((Button) view).getTag().toString());
            atkButtonClicked(atkNum);
        }
    };

    private void setProgressBars() {
        pBar_main_Player1ProgressBar = findViewById(R.id.prgrsBar_main_Player1ProgressBar);
        pBar_main_Player2ProgressBar = findViewById(R.id.prgrsBar_main_Player2ProgressBar);
        pBar_main_Player1ProgressBar.setBackgroundColor(getColor(R.color.lifeBarGreen));
        pBar_main_Player2ProgressBar.setBackgroundColor(getColor(R.color.lifeBarGreen));
        pBar_main_Player1ProgressBar.setMax(MAX_HP);
        pBar_main_Player2ProgressBar.setMax(MAX_HP);
        pBar_main_Player1ProgressBar.setProgress(MAX_HP);
        pBar_main_Player2ProgressBar.setProgress(MAX_HP);
    }

    private void atkButtonClicked(int atkNumber) {
        //0->2 player 1 attacked  // 3->5 player 2 attacked
        int damagedHP = reduceDamageFromOpp(atkNumber);
        changeColor(damagedHP,atkNumber);
        checkWinner(damagedHP, atkNumber);
        toggleAtkGroup();


    }

    private void changeColor(int damagedHP,int atkNumber) {
        if(damagedHP<HP_RED_BAR_VALUE){
            if (atkNumber / NUMBER_OF_ATTACKS == 0) // player 1
                pBar_main_Player2ProgressBar.setBackgroundColor(getColor(R.color.lifeBarRed));
            else
                pBar_main_Player1ProgressBar.setBackgroundColor(getColor(R.color.lifeBarRed));


        }

    }

    private void checkWinner(int damagedHP,int atkNumber) {
        if (damagedHP < 1) {
            if (atkNumber / NUMBER_OF_ATTACKS == 0)
                announceWinner(getString(R.string.Player1Name)); // change to variable String
            else
                announceWinner(getString(R.string.Player2Name)); // change to variable String

            for(Button btn : btnsPlayer_1_Attack) btn.setEnabled(false);
            for(Button btn : btnsPlayer_2_Attack) btn.setEnabled(false);

        }

    }

    private void announceWinner(String playerName) {
        isGameDone=true;
        if(playerName.equals(getString(R.string.Player2Name))){
            img_main_winnerPic.setImageDrawable(getDrawable(R.drawable.ic_superman));
            setLblAnnounceWinner(getText(R.string.Player2Name));
        }
        else{
            img_main_winnerPic.setImageDrawable(getDrawable(R.drawable.ic_batman));
            setLblAnnounceWinner(getText(R.string.Player1Name));

        }
        lay_main_winnerAnnouncement.animate().rotation(720).alpha(1).setDuration(1000);
        setLblAnnounceWinner(getText(R.string.Player1Name));

    }

    private void setLblAnnounceWinner(CharSequence playerName) {
        String lblText = playerName + " " + getText(R.string.winnerAnnouncement);
        lbl_main_announceWinnerName.setText(lblText);
    }

    private void toggleAtkGroup() {
        if (isPlayer1PlayingNextTurn) { //
            for(Button btn : btnsPlayer_1_Attack) btn.setEnabled(true);
            for(Button btn : btnsPlayer_2_Attack) btn.setEnabled(false);
            isPlayer1PlayingNextTurn=false;
        }
        else {
            for(Button btn : btnsPlayer_1_Attack) btn.setEnabled(false);
            for(Button btn : btnsPlayer_2_Attack) btn.setEnabled(true);
            isPlayer1PlayingNextTurn=true;
        }

    }


    private int reduceDamageFromOpp(int atkNumber) {
        Log.i("id in button  ", atkNumber + " ");
        int currentHP;
        if (atkNumber / NUMBER_OF_ATTACKS == 0) { // player 1 is attacking
            currentHP = pBar_main_Player2ProgressBar.getProgress();
            if (atkNumber % NUMBER_OF_ATTACKS == 0)
                currentHP -= 10;
            else if (atkNumber % NUMBER_OF_ATTACKS == 1)
                currentHP -= 20;
            else //(atkNumber%NUMBER_OF_ATTACKS==2)
               currentHP -= 50;

            pBar_main_Player2ProgressBar.setProgress(currentHP);
            return currentHP; // updated after damage reduced

        } else { // player 2 is attacking
            currentHP = pBar_main_Player1ProgressBar.getProgress();
            if (atkNumber % NUMBER_OF_ATTACKS == 0)
                currentHP -= 10;
            else if (atkNumber % NUMBER_OF_ATTACKS == 1)
                currentHP -= 20;
            else // (atkNumber%NUMBER_OF_ATTACKS==2)
                currentHP -= 50;

            pBar_main_Player1ProgressBar.setProgress(currentHP);
            return currentHP; // updated after damage reduced
        }
    }
}





