package com.example.hikerswatch;



import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends Activity implements LocationListener {

    LocationManager locationManager;

    String provider;

    TextView latv;
    TextView lngv;
    TextView accuracyv;
    TextView bearingv;
    TextView speedv;
    TextView altv;
    TextView addressv;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        latv = findViewById(R.id.lat);
        lngv = findViewById(R.id.lng);
        accuracyv = findViewById(R.id.accuracy);
        speedv = findViewById(R.id.speed);
        bearingv = findViewById(R.id.bearing);
        altv = findViewById(R.id.alt);
        addressv = findViewById(R.id.address);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        provider = locationManager.getBestProvider(new Criteria(), false);

        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        }
        Location location = locationManager.getLastKnownLocation(provider);

        onLocationChanged(location);



    }

    @Override
    protected void onPause() {
        super.onPause();

        locationManager.removeUpdates(this);

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(provider, 400, 1, this);

    }

    @Override
    public void onLocationChanged(Location location) {

        Double lat = location.getLatitude();

        Double lng = location.getLongitude();

        Double alt = location.getAltitude();

        float bearing = location.getBearing();

        float speed = location.getSpeed();

        float accuracy = location.getAccuracy();

        //Address near location
        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

        try {

            List<Address> addressList = geocoder.getFromLocation(lat, lng, 1);

            if (addressList != null && addressList.size() > 0){

                Log.i("Place Info", addressList.get(0).toString());

                String addressHolder = "";

                for (int i = 0; i < addressList.get(0).getMaxAddressLineIndex(); i++){

                    addressHolder = addressList.get(0).getAddressLine(i) + "\n";

                }

                addressv.setText("Address:\n " + addressHolder);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        latv.setText("Latitude: " + lat.toString());
        lngv.setText("Longitude: " + lng.toString());
        altv.setText("Altitude: " + alt.toString() + "m");
        bearingv.setText("Bearing: " + bearing);
        speedv.setText("Speed: " + speed + "m/s");
        accuracyv.setText("Accuracy: " + accuracy + "m");


        Log.i("Latitude", String.valueOf(lat));
        Log.i("Longitude", String.valueOf(lng));
        Log.i("Altitude", String.valueOf(alt));
        Log.i("Bearing", String.valueOf(bearing));
        Log.i("Speed", String.valueOf(speed));
        Log.i("Accuracy", String.valueOf(accuracy));

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
