package com.example.justin.silent_assassin;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
import com.google.android.gms.maps.GoogleMap.OnMyLocationClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Map;
import java.util.Random;

/*
* Sources used:
* https://developers.google.com/maps/documentation/android-api/location#code_samples
* https://github.com/googlemaps/android-samples/blob/master/ApiDemos/java/app/src/main/java/com/example/mapdemo/MyLocationDemoActivity.java
* https://developer.android.com/training/permissions/requesting.html*/


public class MapsActivity extends AppCompatActivity implements OnMyLocationButtonClickListener,
        OnMyLocationClickListener,
        OnMapReadyCallback,
        ActivityCompat.OnRequestPermissionsResultCallback {

    public void success()
    {
        AlertDialog.Builder build = new AlertDialog.Builder(this);
        build.setTitle("Successfull Attack! $200");
        AlertDialog alert1 = build.create();
        alert1.show();
    }

    public void Fail()
    {
        AlertDialog.Builder build = new AlertDialog.Builder(this);
        build.setTitle("UnSuccessfull Attack!");
        AlertDialog alert1 = build.create();
        alert1.show();
    }

    public void TargetAquired()
    {
        AlertDialog.Builder build = new AlertDialog.Builder(this);
        build.setTitle("Target Aquired!");
        AlertDialog alert1 = build.create();
        alert1.show();
    }

    public void FourHM()
    {
        AlertDialog.Builder build = new AlertDialog.Builder(this);
        build.setTitle("You are now able to attack!");
        AlertDialog alert1 = build.create();
        alert1.show();
    }

    public void NoT()
    {
        AlertDialog.Builder build = new AlertDialog.Builder(this);
        build.setTitle("You either do not have a target or are too far away");
        AlertDialog alert1 = build.create();
        alert1.show();
    }

    public void sButton(View v)
    {
        if(user.target.equals(""))
        {
            NoT();
        }

        else
        {

            Random r = new Random();

            int a = r.nextInt((100)+1)+10;

            if(a < 50)
            {
                Fail();
            }

            else
            {
                user.money = user.money + 200;
                success();
            }

            user.target = "";
            user.TLat = 0;
            user.TLong = 0;

            myRef.child(user.UserName).setValue(user);
            mMap.clear();

        }
    }


    private GoogleMap mMap;
    private static final int MY_LOCATION_REQUEST_CODE = 1;

    FirebaseDatabase database = FirebaseDatabase.getInstance(); //creating database
    DatabaseReference myRef = database.getReference("users");
    //String userID = myRef.push().getKey(); //getting the userID for the user
    User user = new User();
    //Button Attack = findViewById(R.id.Attack);

    //final AlertDialog.Builder build = new AlertDialog.Builder(this);

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Bundle u = getIntent().getExtras();
        user = u.getParcelable("user");

        //build.setTitle("Target Aquired!");

        //Attack.setOnClickListener(new View.OnClickListener()
        //{
            //@Override
            //public void onClick(View view)
            //{
                //if(user.target.equals(""))
                //{

                //}

                //else
                //{
                    //user.money = user.money + 200;
                    //myRef.child(user.UserName).setValue(user);

                //}
            //}
        //});


        if(user.target.equals(""))
        {
            Query query = myRef.orderByChild("isTargeted").equalTo("n");
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {

                        for (DataSnapshot uniqueKeySnapshot : dataSnapshot.getChildren()) {
                            User M = uniqueKeySnapshot.getValue(User.class);
                            if (!M.UserName.equals(user.UserName)) {
                                user.target = M.UserName;
                                user.TLat = M.Lat;
                                user.TLong = M.Long;
                            }
                        }

                        myRef.child(user.UserName).setValue(user);
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }

        else
        {
            Query query = myRef.orderByChild("UserName").equalTo(user.target);
            query.addListenerForSingleValueEvent(new ValueEventListener()
            {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot)
                {
                    if (!dataSnapshot.exists())
                    {
                        user.target = "";
                    }

                    else
                    {
                        User M = dataSnapshot.child(user.target).getValue(User.class);
                        user.TLat = M.Lat;
                        user.TLong = M.Long;
                        LatLng temp = new LatLng(M.Lat, M.Long);

                        if(user.TLat != 0 && user.TLong != 0)
                        {
                            mMap.addMarker(new MarkerOptions().position(temp));
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError)
                {

                }
            });
        }

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap map)
    {
        mMap = map;

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                Toast.makeText(this, "Location services are needed for this app to work", Toast.LENGTH_LONG).show();

            } else {

                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_LOCATION_REQUEST_CODE);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            mMap.setMyLocationEnabled(true);
        }
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        Location location = null;
        if (locationManager != null) {
            location = locationManager.getLastKnownLocation(locationManager
                    .getBestProvider(criteria, true));
        }

        WifiManager wm = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        String ip = null;
        if (wm != null) {
            ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());
        }
        Log.d("MapsActivity", "ip address = " + ip);

        // Write a message to the database
        double Lat = 0;
        double Long = 0;

        if (location != null)
        {
            user.Lat = location.getLatitude();
            user.Long = location.getLongitude();
        }

        //User user = new User(ip, Lat, Long,"asdf");
        //firebase doesn't like classes with constructor arguments, so I am restricted to
        //getting the doubles from the location class

        if(user.Lat != 0 && user.Long != 0)
        {
            myRef.child(user.UserName).setValue(user);
        }

        myRef.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {

                if(user.target.equals(""))
                {
                    Query query = myRef.orderByChild("isTargeted").equalTo("n");
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists())
                            {

                                for (DataSnapshot uniqueKeySnapshot : dataSnapshot.getChildren()) {
                                    User M = uniqueKeySnapshot.getValue(User.class);
                                    if (!M.UserName.equals(user.UserName)) {
                                        user.target = M.UserName;
                                        user.TLat = M.Lat;
                                        user.TLong = M.Long;
                                    }
                                }

                                if(!user.target.equals(""))
                                {
                                    TargetAquired();
                                    FourHM();
                                }

                                myRef.child(user.UserName).setValue(user);
                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }

                else
                {
                    Query query = myRef.orderByChild("UserName").equalTo(user.target);
                    query.addListenerForSingleValueEvent(new ValueEventListener()
                    {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot)
                        {
                            if (!dataSnapshot.exists())
                            {
                                user.target = "";
                            }

                            else
                            {
                                User M = dataSnapshot.child(user.target).getValue(User.class);
                                user.TLat = M.Lat;
                                user.TLong = M.Long;
                                LatLng temp = new LatLng(M.Lat, M.Long);
                                if(user.TLat != 0 && user.TLong != 0)
                                {
                                    mMap.addMarker(new MarkerOptions().position(temp));
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError)
                        {

                        }
                    });
                }
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                //String value = dataSnapshot.getValue(String.class);
                //for (DataSnapshot postSnapshot: dataSnapshot.getChildren())
                //{
                  //  // TODO: handle the post
                    //User change = postSnapshot.getValue(User.class);
                    //double Lat = change.Lat;
                    //double Long = change.Long;
                    //LatLng temp = new LatLng(Lat, Long);
                    //if(!postSnapshot.getKey().equals(user.UserName))
                    //{
                      //  mMap.addMarker(new MarkerOptions().position(temp));
                    //}
                    //Log.d("MapsActivity", "Something happened yo");
                //}
                //User change = dataSnapshot.getValue(User.class);
                //Location loc = dataSnapshot.getValue(Location.class);

            }

            @Override
            public void onCancelled(DatabaseError error)
            {
                // Failed to read value
                //Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
        //myRef.removeValue();

    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(this, "Current location:\n" + location, Toast.LENGTH_LONG).show();

    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_LOCATION_REQUEST_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                            ActivityCompat.checkSelfPermission(this,
                                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    mMap.setMyLocationEnabled(true);
                }

                else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, "Location services disabled", Toast.LENGTH_LONG).show();

                }
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }
}

