package com.example.habitize;
import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

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
import com.google.android.gms.maps.GoogleMap.OnCameraIdleListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
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
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class MapFragment extends Fragment implements OnMapReadyCallback {
    private static final String TAG = MapsActivity.class.getSimpleName();
    private GoogleMap map;
    private CameraPosition cameraPosition;
    TextView address;
    Button retryLocBTN, locSearchBTN, back;
    private Context thisContext;

    protected static final int REQUEST_CHECK_SETTINGS = 0x1;
    Marker currMarked;
    private SupportMapFragment mapFragment;
    // The entry point to the Places API.
    private PlacesClient placesClient;
    private AppCompatActivity activity;
    private UiSettings mUISettings;

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
    Location lastKnownLocation;

    // Keys for storing activity state.
    private static final String KEY_CAMERA_POSITION = "camera_position";
    private static final String KEY_LOCATION = "location";
    private scrollDisabler scrollController;
    // Keys for autocompletefragment and global variable
    AutocompleteSupportFragment autocompleteFragment;
    private static int AUTOCOMPLETE_REQUEST_CODE = 2;
    private TouchableWrapper mTouchWrapper;
    private boolean viewing = false;
    private Button deleteButton;


    public interface scrollDisabler{
        void disableScroll();
        void enableScroll();
    }

    public MapFragment() {
    }



    public Double getLat(){
        return lastKnownLocation.getLatitude();
    }
    public Double getLon(){
        return lastKnownLocation.getLongitude();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_maps,container,false);
        // Retrieve location and camera position from saved instance state.
        if (getArguments() != null) {
            double lon = (Double)getArguments().getSerializable("lon");
            double lat = (Double)getArguments().getSerializable("lat");
            lastKnownLocation = new Location("");
            lastKnownLocation.setLatitude(lat);
            lastKnownLocation.setLongitude(lon);
            viewing = true;
        }


        this.activity = (AppCompatActivity) view.getContext();
        scrollController = (scrollDisabler) activity;
        locSearchBTN = view.findViewById(R.id.initSearchBTN);
        address = view.findViewById(R.id.addressView);
        deleteButton = view.findViewById(R.id.deleteButton);

        if(viewing == true){
            locSearchBTN.setVisibility(View.GONE);
        }

        Places.initialize(activity, "AIzaSyBYB0fEQjiorItqGhF8RyD9GVV_z7qOF5c");
        placesClient = Places.createClient(this.activity);

        // Construct a FusedLocationProviderClient.
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this.activity);

        // Build the map.
        mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync( this);

        return view;

    }

    public void enableMapScroll(){
        mUISettings.setAllGesturesEnabled(true);
        map.setOnCameraMoveStartedListener(new GoogleMap.OnCameraMoveStartedListener() {
            @Override
            public void onCameraMoveStarted(int i) {
                scrollController.disableScroll();
            }
        });
        map.setOnCameraIdleListener(new OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                scrollController.enableScroll();
            }
        });
    }
    public void disableMapScroll(){
        mUISettings.setAllGesturesEnabled(false);
    }


    /**
     * Manipulates the map when it's available. This callback is triggered when the map is ready to be used.
     */
    @Override
    public void onMapReady(GoogleMap map) {

        this.map = map;
        mUISettings = map.getUiSettings();
        if(!viewing) {
            // Prompt the user for permission.
            createLocationRequest();

            // Turn on the My Location layer and the related control on the map.
            updateLocationUI();

            // Get the current location of the device and set the position of the map.
            getDeviceLocation();

            // if we are moving the camera we dont want to scroll the tabs

            // Listener for the location search button. When the button is clicked, construct the
            // autocomplete search bar. Set the fields to specify which types of place data to
            // return after the user has made a selection.
            locSearchBTN.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    List<Place.Field> placeFields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG);
                    // Start the auto complete intent
                    Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, placeFields).build(activity);
                    startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);
                }
            });
        }
        else{
            setLastKnownLocation();
        }
        disableMapScroll();

    }
    private void getDeviceLocation() {
        try {
            if (locationPermissionGranted) {
                @SuppressLint("MissingPermission")
                Task<Location> locationResult = fusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(this.activity, new OnCompleteListener<Location>() {
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
                            MarkerOptions markerOptions = new MarkerOptions();
                            markerOptions.position(new LatLng(lastKnownLocation.getLatitude(),
                                    lastKnownLocation.getLongitude()));
                            markerOptions.title("Current position");
                            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
                            currMarked = map.addMarker(markerOptions);
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
        // find the addressline
        Geocoder geocoder = new Geocoder(this.activity, Locale.getDefault());
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
        map.clear(); // remove all previous markers
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(lat);
        markerOptions.title("Desired position");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
        currMarked = map.addMarker(markerOptions);
        if (newPlace != null) {
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(lat.latitude,
                            lat.longitude), DEFAULT_ZOOM));
        }
        Location temp = new Location(LocationManager.FUSED_PROVIDER);
        temp.setLatitude(lat.latitude);
        temp.setLongitude(lat.longitude);
        setLocation(temp);
        lastKnownLocation = temp;
    }
    public void setLastKnownLocation(){
        map.clear();
        MarkerOptions markerOptions = new MarkerOptions();
        LatLng latLng = new LatLng(this.lastKnownLocation.getLatitude(),this.lastKnownLocation.getLongitude());
        markerOptions.position(latLng);
        markerOptions.title("Desired position");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
        currMarked = map.addMarker(markerOptions);
        setLocation(lastKnownLocation);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,DEFAULT_ZOOM));
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
        SettingsClient client = LocationServices.getSettingsClient(activity);
        if (ContextCompat.checkSelfPermission(activity,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            locationPermissionGranted = true;
            getDeviceLocation();}
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());


        // Handles the result of the request for location permissions
        task.addOnSuccessListener(activity, new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                // All location settings are satisfied. The client can initialize
                // location requests here.
                locationPermissionGranted = true;
                ActivityCompat.requestPermissions(activity,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
                updateLocationUI();

            }
        });

        task.addOnFailureListener(activity, e -> {
            if (e instanceof ResolvableApiException) {
                // Location settings are not satisfied, but this can be fixed
                // by showing the user a dialog.
                try {
                    // Show the dialog by calling startResolutionForResult(),
                    // and check the result in onActivityResult().
                    ResolvableApiException resolvable = (ResolvableApiException) e;
                    resolvable.startResolutionForResult(activity,
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
                case RESULT_OK:    // the user accepted permission to location services
                    retryLocBTN.setVisibility(View.GONE);
                    updateLocationUI();
                    locationPermissionGranted = true;
                    break;
                case Activity.RESULT_CANCELED:  // the user denied permission to location services
                    // create a retry button for the user to accept the permissions
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
                    Log.i(TAG, "Place: " + place.getName() + ", " + place.getId() + ", " + place.getLatLng());  // check if it shows
                    setNewLocation(place);
                    break;
                case AutocompleteActivity.RESULT_ERROR: // errro
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


    public class TouchableWrapper extends FrameLayout {
        public TouchableWrapper(Context context) {
            super(context);
        }

        @Override
        public boolean dispatchTouchEvent(MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    System.out.println("MAP TOUCHED");
                    return true;
                case MotionEvent.ACTION_UP:
                    System.out.println("MAP LET GO");
                    return false;
            }
            return super.dispatchTouchEvent(event);
        }
    }

}
