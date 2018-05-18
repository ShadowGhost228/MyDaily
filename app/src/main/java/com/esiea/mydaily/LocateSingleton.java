package com.esiea.mydaily;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;

public class LocateSingleton {

    private LocateSingleton(){

    }

    private static LocateSingleton instance = null;

    public static void getLocation(LocationListener locationListener, LocationManager locationManager, Context context){
        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            return;
        }
        //locationManager.getLastKnownLocation("gps", 5000, 0, locationListener);
    }

    /*
    public static LocateSingleton getInstance() {

        if (instance == null) {
            instance = new LocateSingleton();
        }

        return instance;

    }*/

}
