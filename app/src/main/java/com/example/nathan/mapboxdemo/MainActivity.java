package com.example.nathan.mapboxdemo;

import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.drawable.Icon;
import android.location.Location;
import android.location.LocationListener;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.annotations.MarkerViewOptions;
import com.mapbox.mapboxsdk.annotations.PolylineOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.geometry.LatLngBounds;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.services.commons.utils.MapboxUtils;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;

import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.SmartLocation;
import io.nlopez.smartlocation.location.config.LocationAccuracy;
import io.nlopez.smartlocation.location.config.LocationParams;
import io.nlopez.smartlocation.location.providers.LocationGooglePlayServicesProvider;

import static com.example.nathan.mapboxdemo.R.id.map;
import static com.example.nathan.mapboxdemo.R.id.never;

public class MainActivity extends AppCompatActivity implements LocationListener, OnLocationUpdatedListener{

    private MapView mapView;

    public ArrayList<LatLng> points = new ArrayList<LatLng>();

    private LocationGooglePlayServicesProvider provider; // for smart location library
    


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Mapbox.getInstance(this, getString(R.string.access_token));
        setContentView(R.layout.activity_main2);

        startLocation();//start location


        //floating action button stuff
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                //setup icon to add
                IconFactory iconFactory = IconFactory.getInstance(MainActivity.this);
                final com.mapbox.mapboxsdk.annotations.Icon icon = iconFactory.fromResource(R.drawable.w1_5_pin);
                mapView.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(MapboxMap mapboxMap) {

                        LatLng latLng = mapboxMap.getCameraPosition().target; //get lat and lon of center of screen


                        // Interact with the map using mapboxMap here
                        MarkerViewOptions markerViewOptions = new MarkerViewOptions()
                                .position(latLng)
                                .title(getString(R.string.AUS))
                                .icon(icon);

                        // Draw polyline on the map
                        ArrayList<LatLng> points = new ArrayList<LatLng>();
                        points.add(new LatLng(36,-84));
                        points.add(new LatLng(37,-85));
                        points.add(new LatLng(31,-82));
                        points.add(new LatLng(35,-81));
                        points.add(new LatLng(33,-89));
                        points.add(new LatLng(37,-87));
                        points.add(new LatLng(36,-84));
                        points.add(new LatLng(38,-86));
                        points.add(new LatLng(39,-82));
                        points.add(latLng);

                        mapboxMap.addPolyline(new PolylineOptions()
                                //.addAll(points)
                                .add(latLng) // sets the lat and lon
                                .color(Color.parseColor("#3bb2d0")) //color of the line
                                .width(2)); // width of the line

                        CameraPosition position = new CameraPosition.Builder()
                                .target(new LatLng(latLng)) // Sets the new camera position
                               // .zoom() // Sets the zoom
                                .bearing(0) // Rotate the camera
                                .tilt(0) // Set the camera tilt
                                .build(); // Creates a CameraPosition from the builder

                        mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(position), 3000);//sets the new camera position and animation time

                        // Draw rectangle on the map
                        ArrayList<LatLng> rect = new ArrayList<LatLng>();
                        rect.add(new LatLng(0,0));
                        rect.add(new LatLng(10,0));
                        rect.add(new LatLng(10,10));
                        rect.add(new LatLng(0,0));
                        mapboxMap.addPolyline(new PolylineOptions()
                                .addAll(rect)
                                .color(Color.parseColor("#3bb2d0"))
                                .width(2));

                        //map click listener
                        mapboxMap.setOnMapClickListener(new MapboxMap.OnMapClickListener() {
                            @Override
                            public void onMapClick(@NonNull LatLng point) {
                                String string = String.format(Locale.US, "User clicked at: %s", point.toString());
                                Toast.makeText(MainActivity.this, string, Toast.LENGTH_LONG).show();
                            }
                        });



                        mapboxMap.addMarker(markerViewOptions);

                    }
                });
            }
        });

        // Create a mapView
        mapView = (MapView) findViewById(R.id.mapView);

        //setup icon to add
        IconFactory iconFactory = IconFactory.getInstance(MainActivity.this);
        final com.mapbox.mapboxsdk.annotations.Icon icon = iconFactory.fromResource(R.drawable.w1_5_pin);

        mapView.onCreate(savedInstanceState);

        //add an icon to the map



    }
    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();


    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    public void addSign() {


    }

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


    @Override
    public void onLocationUpdated(final Location location) {

        //below code updates the maplocation based upon new gps coordinates
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(MapboxMap mapboxMap) {


                CameraPosition position = new CameraPosition.Builder()
                        .target(new LatLng(location.getLatitude(), location.getLongitude())) // Sets the new camera position
                        .zoom(10) // Sets the zoom
                        .bearing(0) // Rotate the camera
                        .tilt(0) // Set the camera tilt
                        .build(); // Creates a CameraPosition from the builder

                mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(position), 3000);//sets the new camera position and animation time

                LatLng latLng = mapboxMap.getCameraPosition().target; //get lat and lon of center of screen

                // Draw polyline on the map
                points.add(latLng);

                mapboxMap.addPolyline(new PolylineOptions()
                        //.addAll(points)

                        .addAll(points) // sets the lat and lon
                        .color(Color.parseColor("#3bb2d0")) //color of the line
                        .width(2)); // width of the line
            }
        });
    }

    private void startLocation() {

        provider = new LocationGooglePlayServicesProvider();
        provider.setCheckLocationSettings(true);

        SmartLocation smartLocation = new SmartLocation.Builder(this).logging(true).build();

        smartLocation.location(provider).start(this);
        //smartLocation.activity().start(this);

    }

    private void startLocationListener() {

        long mLocTrackingInterval = 1000 * 1; // 1 sec
        float trackingDistance = 0;
        LocationAccuracy trackingAccuracy = LocationAccuracy.HIGH;

        LocationParams.Builder builder = new LocationParams.Builder()
                .setAccuracy(trackingAccuracy)
                .setDistance(trackingDistance)
                .setInterval(mLocTrackingInterval);

        SmartLocation.with(this)
                .location()
                .continuous()
                .config(builder.build())
                .start(new OnLocationUpdatedListener() {
                    @Override
                    public void onLocationUpdated(Location location) {
                        //processLocation(location);
                    }
                });
    }



}
