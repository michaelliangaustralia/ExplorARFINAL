package explorar.explorarv9000;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by carregliu on 29/09/2017.
 */

public class OrganizerHome extends OrganizationLogin {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.organiser_home);
        Button organiserlistevents = (Button)findViewById(R.id.organiser_home_view_events);
        organiserlistevents.setOnClickListener(this);
        Button organisercreateevent = (Button)findViewById(R.id.organiser_home_create_event);
        organisercreateevent.setOnClickListener(this);
    }
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.organiser_home_view_events:
                Intent i = new Intent(OrganizerHome.this, EventsList.class);
                startActivity(i);
                break;
            case R.id.organiser_home_create_event:
                Intent l = new Intent(OrganizerHome.this, CreateEvent.class);
                startActivity(l);
                break;
            default:
                break;
        }
    }
}
