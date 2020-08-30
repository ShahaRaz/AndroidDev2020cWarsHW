package com.example.warshw2020c.Utilities;

import android.content.Context;
import android.content.SharedPreferences;

public class MySPV3 {

    public interface KEYS {
          String LAST_GAME = "LAST_GAME";
          String TOP_N_LIST_OBJ = "TOP_N_LIST_OBJ";
    }

    // TODO: 27/08/2020 insert Gson here, so i dont need to import & create new Instance every time i insert object as string into here.

    private static MySPV3 instance;
    private SharedPreferences prefs;
    private static final String FILE_NAME = "MAIN_SP";

    public static MySPV3 getInstance() {
        return instance;
    }

    private MySPV3(Context context) {
        prefs = context.getApplicationContext().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
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
    public void clearSP(String sharedPrefFileName){
        /*Only call from MyApp Input string LABELED above as FILE_NAME*/
//        /*ex:         MySPV3.getInstance().clearSP("APP_SP_DB");  */
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.apply();

    }
}
