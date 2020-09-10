package com.example.warshw2020c;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
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

import com.example.warshw2020c.Fragments.Activity_TopNBoard;
import com.example.warshw2020c.Utilities.MyLocation;
import com.example.warshw2020c.Utilities.MySPV3;
import com.example.warshw2020c.Utilities.MySignalV2;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Random;

public class Activity_Battle extends AppCompatActivity {
    private static final int NUMBER_OF_ATTACKS = 3;
    public static final int DELAY_TIME_IN_MILISEC = 10;
    private static final int MAX_HP = 200;
    private static final int HP_RED_BAR_VALUE = 60;
    private ProgressBar pBar_battle_Player1ProgressBar, pBar_battle_Player2ProgressBar;
    private ArrayList<Button> btnsPlayer_1_Attack = new ArrayList<Button>();
    private ArrayList<Button> btnsPlayer_2_Attack = new ArrayList<Button>();
    private ImageView btn_battle_rollDice;
    private Random rng = new Random(); // random number generator
    private TextView lbl_battle_announceWinnerName;
    private ImageView img_battle_winnerPic;
    private LinearLayout lay_battle_winnerAnnouncement;
    private LinearLayout lay_battle_rollDice;
    private ImageView img_battle_diceResultP1;
    private ImageView img_battle_diceResultP2;
    private Button btn_battle_startGame;
    private int player1Dice;
    private int player2Dice;
    private boolean isPlayer1PlayingNextTurn;
    private int atkNum; // 2 players , 3 atks for each => atkNum [0,5] (in range)
    private boolean isGameDone;
    private Button btn_battle_BoardButton;
    private Button btn_battle_backButton;
    private int hitCounterP1;
    private int hitCounterP2;

    double[] valuesLatLon = new double[2]; // lat,lon
    long currentTimeStamp;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length == 0) {
            // alert - no permission
            return;

        }

        switch (requestCode) {
            case MyLocation.KEYS.MY_PERMISSIONS_REQUEST_LOCATION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        valuesLatLon = MyLocation.getInstance().activityAskForLocation();
                    }
                else {
                    MySignalV2.getInstance().showToast("Location Permission denied");
                    valuesLatLon[0]=0.0;
                    valuesLatLon[1]=0.0;
                }

            break;
        }
        // alert - no permission
        return;
    }




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
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(!isGameDone)
                    runNextTimedTurn();
                handler.postDelayed(this,DELAY_TIME_IN_MILISEC);
            }
        },DELAY_TIME_IN_MILISEC);



    }

    private void runNextTimedTurn() {
        int rndmAtkNum = (rng.nextInt(NUMBER_OF_ATTACKS)); // 0->2
        if(isPlayer1PlayingNextTurn)
            rndmAtkNum+=NUMBER_OF_ATTACKS; // player's 2 turn

        atkButtonClicked(rndmAtkNum);

    }

    private void StartDiceLottery() {
        btn_battle_rollDice = findViewById(R.id.btn_battle_rollDice);
        img_battle_diceResultP1 = findViewById((R.id.img_battle_diceResultP1));
        img_battle_diceResultP2 = findViewById((R.id.img_battle_diceResultP2));
        lay_battle_rollDice = findViewById(R.id.lay_battle_rollDice);

        btn_battle_rollDice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rollDice();
            }
        });


        btn_battle_startGame = findViewById(R.id.btn_battle_startGame);
        //btn_battle_startGame.setClickable(false);
        btn_battle_startGame.setOnClickListener(new View.OnClickListener() {
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
        lay_battle_rollDice.setAlpha(0);
        pBar_battle_Player1ProgressBar.setAlpha(1);
        pBar_battle_Player2ProgressBar.setAlpha(1);
        startGameWTimer();

    }

    private void rollDice() {
        player1Dice = (rng.nextInt(6)+1); //
        showDiceToPlayer(player1Dice , img_battle_diceResultP1);


        player2Dice = (rng.nextInt(6)+1); //
        showDiceToPlayer(player2Dice , img_battle_diceResultP2);

        if(player1Dice==player2Dice)
            btn_battle_startGame.setEnabled(false);
        else // not same result
            btn_battle_startGame.setEnabled(true);


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
        lbl_battle_announceWinnerName = findViewById(R.id.lbl_battle_announceWinnerName);
        img_battle_winnerPic = findViewById(R.id.img_battle_winnerPic);
        lay_battle_winnerAnnouncement = findViewById(R.id.lay_battle_winnerAnnouncement);

    }

    private void setDefaultFirstAttacker() {
        //by default player 1 starts
        for(Button btn : btnsPlayer_2_Attack) btn.setEnabled(false);
        isPlayer1PlayingNextTurn =true;

    }


    private void associateButtons() {
        //Setting an arrayList (manual Toggle group)
        btnsPlayer_1_Attack.add((Button)findViewById(R.id.btn_battle_atk1));
        btnsPlayer_1_Attack.add((Button)findViewById(R.id.btn_battle_atk2));
        btnsPlayer_1_Attack.add((Button)findViewById(R.id.btn_battle_atk3));
       // for(Button btn : btnsPlayer_1_Attack) btn.setOnClickListener(buttonClickeListener);
        for(Button btn : btnsPlayer_1_Attack) btn.setEnabled(false);

        btnsPlayer_2_Attack.add((Button)findViewById(R.id.btn_battle_atk4));
        btnsPlayer_2_Attack.add((Button)findViewById(R.id.btn_battle_atk5));
        btnsPlayer_2_Attack.add((Button)findViewById(R.id.btn_battle_atk6));
       // for(Button btn : btnsPlayer_2_Attack) btn.setOnClickListener(buttonClickeListener);
        for(Button btn : btnsPlayer_2_Attack) btn.setEnabled(false);

        btn_battle_backButton = findViewById(R.id.btn_battle_backButton);
        btn_battle_backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        btn_battle_BoardButton = findViewById(R.id.btn_battle_BoardButton);

        btn_battle_BoardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentTop10_FromBattle = new Intent(Activity_Battle.this, Activity_TopNBoard.class);
                startActivity(intentTop10_FromBattle);
                finish(); // Saves result to SP when announcing winner
            }
        });
        // UNCOMMENT setOnClickListeners inorder to enable __ Manual Tap Battle __
    }

    private View.OnClickListener buttonClickeListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            atkNum = Integer.parseInt(((Button) view).getTag().toString());
            atkButtonClicked(atkNum);
        }
    };

    private void setProgressBars() {
        pBar_battle_Player1ProgressBar = findViewById(R.id.prgrsBar_battle_Player1ProgressBar);
        pBar_battle_Player2ProgressBar = findViewById(R.id.prgrsBar_battle_Player2ProgressBar);
        pBar_battle_Player1ProgressBar.setBackgroundColor(getColor(R.color.lifeBarGreen));
        pBar_battle_Player2ProgressBar.setBackgroundColor(getColor(R.color.lifeBarGreen));
        pBar_battle_Player1ProgressBar.setMax(MAX_HP);
        pBar_battle_Player2ProgressBar.setMax(MAX_HP);
        pBar_battle_Player1ProgressBar.setProgress(MAX_HP);
        pBar_battle_Player2ProgressBar.setProgress(MAX_HP);
        hitCounterP1=0;
        hitCounterP2=0;
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
                pBar_battle_Player2ProgressBar.setBackgroundColor(getColor(R.color.lifeBarRed));
            else
                pBar_battle_Player1ProgressBar.setBackgroundColor(getColor(R.color.lifeBarRed));


        }

    }

    private void checkWinner(int damagedHP,int atkNumber) {
        if (damagedHP < 1) {
            /*Disable buttons*/
            for(Button btn : btnsPlayer_1_Attack) btn.setEnabled(false);
            for(Button btn : btnsPlayer_2_Attack) btn.setEnabled(false);

            /*check who is the winner */
            if (atkNumber / NUMBER_OF_ATTACKS == 0) {
                announceWinner(getString(R.string.Player1Name),hitCounterP1);
            }
            else {
                announceWinner(getString(R.string.Player2Name),hitCounterP2);

            }


        }

    }

    private void announceWinner(String playerName,int winnersHitCount) {

        isGameDone=true;
        getLocationCoordinatesForSP();
        saveBattleScoreToSP(playerName,winnersHitCount);

        if(playerName.equals(getString(R.string.Player2Name))){
            img_battle_winnerPic.setImageDrawable(getDrawable(R.drawable.ic_superman));
            setLblAnnounceWinner(getText(R.string.Player2Name),hitCounterP2);

        }
        else{
            img_battle_winnerPic.setImageDrawable(getDrawable(R.drawable.ic_batman));
            setLblAnnounceWinner(getText(R.string.Player1Name),hitCounterP1);
        }


        lay_battle_winnerAnnouncement.animate().rotation(720).alpha(1).setDuration(1000);



    }

    private void getLocationCoordinatesForSP() {
        // have permission?
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MyLocation.KEYS.MY_PERMISSIONS_REQUEST_LOCATION);
        }
        valuesLatLon = MyLocation.getInstance().activityAskForLocation();
    }

    private void saveBattleScoreToSP(String playerName,int winnersHitCount){

        currentTimeStamp = System.currentTimeMillis() / 1000L;


        valuesLatLon = MyLocation.getInstance().activityAskForLocation();

        Gson json = new Gson();
        String scoreAsJson = json.toJson(new TopScore(valuesLatLon[0], valuesLatLon[1], currentTimeStamp, winnersHitCount, playerName));
        MySPV3.getInstance().putString(MySPV3.KEYS.LAST_GAME, scoreAsJson);
    }
// TODO: 10/09/2020 delete me if im not needed 
//    private double[] getLocationCoordinates(){
//        double[] valuesLatLon = new double[2]; // lat,lon
//
//            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//                valuesLatLon[0]=30.0;
//                valuesLatLon[1]=30.0;
//            }
//            else{
//                valuesLatLon = MyLocation.getInstance().activityAskForLocation();
//            }
///*
//                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MyLocation.KEYS.MY_PERMISSIONS_REQUEST_LOCATION);
//
//                    boolean Result = MyLocation.requestFineLocationPermission(this);
//                Log.d("Ask Location Permission: " , ""+ Result);
//
//
// */
//            valuesLatLon[0]=30.0;
//            valuesLatLon[1]=30.0;
//    }



    private void setLblAnnounceWinner(CharSequence playerName,int strikeCount) {
        String lblText = playerName + " " + getText(R.string.winnerAnnouncement) + " with " + strikeCount + " Strikes!";
        lbl_battle_announceWinnerName.setText(lblText);

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
            hitCounterP1++;
            currentHP = pBar_battle_Player2ProgressBar.getProgress();
            if (atkNumber % NUMBER_OF_ATTACKS == 0)
                currentHP -= 10;
            else if (atkNumber % NUMBER_OF_ATTACKS == 1)
                currentHP -= 20;
            else { //(atkNumber%NUMBER_OF_ATTACKS==2)
                currentHP -= 50;
                MySignalV2.getInstance().vibrate(200);
            }
            pBar_battle_Player2ProgressBar.setProgress(currentHP);
            return currentHP; // updated after damage reduced

        }
        else { // player 2 is attacking
            currentHP = pBar_battle_Player1ProgressBar.getProgress();
            hitCounterP2++;
            if (atkNumber % NUMBER_OF_ATTACKS == 0)
                currentHP -= 10;
            else if (atkNumber % NUMBER_OF_ATTACKS == 1)
                currentHP -= 20;
            else // (atkNumber%NUMBER_OF_ATTACKS==2)
                currentHP -= 50;

            pBar_battle_Player1ProgressBar.setProgress(currentHP);
            return currentHP; // updated after damage reduced
        }
    }
}





