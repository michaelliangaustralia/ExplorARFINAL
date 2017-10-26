package explorar.explorarv9000;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Arrays;

import static explorar.explorarv9000.OrganizationLogin.loggedUser;

public class OrganizerCRUDSetEventLocation extends FragmentActivity implements OnMapReadyCallback{

    Button organizer_create_event_location_button;

    private String[] eventDataArray;
    private GoogleMap mMap;
    private Marker marker;
    private LatLng markerPosition;
    private double markerPositionLat;
    private double markerPositionLong;

    private String hostOrg;
    private String eventTitle;
    private String eventDate;
    private String eventStartTime;
    private String eventEndTime;
    private String eventLocation;
    private String eventPrice;
    private String eventDescription;

    private Double latitude;
    private Double longitude;

    private int eventID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.organiser_set_event_location);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        /*
        Intents
         */

        //Intent: Get Intent
        Intent intentThatStartedActivity = getIntent();

        //Intent: Get Information that was packaged in it
        if (intentThatStartedActivity.hasExtra(Intent.EXTRA_TEXT)) {
            eventDataArray = intentThatStartedActivity.getStringArrayExtra(Intent.EXTRA_TEXT);
            Log.i("Michael", "eventDataArray is " + Arrays.toString(eventDataArray));
        }


        /*
        eventId
         */

        eventID = crudEvent.eventID;

//        //Intent: Get Information that was packaged in it
//        else if (intentThatStartedActivity.hasExtra("eventID")) {
//            eventID = intentThatStartedActivity.getIntExtra("eventID");
//            Log.i("Michael", "eventDataArray is " + Arrays.toString(eventDataArray));
//        }


        /*
        Assign intent data to variables
         */

        hostOrg = eventDataArray[0];
        eventTitle = eventDataArray[1];
        eventDate = eventDataArray[2];
        eventStartTime = eventDataArray[3];
        eventEndTime = eventDataArray[4];
        eventLocation = eventDataArray[5];
        eventPrice = eventDataArray[6];
        eventDescription = eventDataArray[7];

        Log.i("Michael", "hostOrg is " + hostOrg);
        Log.i("Michael", "eventTitle is " + eventTitle);
        Log.i("Michael", "eventDate is " + eventDate);
        Log.i("Michael", "eventStartTime is " + eventStartTime);
        Log.i("Michael", "eventEndTime is " + eventEndTime);
        Log.i("Michael", "eventPrice is " + eventPrice);
        Log.i("Michael", "eventDescription is " + eventDescription);

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

    //TODO: Make the marker center itself on the screen and have the user drag the screen and not the marker
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng unswLatLng = new LatLng(-33.917423, 151.229229);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(unswLatLng));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(12.0f));

        // Add a marker in Sydney and move the camera
        marker = mMap.addMarker(new MarkerOptions()
                .position(unswLatLng)
                .draggable(true)
        );

        /*
        Create the button
         */

        organizer_create_event_location_button = (Button) findViewById(R.id.organiser_set_event_location_set_location_button);
        final DbCreation helper = new DbCreation(this);



        organizer_create_event_location_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //set markerPosition to current position of marker

                markerPosition = marker.getPosition();
                markerPositionLat = markerPosition.latitude;
                markerPositionLong = markerPosition.longitude;

                latitude = markerPositionLat;
                longitude = markerPositionLong;

                Log.i("Michael", "latitude is " + latitude);
                Log.i("Michael", "longitude is " + longitude);

                if(view.getId() == (R.id.organiser_set_event_location_set_location_button)) {
//                    if(latitude.equals("") || longitude.equals("")) {
//                        Toast.makeText(getApplicationContext(), "Choose Event Location", Toast.LENGTH_LONG).show();
//                        return;
//                    }
//
//                    else {

                        //Delete previous event instance from database
                        helper.deleteRow(eventID);

                        Log.i("Michael-crudsetlocation", "eventID is " + eventID);
                        Log.i("Michael-crudsetlocation", "deleteRow executed");

                        //Add data into database
                        helper.insertEvent(loggedUser, eventTitle, eventLocation, eventDate, eventStartTime, eventEndTime, eventPrice, eventDescription, latitude, longitude);
                        Toast.makeText(getApplicationContext(), "Event Successfully Created", Toast.LENGTH_LONG).show();

                        //Start Intent
                        Intent openNextActivityIntent = new Intent(OrganizerCRUDSetEventLocation.this, EventsList.class);
                        startActivity(openNextActivityIntent);

                }}});


    }



}

