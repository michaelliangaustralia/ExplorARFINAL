package explorar.explorarv9000;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Arrays;

import model.Event;

import static explorar.explorarv9000.OrganizationLogin.loggedUser;

/**
 * Created by carregliu on 15/10/2017.
 */

public class crudEvent extends AppCompatActivity implements android.view.View.OnClickListener {
    Button btnSave , btnDelete;
    Button btnClose;
    EditText eventTitle, eventDate, eventStartTime, eventEndTime, eventLocation, eventPrice, eventDescription;
    public static int eventID;

    private String dataEventTitle;
    private String dataEventDate;
    private String dataEventStartTime;
    private String dataEventEndTime;
    private String dataEventLocation;
    private String dataEventPrice;
    private String dataEventDescription;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_events_details);

        //Assign buttons
        btnSave = (Button)findViewById(R.id.btnSetLocation);
        btnDelete = (Button)findViewById(R.id.btnDelete);
        btnClose = (Button)findViewById(R.id.btnClose);

        //Assign edit textviews
        eventTitle = (EditText) findViewById(R.id.event_title);
        eventDate = (EditText) findViewById(R.id.event_date);
        eventStartTime = (EditText) findViewById(R.id.event_starttime);
        eventEndTime = (EditText) findViewById(R.id.event_endtime);
        eventLocation = (EditText) findViewById(R.id.event_location);
        eventPrice = (EditText) findViewById(R.id.editText);
        eventDescription = (EditText) findViewById(R.id.editText7);

        //set listeners
        btnSave.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnClose.setOnClickListener(this);

        //?

        /*
        Get intent
         */
        Intent intentThatStartedActivity = getIntent();

        if (intentThatStartedActivity.hasExtra(Intent.EXTRA_TEXT)) {
            eventID = Integer.parseInt(intentThatStartedActivity.getStringExtra(Intent.EXTRA_TEXT));
            Log.i("Michael", "eventID is " + eventID);
        }

//        //Start repo
//        EventRepo repo = new EventRepo(this);
//        Event event;
//
//        event = repo.getEventbyID(eventID);
//
//        eventName.setText(event.eName);
//        eventDate.setText(event.eDate);
//        eventStartTime.setText(event.eStartTime);
//        eventEndTime.setText(event.eEndTime);
//        eventLocation.setText(event.eLocation);
//        eventPrice.setText(event.ePrice);
//        eventDescription.setText(event.eDesc);

    }

    @Override
    public void onClick(View view) {


        //Assign text to edit text views
        dataEventTitle = eventTitle.getText().toString();
        dataEventDate = eventDate.getText().toString();
        dataEventStartTime = eventStartTime.getText().toString();
        dataEventEndTime = eventEndTime.getText().toString();
        dataEventLocation = eventLocation.getText().toString();
        dataEventPrice = eventPrice.getText().toString();
        dataEventDescription = eventDescription.getText().toString();

        //Button click
        if (view == findViewById(R.id.btnSetLocation)) {

            String[] eventDataArray = new String[] {loggedUser, dataEventTitle, dataEventDate, dataEventStartTime, dataEventEndTime, dataEventLocation, dataEventPrice, dataEventDescription};
            Log.i("Michael - CRUD Event", "eventDataArray is " + Arrays.toString(eventDataArray));

            /*
            intent
             */

            //intiialise intent
            Intent intent = new Intent(crudEvent.this, OrganizerCRUDSetEventLocation.class);

            //putextra
            intent.putExtra(Intent.EXTRA_TEXT,eventDataArray);
//            intent.putExtra("eventID",eventID);

            //Start intent
            startActivity(intent);

//
//
//        if (view == findViewById(R.id.btnSave)) {
//
//
//            EventRepo repo = new EventRepo(this);
//            Event event = new Event();
//
//
//            event.eName=eventName.getText().toString();
//            event.eDate=eventDate.getText().toString();
//            event.eStartTime=eventStartTime.getText().toString();
//            event.eEndTime=eventEndTime.getText().toString();
//            event.eLocation=eventLocation.getText().toString();
//            event.ePrice=eventPrice.getText().toString();
//            event.eDesc=eventDescription.getText().toString();
//            event.eID=event_ID;
//
//            if(event_ID==0) {
//                event_ID = repo.insert(event);
//                Toast.makeText(this, "New Event Insert", Toast.LENGTH_SHORT).show();
//            } else {
//                repo.update(event);
//                Toast.makeText(this, "Event List Updated", Toast.LENGTH_SHORT).show();
//            }

            //Delete button
        } else if (view == findViewById(R.id.btnDelete)) {
            final DbCreation db = new DbCreation(this);
            db.deleteRow(eventID);

            Toast.makeText(this, "Event deleted", Toast.LENGTH_SHORT);
            finish();
            //Close button
        } else if (view == findViewById(R.id.btnClose)) {
            finish();
        }
    }
}
