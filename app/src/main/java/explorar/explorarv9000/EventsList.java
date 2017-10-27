package explorar.explorarv9000;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by kevin on 14/10/2017.
 */


public class EventsList extends ListActivity implements android.view.View.OnClickListener {
    Button btnAdd, btnGetAll;
    TextView eId, eventDate, eventTitle;
    ImageView eImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.organiser_events);

        /*
        Buttons: Declare all the buttons
         */

        //Buttons: Add (This button is hidden for some reason
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);

        //Buttons: Refresh
        btnGetAll = (Button) findViewById(R.id.btnGetAll);
        btnGetAll.setOnClickListener(this);

        //eventrepo declared
        EventRepo repo = new EventRepo(this);

        //eventList declared
        ArrayList<HashMap<String, String>> eventList = repo.getEventList();

        if(eventList.size()!=0) {
            //get list view
            ListView lv = getListView();

            //onItemClick - of an event
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                //
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long eID) {
                    eId = (TextView) view.findViewById(R.id.eID);
                    String eventID = eId.getText().toString();
                    Log.i("Michael EventsList", "eventID is " + eventID);

                    //start intent
                    Intent objIndent = new Intent(getApplicationContext(), crudEvent.class);
                    objIndent.putExtra(Intent.EXTRA_TEXT, eventID);
                    startActivity(objIndent);
                }
            });


            //Added in everything
            SimpleAdapter adapter = new SimpleAdapter(
                    EventsList.this,
                    eventList,
                    R.layout.list_row,
                    new String[] {"eID","eName","eDate", "eImage"},
                    new int[] {R.id.eID,R .id.list_row_event_title,R.id.list_row_event_date, R.id.list_row_event_photo});
            setListAdapter(adapter);

        }
        else{
            Toast.makeText(this,"No event!",Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onClick(View view) {

        EventRepo repo = new EventRepo(this);
        ArrayList<HashMap<String, String>> eventList = repo.getEventList();

        //Add button
        if (view == findViewById(R.id.btnAdd)) {

            Intent intent = new Intent(this, CreateEvent.class);
            intent.putExtra("eID", 0);
            startActivity(intent);

        }

        else {
            if(eventList.size()!=0) {
                ListView lv = getListView();
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long eID) {
                        eId = (TextView) view.findViewById(R.id.eID);
                        eventDate = (TextView) view.findViewById(R.id.list_row_event_date);
                        eventTitle = (TextView) view.findViewById(R.id.list_row_event_title);
                        String eId1 = eId.getText().toString();
                        Intent objIndent = new Intent(getApplicationContext(), crudEvent.class);
                        objIndent.putExtra("eID", Integer.parseInt(eId1));
                        startActivity(objIndent);
                    }
                });
                ListAdapter adapter = new SimpleAdapter( EventsList.this,eventList, R.layout.list_row, new String[] { "eID","eName","eDate","eImage"}, new int[] {R.id.eID,R .id.list_row_event_title,R.id.list_row_event_date,R.id.list_row_event_photo});
                setListAdapter(adapter);
            }
            else{
                Toast.makeText(this,"No event!",Toast.LENGTH_SHORT).show();
            }
        }

    }
}