package com.example.warshw2020c.Utilities;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class MySPV3 {

    public interface KEYS {
        public static final String LAST_GAME = "LAST_GAME";
        public static final String TOP_N_LIST_OBJ = "TOP_N_LIST_OBJ";
        public static final String USER_LAST_LOGIN_TIME_STAMP = "USER_LAST_LOGIN_TIME_STAMP";
        public static final String USER_MAIL = "USER_MAIL";
    }

    // TODO: 27/08/2020 insert Gson here, so i dont need to import & create new Instance every time i insert object as string into here.

    private static MySPV3 instance;
    private SharedPreferences prefs;

    public static MySPV3 getInstance() {
        return instance;
    }

    private MySPV3(Context context) {
        prefs = context.getApplicationContext().getSharedPreferences("MAIN_SP", Context.MODE_PRIVATE);
    }

    public static MySPV3 initHelper(Context context) {
        if (instance == null)
            instance = new MySPV3(context);
        return instance;
    }

    public void putString(String key, String value) {
        prefs.edit().putString(key, value).apply();
    }

    public String getString(String key, String def) {
        return prefs.getString(key, def);
    }

    public void putBoolean(String key, boolean value) {
        prefs.edit().putBoolean(key, value).apply();
    }

    public boolean getBoolean(String key, boolean def) {
        return prefs.getBoolean(key, def);
    }

    public void putDouble(String KEY, double defValue) {
        putString(KEY, String.valueOf(defValue));
    }

    public double getDouble(String KEY, double defValue) {
        return Double.parseDouble(getString(KEY, String.valueOf(defValue)));
    }
}
