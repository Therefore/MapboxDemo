package com.example.nathan.mapboxdemo;

import android.app.Activity;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;

/**
 * Created by Nathan on 7/29/2017.
 */

public class FusedLocationProvider extends Activity implements LocationListener {
    @Override
    public void onLocationChanged(Location location) {
        Log.d("Location Changed: ", Double.toString(location.getLatitude()));
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d("Provider status: ", "Enabled");

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
