package explorar.explorarv9000;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by carregliu on 7/10/2017.
 */

public class CreateEvent extends MainActivity implements View.OnClickListener {
    DbCreation helper = new DbCreation(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_event);
        Button createevent = (Button)findViewById(R.id.create_event_button);
        createevent.setOnClickListener(this);
    }
    public void onClick(View v) {
        EditText eventitle = (EditText)findViewById(R.id.event_title);
        EditText eventdate = (EditText)findViewById(R.id.event_date);
        EditText eventstarttime = (EditText)findViewById(R.id.event_end_time);
        EditText eventendtime = (EditText)findViewById(R.id.event_start_time);
        EditText eventlocation = (EditText)findViewById(R.id.event_location);
        EditText eventprice = (EditText)findViewById(R.id.editText);
        EditText eventdesc = (EditText)findViewById(R.id.editText7);
        String eventitlestr = eventitle.getText().toString();
        String eventdatestr = eventdate.getText().toString();
        String eventstarttimestr = eventstarttime.getText().toString();
        String eventendtimestr = eventendtime.getText().toString();
        String eventlocationstr = eventlocation.getText().toString();
        String eventpricestr = eventprice.getText().toString();
        String eventdescstr = eventdesc.getText().toString();
        if(v.getId() == (R.id.create_event_button)) {
            helper.insertEvent(eventitlestr, eventdatestr, eventstarttimestr, eventendtimestr, eventlocationstr, eventpricestr, eventdescstr, "latitude", "longitude");
            Toast.makeText(getApplicationContext(), "Choose Event Location", Toast.LENGTH_LONG).show();
            Intent i = new Intent(CreateEvent.this, OrganizerSetEventLocation.class);
            startActivity(i);
        }else {
            Toast.makeText(getApplicationContext(), "Fields Vacant", Toast.LENGTH_LONG).show();
        }
    }
}
