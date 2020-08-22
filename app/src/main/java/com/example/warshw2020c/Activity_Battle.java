// TODO: 21/08/2020 Repalace keyword _main_ with _Battle_ in all necessary places 
package com.example.warshw2020c;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

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
    int player1Dice;
    int player2Dice;


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
        setDefaultFirstAttacker(); // more readable (even though it's one line method)
        StartDiceLottery();
        setProgressBars();
        setDefaultWinnerTag();
    }

    private void StartDiceLottery() {
        btn_main_rollDice = findViewById(R.id.btn_main_rollDice);
        img_main_diceResultP1 = findViewById((R.id.img_main_diceResultP1));
        img_main_diceResultP2 = findViewById((R.id.img_main_diceResultP2));
        lay_main_rollDice = findViewById(R.id.lay_main_rollDice);

        btn_main_startGame = findViewById(R.id.btn_main_startGame);
        //btn_main_startGame.setClickable(false);
        btn_main_startGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishedLottery();
            }
        });


        btn_main_rollDice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rollDice();
            }
        });
    }

    private void finishedLottery() {
        if(player2Dice>player1Dice)
            toggleAtkGroup();// by default player1 starts
        lay_main_rollDice.setAlpha(0);
        pBar_main_Player1ProgressBar.setAlpha(1);
        pBar_main_Player2ProgressBar.setAlpha(1);
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


    private void setDefaultWinnerTag() {
        lbl_main_announceWinnerName = findViewById(R.id.lbl_main_announceWinnerName);
        img_main_winnerPic = findViewById(R.id.img_main_winnerPic);
        lay_main_winnerAnnouncement = findViewById(R.id.lay_main_winnerAnnouncement);

    }

    private void setDefaultFirstAttacker() {
        //by default player 1 starts
        for(Button btn : btnsPlayer_2_Attack) btn.setEnabled(false);

    }


    private void associateButtons() {
        //Setting an arrayList (manual Toggle group)
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
            atkButtonClicked(view);
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

    private void atkButtonClicked(View view) {

        int damagedHP = reduceDamageFromOpp(view);
        changeColor(damagedHP,view);
        toggleAtkGroup();
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
        lay_main_winnerAnnouncement.setTranslationY(0);
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
        lbl_main_announceWinnerName.setText( playerName + " " + getText(R.string.winnerAnnouncement));
    }

    private void toggleAtkGroup() {
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





