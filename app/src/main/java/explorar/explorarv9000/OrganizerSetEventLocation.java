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
import static explorar.explorarv9000.OrganizationLogin.loggedUser;

public class OrganizerSetEventLocation extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Marker marker;
    private LatLng markerPosition;
    private double markerPositionLat;
    private double markerPositionLong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.organiser_set_event_location);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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

    //TODO: Make the marker center itself on the screen and have the user drag the screen and not the marker!
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

        final Button organizer_create_event_location_button = (Button) findViewById(R.id.organiser_set_event_location_set_location_button);
        final DbCreation helper = new DbCreation(this);

        organizer_create_event_location_button.setOnClickListener(new View.OnClickListener() {
                                                                      @Override
                                                                      public void onClick(View view) {
                                                                          //set markerPosition to current position of marker
                                                                          markerPosition = marker.getPosition();
                                                                          markerPositionLat = markerPosition.latitude;
                                                                          markerPositionLong = markerPosition.longitude;
                                                                          Log.i("Michael", "markerPosition is " + markerPosition);
                                                                          String latitude = Double.toString(markerPositionLat);
                                                                          String longitude = Double.toString(markerPositionLong);
                                                                          if(view.getId() == (R.id.organiser_set_event_location_set_location_button)) {
                                                                              if(latitude.equals("") || longitude.equals("")) {
                                                                                  Toast.makeText(getApplicationContext(), "Choose Event Location", Toast.LENGTH_LONG).show();
                                                                                  return;
                                                                              } else {
                                                                                  helper.insertEvent(loggedUser, "eventitle", "eventlocation", "eventdate", "eventstarttime", "endendtime", "eventprice", "eventdescription", latitude, longitude);
                                                                                  Toast.makeText(getApplicationContext(), "Event Successfully Created", Toast.LENGTH_LONG).show();
                                                                                  Intent openNextActivityIntent = new Intent(OrganizerSetEventLocation.this, OrganizerHome.class);
                                                                                  openNextActivityIntent.putExtra(Intent.EXTRA_TEXT, markerPositionLat);
                                                                                  openNextActivityIntent.putExtra(Intent.EXTRA_TEXT, markerPositionLong);
                                                                                  startActivity(openNextActivityIntent);
                                                                              }
                                                                          }}});}}

