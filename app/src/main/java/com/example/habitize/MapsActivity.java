// Copyright 2020 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.example.habitize;
import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;


import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * An activity that displays a map showing the place at the device's current location.*/
public class MapsActivity extends AppCompatActivity
        implements OnMapReadyCallback {

    private static final String TAG = MapsActivity.class.getSimpleName();
    private GoogleMap map;
    private CameraPosition cameraPosition;
    TextView address;
    Button retryLocBTN, locSearchBTN, back;
    protected static final int REQUEST_CHECK_SETTINGS = 0x1;

    // The entry point to the Places API.
    private PlacesClient placesClient;

    // The entry point to the Fused Location Provider.
    private FusedLocationProviderClient fusedLocationProviderClient;

    // A default location (Sydney, Australia) and default zoom to use when location permission is
    // not granted.
    private final LatLng defaultLocation = new LatLng(-33.8523341, 151.2106085);
    private static final int DEFAULT_ZOOM = 15;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private boolean locationPermissionGranted;

    // The geographical location where the device is currently located. That is, the last-known
    // location retrieved by the Fused Location Provider.
    private Location lastKnownLocation;

    // Keys for storing activity state.
    private static final String KEY_CAMERA_POSITION = "camera_position";
    private static final String KEY_LOCATION = "location";

    // Keys for autocompletefragment and global variable
    AutocompleteSupportFragment autocompleteFragment;
    private static int AUTOCOMPLETE_REQUEST_CODE = 2;

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Retrieve location and camera position from saved instance state.
        if (savedInstanceState != null) {
            lastKnownLocation = savedInstanceState.getParcelable(KEY_LOCATION);
            cameraPosition = savedInstanceState.getParcelable(KEY_CAMERA_POSITION);
        }

        // Retrieve the content view that renders the map.
        setContentView(R.layout.activity_maps);

        // Initialize button
        locSearchBTN = findViewById(R.id.initSearchBTN);
        back = findViewById(R.id.backBTN);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Construct a PlacesClient
        Places.initialize(getApplicationContext(), "@string/google_maps_key");
        placesClient = Places.createClient(this);

        // Construct a FusedLocationProviderClient.
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        // Build the map.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * This method checks if map is not null, then pass/add in the intent the last location and the last camera location
     * @param outState
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (map != null) {
            outState.putParcelable(KEY_CAMERA_POSITION, map.getCameraPosition());
            outState.putParcelable(KEY_LOCATION, lastKnownLocation);
        }
        super.onSaveInstanceState(outState);
    }


    /**
     * Manipulates the map when it's available. This callback is triggered when the map is ready to be used.
     */
    @Override
    public void onMapReady(GoogleMap map) {
        this.map = map;

        // Use a custom info window adapter to handle multiple lines of text in the
        // info window contents.
        this.map.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

            @Override
            // Return null here, so that getInfoContents() is called next.
            public View getInfoWindow(Marker arg0) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                // Inflate the search stuff later
                View infoWindow = getLayoutInflater().inflate(R.layout.activity_maps, (FrameLayout) findViewById(R.id.map), false);

                return infoWindow;
            }
        });


        // Prompt the user for permission.
        createLocationRequest();

        // Turn on the My Location layer and the related control on the map.
        updateLocationUI();

        // Get the current location of the device and set the position of the map.
        getDeviceLocation();

        /**
         * Listener for the location search button. When the button is clicked, construct the
         * autocomplete search bar. Set the fields to specify which types of place data to
         * return after the user has made a selection.
         */
        locSearchBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Place.Field> placeFields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG);
                // Start the auto complete intent
                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN,placeFields).build(MapsActivity.this);
                startActivityForResult(intent,AUTOCOMPLETE_REQUEST_CODE);
            }
        });
    }

    /**
     * // Get the best and most recent location of the device and the camera's position, which may be null in rare cases when a location is not available.
     */
    private void getDeviceLocation() {
        try {
            if (locationPermissionGranted) {
                @SuppressLint("MissingPermission")
                Task<Location> locationResult = fusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) {
                            // Set the map's camera position to the current location of the device.
                            lastKnownLocation = task.getResult();
                            if (lastKnownLocation != null) {
                                map.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                        new LatLng(lastKnownLocation.getLatitude(),
                                                lastKnownLocation.getLongitude()), DEFAULT_ZOOM));
                            }
                            setLocation(lastKnownLocation);
                        }
                    }
                });
            } else {
                // if no position is found, set a default.
                Log.d(TAG, "Current location is null. Using defaults.");
                createLocationRequest();
                map.moveCamera(CameraUpdateFactory
                        .newLatLngZoom(defaultLocation, DEFAULT_ZOOM));
                map.getUiSettings().setMyLocationButtonEnabled(false);
                }
            } catch(SecurityException e){
                Log.e("Exception: %s", e.getMessage(), e);
            }
    }

    /**
     * This method takes the current location of the user and display the addressline on the screen.
     * @param lastKnownLoc is a Location object that contains the lat and long values of the current
     *                     position of the user.
     */
    public void setLocation(Location lastKnownLoc){
        address = findViewById(R.id.addressView);
        // find the addressline
        Geocoder geocoder = new Geocoder(MapsActivity.this, Locale.getDefault());
        try {
            // addressList just takes one value at most so it is safe to use the index 0 to get the result
            List<Address> addressList = geocoder.getFromLocation(lastKnownLoc.getLatitude(), lastKnownLoc.getLongitude(), 1);
            address.setText(addressList.get(0).getAddressLine(0).toString());
        } catch (IOException e) {
            e.printStackTrace();
            address.setText("Location not found. Try again. Error:"+e.getMessage());
        }
        return;
    }

    /**
     * Updates the map's UI settings based on whether the user has granted location permission.
     */
    @SuppressLint("MissingPermission")
    private void updateLocationUI() {
        if (map == null) {
            return;
        }
        try {
            if (locationPermissionGranted) {
                // if location is granted permission, make the location search button accessible
                map.setMyLocationEnabled(true);
                map.getUiSettings().setMyLocationButtonEnabled(true);
                locSearchBTN.setVisibility(View.VISIBLE);

            } else {
                // if location is not granted permission, prompt the user to grant it again.
                map.setMyLocationEnabled(false);
                map.getUiSettings().setMyLocationButtonEnabled(false);
                lastKnownLocation = null;
                createLocationRequest();
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    /**
     * This method takes the new user input location from the search bar and handles it here.
     * The inputted location will be find in the maps and get its corresponding the addressline.
     * @param newPlace
     */
    public void setNewLocation(Place newPlace){
        // set the new location that the user inputted to be the new location of the habit
        LatLng lat = newPlace.getLatLng();
        if (newPlace != null) {
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(lat.latitude,
                            lat.longitude), DEFAULT_ZOOM));
        }
        Location temp = new Location(LocationManager.FUSED_PROVIDER);
        temp.setLatitude(lat.latitude);
        temp.setLongitude(lat.longitude);
        setLocation(temp);

    }

    /** If the user opened the app for the first time, a permission request is displayed
     * to enable location services. The user only needs to accept once. Since it will only
     *  show up if the app doesn't have permission to access the location services.  **/
    protected void createLocationRequest() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);

        // Check whether the current location settings are satisfied
        SettingsClient client = LocationServices.getSettingsClient(this);
        if (ContextCompat.checkSelfPermission(MapsActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                locationPermissionGranted = true;
                getDeviceLocation();}
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());


        // Handles the result of the request for location permissions
        task.addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                // All location settings are satisfied. The client can initialize
                // location requests here.
                locationPermissionGranted = true;
                ActivityCompat.requestPermissions(MapsActivity.this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
                updateLocationUI();

            }
        });

        task.addOnFailureListener(this, e -> {
            if (e instanceof ResolvableApiException) {
                // Location settings are not satisfied, but this can be fixed
                // by showing the user a dialog.
                try {
                    // Show the dialog by calling startResolutionForResult(),
                    // and check the result in onActivityResult().
                    ResolvableApiException resolvable = (ResolvableApiException) e;
                    resolvable.startResolutionForResult(MapsActivity.this,
                            REQUEST_CHECK_SETTINGS);
                } catch (IntentSender.SendIntentException sendEx) {
                    // Ignore the error.
                }
            }
        });
    }
    @Override
    // Handles if the user turned off the location services
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // This method handles what will happen when the user has selected a place

        if (requestCode == REQUEST_CHECK_SETTINGS) {
            // handle the result and check if the location is granted
            switch (resultCode) {
                case Activity.RESULT_OK:    // the user accepted permission to location services
                    retryLocBTN.setVisibility(View.GONE);
                    updateLocationUI();
                    locationPermissionGranted = true;
                    break;
                case Activity.RESULT_CANCELED:  // the user denied permission to location services
                    Toast.makeText(MapsActivity.this, "Location Services is required to access current location", Toast.LENGTH_SHORT).show();
                    // create a retry button for the user to accept the permissions
                    address = findViewById(R.id.addressView);
                    retryLocBTN = findViewById(R.id.retryLocationBTN);
                    address.setText("Location cannot be found. Please turn on Location Services Permission");
                    retryLocBTN.setVisibility(View.VISIBLE);
                    break;
            }
            retryLocBTN.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    createLocationRequest();    // Prompt user to accept the permission again
                }
            });
        }
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE){
            // handle the result for the search bar on maps
            switch (resultCode){
                case RESULT_OK:    // success
                    // Initialize place
                    Place place = Autocomplete.getPlaceFromIntent(data);
                    Log.i(TAG, "Place: " + place.getName() + ", " + place.getId() + ", " + place.getLatLng());
                    setNewLocation(place);
                    break;
                case AutocompleteActivity.RESULT_ERROR: // errro
                    Toast.makeText(this,"An error has occured.",Toast.LENGTH_SHORT);
                    Status status = Autocomplete.getStatusFromIntent(data);
                    Log.i(TAG, status.getStatusMessage());
                    break;
                case AutocompleteActivity.RESULT_CANCELED:
                    // the user canceled the operation. Just ignore.
            }
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
