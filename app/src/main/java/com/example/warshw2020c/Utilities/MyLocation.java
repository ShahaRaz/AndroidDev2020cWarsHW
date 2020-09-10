package com.example.warshw2020c.Utilities;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.warshw2020c.TopScore;

public class MyLocation extends FragmentActivity {
    private static MyLocation instance;
    private static Context appContext;


    private LocationManager locationManager;
    private LocationListener locationListener;
    private String lastLocationString;
    private double lastLocationLat;
    private double lastLocationLon;
    Location lastKnownLocation;


    public interface KEYS {
        String NO_LOCATION_PERMISSION = "NO_LOCATION_PERMISSION";
        int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    }


    public static MyLocation getInstance()  {
        return instance;
    }

    private MyLocation(Context context) {
        appContext = context;
    }

    public static MyLocation initHelper(Context context) {
        if (instance == null) // init once from MyApp
            instance = new MyLocation(context);
        return instance;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length == 0)
            return;
        if(requestCode==KEYS.MY_PERMISSIONS_REQUEST_LOCATION){
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED)
            if (ContextCompat.checkSelfPermission(appContext, Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10, locationListener);
            }
        }

    }

/* // TODO:08/09/2020make ot possible to ask permissions inside here instead of the calling functing ASK GUY
    public double[] activityAskForLocation(Activity callerActivity) throws Exception {
        // have permission?
        if (ContextCompat.checkSelfPermission(appContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            buildAlertMessageNoGps();
            ActivityCompat.requestPermissions(callerActivity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, KEYS.MY_PERMISSIONS_REQUEST_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(appContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            throw new Exception(MyLocation.KEYS.NO_LOCATION_PERMISSION);
            }
        else { // we have permission!
            initLocationManagerNListener();
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            MySignalV2.getInstance().showToast(this.lastLocationLat + " , " + this.lastLocationLon);
            double[] result = {lastLocationLat,lastLocationLon};
            return result;
        }
    }


 */


    public double[] activityAskForLocation() {//TODO:08/09/2020 make it possible to ask permissions inside here instead of the calling functing ASK GUY
        if(ContextCompat.checkSelfPermission(appContext,Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED) {
            double[] resultBad = {0.0, 0.0};
            return resultBad;
        }
        else { // permission is granted
            initLocationManagerNListener();
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10, locationListener);

            MySignalV2.getInstance().showToast(this.lastLocationLat + "," + this.lastLocationLon);
            double[] resultGood = {lastLocationLat, lastLocationLon};
            return resultGood;
        }
    }



    public static boolean requestFineLocationPermission(Activity callerActivity){
        if (ContextCompat.checkSelfPermission(appContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // request permission
            ActivityCompat.requestPermissions(callerActivity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, KEYS.MY_PERMISSIONS_REQUEST_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(appContext, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            return true;
        else
            return false;
    }



    private void initLocationManagerNListener() {
        if (ActivityCompat.checkSelfPermission(appContext,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager = (LocationManager) appContext.getSystemService(Context.LOCATION_SERVICE);
            locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(@NonNull Location location) {
                    Log.i("MyLocation: ", location.toString());
                    lastKnownLocation=location;
                    lastLocationString = location.toString();
                    lastLocationLat=location.getLatitude();
                    lastLocationLon=location.getLongitude();
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderDisabled(@NonNull String provider) {
                    MySignalV2.getInstance().showToast("Please Activate Location Services");
                }

                @Override
                public void onProviderEnabled(@NonNull String provider) {
                    MySignalV2.getInstance().showToast("Location is on :)");

                }
            };
        }
    }

}
