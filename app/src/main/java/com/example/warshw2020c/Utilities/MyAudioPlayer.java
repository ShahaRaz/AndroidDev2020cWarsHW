package com.example.warshw2020c.Utilities;

import android.content.Context;
import android.media.MediaPlayer;

import com.example.warshw2020c.R;

public class MyAudioPlayer {

    private MediaPlayer mMediaPlayer;

    public void stop() {
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    public void play(int rid) {
        stop();

        mMediaPlayer = MediaPlayer.create(appContext, rid);
        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                stop();
            }
        });

        mMediaPlayer.start();
    }


    //_______________________
    private static MyAudioPlayer instance;
    private static Context appContext;


    public static MyAudioPlayer getInstance() {
        return instance;
    }

    private MyAudioPlayer(Context context) {
        appContext = context;
    }

    public static MyAudioPlayer initHelper(Context context) {
        if (instance == null) {
            instance = new MyAudioPlayer(context);
        }
        return instance;
    }

}

