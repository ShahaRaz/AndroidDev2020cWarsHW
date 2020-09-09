package com.example.warshw2020c.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.warshw2020c.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class Fragment_Map extends Fragment implements OnMapReadyCallback {

    private static final String TAG = "pttt" + Fragment_Map.class.getName();

    private ArrayList<Marker> mMarkerArray = new ArrayList<Marker>();
    Marker lastMarkerSelected;
    protected View view;
    private TextView map_LBL_info;
    GoogleMap map;
    MapView mapView;

    // callback for the 2TopNBoard2
    private CallBack_Map callBack_TopNBoard;
    public void setTopBoardActivityCallBack(CallBack_Map callBack_map){
        this.callBack_TopNBoard = callBack_map;
    }

    public static Fragment_Map newInstance() {
        Log.d(TAG, "newInstance");
        Fragment_Map fragment = new Fragment_Map();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");

        if (view == null) {
            view = inflater.inflate(R.layout.fragment_map, container, false);
        }
/*
        findViews(view);

        map_LBL_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (callBack_mainActivity != null) {
                    callBack_mainActivity.setMyColor(Color.GREEN);
                }

                if (callBack_location != null) {
                    callBack_location.locationReady(32.049278, 34.806560);
                }
            }
        });

*/
        return view;
    }

//    private void findViews(View view) {
//        map_LBL_info = view.findViewById(R.id.map_LBL_info);
//    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        Log.d("pttt", "onSaveInstanceState");

        super.onSaveInstanceState(outState);
    }


    public void setMap(String location) {
        map_LBL_info.setText(location);
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

        if (!hidden) {

        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mapView = view.findViewById(R.id.mapFrag_MAP_GMaps);
        if(mapView!=null){
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(getContext());
        map = googleMap;
        map.setMinZoomPreference(5);
        LatLng telAviv = new LatLng(32.073841,34.792090);
        lastMarkerSelected = map.addMarker((new MarkerOptions().position(telAviv).title("Azrieli Towers TLV")));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(telAviv,13)); // number between 1-20 (20 must zoomed in)
    }

    public void markNewPoint(double lat,double lon,String lbl){
        if(lon==0.0)
            return; // invalid location
        // got a valid location:
        lastMarkerSelected.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)); // revert color of last marker selected
        LatLng tempPoint = new LatLng(lat, lon);
        lastMarkerSelected = map.addMarker((new MarkerOptions().position(tempPoint).title(lbl))
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))); // change color
        map.moveCamera(CameraUpdateFactory.newLatLng(tempPoint));
        mMarkerArray.add(lastMarkerSelected); // create arrayList of markers
        }

}
