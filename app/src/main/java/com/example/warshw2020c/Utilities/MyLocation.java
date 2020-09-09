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

//    // TODO: 08/09/2020 check if necessary [ This is a Handler for PermissionRequestResult ]
//    // Solution from OneNote-> Android Dev -> Fragments + callBack -> Callback2 page 5 |
//    //  | Instead of extending the entire class FragmentActivity
//    FragmentActivity fragmentActivity = new FragmentActivity() {
//        @Override
//        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//
//                if (ContextCompat.checkSelfPermission(appContext, Manifest.permission.ACCESS_FINE_LOCATION)
//                        == PackageManager.PERMISSION_GRANTED) {
//                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
//                }
//            }
//
//        }
//    };
//  | Instead of extending the entire class FragmentActivity
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length == 0)
            return;
        if(requestCode==KEYS.MY_PERMISSIONS_REQUEST_LOCATION){
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED)
            if (ContextCompat.checkSelfPermission(appContext, Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
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

    public double[]activityAskForLocation() throws Exception {//TODO:08/09/2020 make it possible to ask permissions inside here instead of the calling functing ASK GUY
        if(ContextCompat.checkSelfPermission(appContext,Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED)
            throw new Exception(MyLocation.KEYS.NO_LOCATION_PERMISSION);
        else {
            initLocationManagerNListener();
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            MySignalV2.getInstance().showToast(this.lastLocationLat + "," + this.lastLocationLon);
            double[] result = {lastLocationLat, lastLocationLon};
            return result;
        }
    }
//}


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
                    lastLocationString = location.toString();
                    lastLocationLat=location.getLatitude();
                    lastLocationLon=location.getLongitude();
                }
            };
        }
    }

}
