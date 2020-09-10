package com.example.warshw2020c.Utilities;

import android.app.Application;

public class MyApp extends Application {

    public static MySPV3 MySPV3;
    public static MySignalV2 MySignal;
    public static MyLocation MyLocation;

    public static MyDate MyDate;

    @Override
    public void onCreate() {
        super.onCreate();
        MySPV3.initHelper(this);
        MySignal.initHelper(this);
        MyLocation.initHelper(this);
        //MySPV3.getInstance().clearSP("MAIN_SP");
    }
}
