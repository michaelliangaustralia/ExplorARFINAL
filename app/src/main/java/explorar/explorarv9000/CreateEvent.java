package explorar.explorarv9000;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Arrays;

import static explorar.explorarv9000.OrganizationLogin.loggedUser;

/**
 * Created by carregliu on 7/10/2017.
 */

public class CreateEvent extends MainActivity implements View.OnClickListener {
    DbCreation helper = new DbCreation(this);

    EditText eventtitle;
    EditText eventdate;
    EditText eventstarttime;
    EditText eventendtime;
    EditText eventlocation;
    EditText eventprice;
    EditText eventdesc;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_event);
        Button createevent = (Button)findViewById(R.id.create_event_button);
        createevent.setOnClickListener(this);

        //initialise edittext views
        eventtitle = (EditText)findViewById(R.id.event_title);
        eventdate = (EditText)findViewById(R.id.event_date);
        eventstarttime = (EditText)findViewById(R.id.event_end_time);
        eventendtime = (EditText)findViewById(R.id.event_start_time);
        eventlocation = (EditText)findViewById(R.id.event_location);
        eventprice = (EditText)findViewById(R.id.editText);
        eventdesc = (EditText)findViewById(R.id.editText7);
        
        
    }

    public void onClick(View v) {

        //Assign text to edit text views
        String eventitlestr = eventtitle.getText().toString();
        String eventdatestr = eventdate.getText().toString();
        String eventstarttimestr = eventstarttime.getText().toString();
        String eventendtimestr = eventendtime.getText().toString();
        String eventlocationstr = eventlocation.getText().toString();
        String eventpricestr = eventprice.getText().toString();
        String eventdescstr = eventdesc.getText().toString();

        //Button click
        if(v.getId() == (R.id.create_event_button)) {

            String[] eventDataArray = new String[] {loggedUser, eventitlestr, eventdatestr, eventstarttimestr, eventendtimestr, eventlocationstr, eventpricestr, eventdescstr};

            Log.i("Michael", Arrays.toString(eventDataArray));

            /*
            intent
             */

            //intiialise intent
            Intent intent = new Intent(CreateEvent.this, OrganizerSetEventLocation.class);

            //putextra
//            intent.putExtra(Intent.EXTRA_TEXT,)

            //Start intent
            startActivity(intent);

        }else {
            Toast.makeText(getApplicationContext(), "Fields Vacant", Toast.LENGTH_LONG).show();
        }
    }
}
