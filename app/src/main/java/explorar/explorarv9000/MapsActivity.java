package explorar.explorarv9000;

import android.Manifest;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.maps.android.ui.IconGenerator;


public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private SQLiteDatabase mDb;
    private Cursor cursor;
    private double markerLat;
    private double markerLong;
    private Marker marker;
    private LatLng markerLatLng;
    private Location mCurrentLocation;


    private static final float DEFAULT_ZOOM = 16.3f;
    public static final String TAG = MapsActivity.class.getSimpleName();
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private static final int REQUEST_PERMISSION_ACCESS_FINE_LOCATION = 1000; //This is an abitrary int that is used in onRequestPermissionsResult for handling permission results
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        /*
        Action bar
         */
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.action_bar);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        View view = getSupportActionBar().getCustomView();
        TextView textView = (TextView) findViewById(R.id.toolbar_title);
        textView.setText("Events Happening Now");

        ImageButton imageButton = (ImageButton) view.findViewById(R.id.action_bar_back);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        // Create the LocationRequest object
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(1 * 1000)        // 1 second, in milliseconds
                .setFastestInterval(1 * 1000); // 1 second, in milliseconds


        //Instantiate mGoogleApiClient
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        //initialiseDB
        initialiseDB();

        //insertFakeData
        insertFakeData();

        //initiliaseCursor
        initialiseCursor();
    }

    @Override //This handles the permission selection by user
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) { //LEARNING: @NonNull is just a marker notation and has no tangible attributes.
        switch (requestCode) {
            case REQUEST_PERMISSION_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) { // permission was granted, yay! Do the location-related task you need to do.

                    //Check if permissions are granted
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        //Not granted - return
                        return;
                    } else {
                        //Granted - Enable location sharing
                        mMap.setMyLocationEnabled(true);
                    }

                } else { // permission denied, boo! Disable the functionality that depends on this permission.

                    //Disable location sharing
                    mMap.setMyLocationEnabled(false);

                }
                return;
            }
        }
    }

    /**
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        /*
        Set up Google Map
         */

        //set mMap to GoogleMap view
        mMap = googleMap;
        Log.i(TAG, "set mMap to GoogleMap view");


        //set default camera to UNSWlatlng and move the camera
//        LatLng unswLatLng = new LatLng(-33.917378, 151.230205);
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(unswLatLng));
//        mMap.moveCamera(CameraUpdateFactory.zoomTo(14.0f));

        /*
        Set up onClickListeners
         */
        mMap.setOnMyLocationButtonClickListener(this);

        //setting up OnMarkerClickListener
        mMap.setOnMarkerClickListener(this);

        //EnablemyLocation
        enableMyLocation();

        /*
        Markers
         */

        //Markers: Initiate custom iconfactory
        IconGenerator iconFactory = new IconGenerator(this);

        //Markers: Set cursor to position 0
        cursor.moveToPosition(0);
        //Markers: Create markers by iterating through database rows
        while (cursor.isAfterLast() == false) {
            //cursor get required data
            markerLat = cursor.getDouble(cursor.getColumnIndex(DbContracts.eventsDBentry.COLUMN_LATITUDE_EVENT));
            markerLong = cursor.getDouble(cursor.getColumnIndex(DbContracts.eventsDBentry.COLUMN_LONGITUDE_EVENT));
            String eventName = cursor.getString(cursor.getColumnIndex(DbContracts.eventsDBentry.COLUMN_NAME_EVENT));
            //Create LatLng variable
            markerLatLng = new LatLng(markerLat, markerLong);
            //Plot Marker
            marker = mMap.addMarker(new MarkerOptions()
                    .position(markerLatLng)
                    .icon(BitmapDescriptorFactory.fromBitmap(iconFactory.makeIcon(eventName)))
                    .anchor(iconFactory.getAnchorU(), iconFactory.getAnchorV()));
            //Store the position of the cursor in the marker as a data object - we will need this later on for pulling more information about it in EventDetailsActivity
            marker.setTag(cursor.getPosition());
            //showInfoWindow
            marker.showInfoWindow();
            //move cursor to next position
            Log.i("Michael", "The current cursorPosition is " + cursor.getPosition());
            cursor.moveToNext();
            Log.i("Michael", "The next cursorPosition is " + cursor.getPosition());
        }

        /*
        Location Button Click Toast
         */

        //Setting Toast to appear on location button click
        mMap.setOnMyLocationButtonClickListener(this);

    }

    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
        } else if (mMap != null) {
            // Access to the location has been granted to the app.
            mMap.setMyLocationEnabled(true);
        }
    }

    @Override
    public boolean onMarkerClick(final Marker marker) {
        //Create Intent to open example event A event details activity
        Intent openEventDetailsIntent = new Intent(MapsActivity.this, EventDetailsActivity.class);

        //Put cursor data into the Intent envelope
        openEventDetailsIntent.putExtra(Intent.EXTRA_TEXT, marker.getTag().toString());

        //Start the intent activity
        startActivity(openEventDetailsIntent);

        return false;
    }

    @Override  //Defines what happens when you click the location button
    public boolean onMyLocationButtonClick() {
        return false;

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.i(TAG, "Location services connected.");
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
            return;
        } else {
            if (LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient) != null) {
                mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                Log.i(TAG, "Last Known Location is " + LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient));
                //            if (location == null) {
                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
//            } else {
//                handleNewLocation(location);
//            }
                handleNewLocation(mCurrentLocation);
            } else {
                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
                Toast.makeText(this, "getLastKnownLocation was null", Toast.LENGTH_LONG);
            }


        }
    }

//    //Request Location Update TODO
//    private void startLocationUpdates() {
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
//                    Manifest.permission.ACCESS_FINE_LOCATION, true);
//            return;
//        }
//        mFusedLocationClient.requestLocationUpdates(mLocationRequest,
//                mLocationCallback,
//                null);
//    }

    private void handleNewLocation(Location location) {
        Log.d(TAG, location.toString());
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(location.getLatitude(),
                        location.getLongitude()),
                DEFAULT_ZOOM
        ));

    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "Location services suspended. Please reconnect.");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST);
            } catch (IntentSender.SendIntentException e) {
                e.printStackTrace();
            }
        } else {
            Log.i(TAG, "Location services connection failed with code " + connectionResult.getErrorCode());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mGoogleApiClient.connect();
        //Request Location Update TODO
//        if (mRequestingLocationUpdates) {
//            startLocationUpdates();
//        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        handleNewLocation(location);
    }

    private void initialiseDB(){
        //DB: Create helper instance
        DbCreation dbCreation = new DbCreation(this);

        //DB: Get readable reference of database and store it in mDb
        mDb = dbCreation.getWritableDatabase();
        Log.i("Michael", "WritableDatabase has been created");
    }

    private void insertFakeData(){
        //DB: Insert Fake Data
        DBInsertFakeData.insertFakeData(mDb);
        Log.i("Michael", "Fake Data has been inserted");
    }

    private void initialiseCursor(){
        //DB: call getEventName() and put it in a cursor variable
        cursor = mDb.rawQuery("Select * from " + DbContracts.eventsDBentry.TABLE_NAME + ";",null);
        Log.i("Michael", "DB data has been inserted into cursor");
    }
}


/*

--in oncreate--
        //Get Current Location Settings - https://developer.android.com/training/location/change-location-settings.html#prompt
//        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
//                .addLocationRequest(mLocationRequest);
//
//        SettingsClient client = LocationServices.getSettingsClient(this);
//        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());
//
//        //Prompt the User to Change Location Settings - https://developer.android.com/training/location/change-location-settings.html#prompt
//        task.addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
//            @Override
//            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
//                // All location settings are satisfied. The client can initialize location requests here.
//                // ...
//            }
//        });
//
//        task.addOnFailureListener(this, new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                int statusCode = ((ApiException) e).getStatusCode();
//                switch (statusCode) {
//                    case CommonStatusCodes.RESOLUTION_REQUIRED:
//                        // Location settings are not satisfied, but this can be fixed
//                        // by showing the user a dialog.
//                        try {
//                            // Show the dialog by calling startResolutionForResult(),
//                            // and check the result in onActivityResult().
//                            ResolvableApiException resolvable = (ResolvableApiException) e;
//                            resolvable.startResolutionForResult(MapsActivity.this,
//                                    REQUEST_CHECK_SETTINGS);
//                        } catch (IntentSender.SendIntentException sendEx) {
//                            // Ignore the error.
//                        }
//                        break;
//                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
//                        // Location settings are not satisfied. However, we have no way
//                        // to fix the settings so we won't show the dialog.
//                        break;
//                }
//            }
//        });


 */