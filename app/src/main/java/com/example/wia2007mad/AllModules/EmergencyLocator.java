package com.example.wia2007mad.AllModules;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.wia2007mad.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

public class EmergencyLocator extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{

    boolean isPermissionGranted;
    GoogleMap mGoogleMap;
    FloatingActionButton fab;
    private FusedLocationProviderClient mLocationClient;
    ImageButton BtnBackEmergency;
    double currentLat = 0, currentLong = 0;
    Spinner SPCategory;
    Button BtnFind;
    SupportMapFragment supportMapFragment;

    private int GPS_REQUEST_CODE = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_locator);

        BtnBackEmergency = findViewById(R.id.BtnBackLocator);
        fab = findViewById(R.id.fab);
        SPCategory = findViewById(R.id.SPCategory);
        BtnFind = findViewById(R.id.BtnFind);

        //"Clinic","Emergency","24 Hours Clinic","First Aid"
        String[] placeCategory = {"Police", "Hospital","Drugstore","Doctor","Dentist","Bank","light_rail_station","mosque","pharmacy","physiotherapist","train_station"};
        String[] showingplaceCategory={"Police", "Hospital","Medicine Store","Doctor","Dentist","Bank","LRT","Mosque","Pharmacy","Physiotherapist","Train Station"};
        SPCategory.setAdapter(new ArrayAdapter<>(EmergencyLocator.this,
                android.R.layout.simple_spinner_dropdown_item, showingplaceCategory));

        checkMyPermission();

        initMap();

        mLocationClient = LocationServices.getFusedLocationProviderClient(this);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCurrLoc();
            }
        });

        BtnBackEmergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                //startActivity(new Intent(EmergencyLocator.this, HealthHome.class));
            }
        });

        BtnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Create a variable to hold the selected item position of Spinner
                int SPLoc = SPCategory.getSelectedItemPosition();

                //Create the variable to hold the string that will be send to the
                //API later to retrieve the nearby places
                //location is obtained when "getCurrentLocation()" is called just now
                //radius is the size of the area counting from your current location
                //type is the type listed in spinner based on user selection ("restaurant", ...)
                //key is the API key in the Google Cloud Developer Platform (Get Public Key)
                //More information: https://developers.google.com/maps/documentation/places/web-service/search-nearby
                String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json"
                        + "?location=" + currentLat + "," + currentLong
                        + "&radius=25000"
                        + "&type=" + placeCategory[SPLoc].toLowerCase()
                        + "&key=" + getResources().getString(R.string.google_map_key);
                //Log.i("log", url);
                // Call to execute PlaceTask
                new PlaceTask().execute(url);
            }
        });
    }


    private void initMap() {
        if(isPermissionGranted){
            if(isGPSenable()) {
                supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_view);
                supportMapFragment.getMapAsync(this);
            }
        }
    }

    private boolean isGPSenable(){
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        boolean providerEnable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if(providerEnable){
            return true;
        }else {
            AlertDialog alertDialog = new AlertDialog.Builder(this)
                    .setTitle("GPS permission")
                    .setMessage("GPS is required for this app tp work. Please enable GPS.")
                    .setPositiveButton("Yes", ((dialogInterface, i) -> {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivityForResult(intent, GPS_REQUEST_CODE);
                    }))
                    .setCancelable(false)
                    .show();
        }
        return false;
    }

    @SuppressLint("MissingPermission")
    private void getCurrLoc() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Request location permissions if needed
            return;
        }

        mLocationClient.getLastLocation().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Location location = task.getResult();
                if (location != null) {
                    currentLat = location.getLatitude(); // Update currentLat with latitude
                    currentLong = location.getLongitude(); // Update currentLong with longitude
                    gotoLocation(location.getLatitude(), location.getLongitude());
                    addMarkerToCurrentLocation(location.getLatitude(),location.getLongitude());
                } else {
                    // Handle the case when location is null
                    Toast.makeText(this, "Location not available", Toast.LENGTH_SHORT).show();
                }
            } else {
                // Handle the case when the task is not successful
                Toast.makeText(this, "Failed to get location", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addMarkerToCurrentLocation(double latitude, double longitude) {
        LatLng latLng = new LatLng(latitude, longitude);

        // Create a marker options object
        MarkerOptions markerOptions = new MarkerOptions()
                .position(latLng)
                .title("Current Location"); // You can set a title for the marker

        // Add the marker to the map
        mGoogleMap.addMarker(markerOptions);
    }

    private void gotoLocation(double latitude, double longitude) {
        LatLng latLng = new LatLng(latitude,longitude);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng,13);
        mGoogleMap.moveCamera(cameraUpdate);
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }


    private void checkMyPermission(){

        Dexter.withContext(this).withPermission(Manifest.permission.ACCESS_FINE_LOCATION).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                Toast.makeText(EmergencyLocator.this, "Permission Granted", Toast.LENGTH_SHORT).show();
                isPermissionGranted = true;
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(),"");
                intent.setData(uri);
                startActivity(intent);
            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();
            }
        }).check();
    }



    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mGoogleMap = googleMap;
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==GPS_REQUEST_CODE){
            LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

            boolean providerEnable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            if(providerEnable){
                Toast.makeText(this,"GPS is enabled", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "GPS is not enabled", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private String downloadUrl(String string) throws IOException {
        URL url = new URL(string);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.connect();
        InputStream stream = connection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        StringBuilder builder = new StringBuilder();
        String line = "";
        while((line = reader.readLine())!= null){
            builder.append(line);
        }
        String data = builder.toString();
        reader.close();
        return data;
    }

    private class PlaceTask extends AsyncTask<String,Integer,String> {
        @Override
        protected String doInBackground(String... strings) {
            String data = "";
            try {
                data = downloadUrl(strings[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return data;
        }

        @Override
        protected void onPostExecute(String s) {
            //super.onPostExecute(s);
            new ParserTask().execute(s);
        }
    }

    private class ParserTask extends AsyncTask<String,Integer, List<HashMap<String,String>>> {
        @Override
        protected List<HashMap<String, String>> doInBackground(String... strings) {
            JsonParser jsonParser = new JsonParser();
            List<HashMap<String,String>> mapList = null;
            JSONObject object = null;
            try {
                object = new JSONObject(strings[0]);
                mapList = jsonParser.parseResult(object);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return mapList;
        }

        //Get back the cleaned value
        //And placed the marker on nearby places
        @Override
        protected void onPostExecute(List<HashMap<String, String>> hashMaps) {
            //super.onPostExecute(hashMaps);
            mGoogleMap.clear();
            for (int i = 0; i < hashMaps.size(); i++){
                HashMap<String,String> hashMapList = hashMaps.get(i);
                double lat = Double.parseDouble(hashMapList.get("lat"));
                double lng = Double.parseDouble(hashMapList.get("lng"));
                String name = hashMapList.get("name");
                LatLng latlng = new LatLng(lat, lng);
                MarkerOptions options = new MarkerOptions();
                options.position(latlng);
                options.title(name);
                mGoogleMap.addMarker(options);
            }
        }
    }


}
