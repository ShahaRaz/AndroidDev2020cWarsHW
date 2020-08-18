package com.example.warshw2020c;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Canvas;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {
    ProgressBar prgrsBar_main_Player1ProgressBar,prgrsBar_main_Player2ProgressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void prog()
    {
        prgrsBar_main_Player1ProgressBar = (ProgressBar)findViewById(R.id.prgrsBar_main_Player1ProgressBar);
        prgrsBar_main_Player2ProgressBar = (ProgressBar)findViewById(R.id.prgrsBar_main_Player2ProgressBar);
        prgrsBar_main_Player1ProgressBar.setMax(200);
        prgrsBar_main_Player2ProgressBar.setMax(200);

    }

    public void draw (Canvas canvas){


    }



}