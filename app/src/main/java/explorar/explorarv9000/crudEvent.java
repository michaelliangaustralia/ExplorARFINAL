package explorar.explorarv9000;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import model.Event;

/**
 * Created by carregliu on 15/10/2017.
 */

public class crudEvent extends AppCompatActivity implements android.view.View.OnClickListener {
    Button btnSave , btnDelete;
    Button btnClose;
    EditText eventName, eventDate, eventStartTime, eventEndTime, eventLocation, eventPrice, eventDescription;
    private int event_ID = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_events_details);
        btnSave = (Button)findViewById(R.id.btnSave);
        btnDelete = (Button)findViewById(R.id.btnDelete);
        btnClose = (Button)findViewById(R.id.btnClose);
        eventName = (EditText) findViewById(R.id.event_title);
        eventDate = (EditText) findViewById(R.id.event_date);
        eventStartTime = (EditText) findViewById(R.id.event_starttime);
        eventEndTime = (EditText) findViewById(R.id.event_endtime);
        eventLocation = (EditText) findViewById(R.id.event_location);
        eventPrice = (EditText) findViewById(R.id.editText);
        eventDescription = (EditText) findViewById(R.id.editText7);
        btnSave.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnClose.setOnClickListener(this);
        event_ID =0;
        Intent intent = getIntent();
        event_ID =intent.getIntExtra("event_ID", 0);
        EventRepo repo = new EventRepo(this);
        Event event = new Event();
        event = repo.getEventbyID(event_ID);
        eventName.setText(event.eName);
        eventDate.setText(event.eDate);
        eventStartTime.setText(event.eStartTime);
        eventEndTime.setText(event.eEndTime);
        eventLocation.setText(event.eLocation);
        eventPrice.setText(event.ePrice);
        eventDescription.setText(event.eDesc);
    }
    @Override
    public void onClick(View view) {
        if (view == findViewById(R.id.btnSave)) {
            EventRepo repo = new EventRepo(this);
            Event event = new Event();
            event.eName=eventName.getText().toString();
            event.eDate=eventDate.getText().toString();
            event.eStartTime=eventStartTime.getText().toString();
            event.eEndTime=eventEndTime.getText().toString();
            event.eLocation=eventLocation.getText().toString();
            event.ePrice=eventPrice.getText().toString();
            event.eDesc=eventDescription.getText().toString();
            event.eID=event_ID;
            if(event_ID==0) {
                event_ID = repo.insert(event);
                Toast.makeText(this, "New Event Insert", Toast.LENGTH_SHORT).show();
            } else {
                repo.update(event);
                Toast.makeText(this, "Event List Updated", Toast.LENGTH_SHORT).show();
            }
        } else if (view == findViewById(R.id.btnDelete)) {
            EventRepo repo = new EventRepo(this);
            repo.delete(event_ID);
            Toast.makeText(this, "Event deleted", Toast.LENGTH_SHORT);
            finish();
        } else if (view == findViewById(R.id.btnClose)) {
            finish();
        }
    }
}
