package explorar.explorarv9000;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by carregliu on 15/10/2017.
 */

public class EventsList extends ListActivity implements android.view.View.OnClickListener {
    Button btnAdd, btnGetAll;
    TextView eId, eventDate, eventTitle;

    @Override
    public void onClick(View view) {
        if (view == findViewById(R.id.btnAdd)) {

            Intent intent = new Intent(this, crudEvent.class);
            intent.putExtra("eventID", 0);
            startActivity(intent);

        } else {
            EventRepo repo = new EventRepo(this);

            ArrayList<HashMap<String, String>> eventList = repo.getEventList();
            if (eventList.size() != 0) {
                ListView lv = getListView();
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long eventID) {
                        eId = (TextView) view.findViewById(R.id.eID);
                        eventDate = (TextView) view.findViewById(R.id.list_row_event_date);
                        eventTitle = (TextView) view.findViewById(R.id.list_row_event_title);
                        String eId1 = eId.getText().toString();
                        Intent objIndent = new Intent(getApplicationContext(), crudEvent.class);
                        objIndent.putExtra("eventID", Integer.parseInt(eId1));
                        startActivity(objIndent);
                    }
                });
                ListAdapter adapter = new SimpleAdapter(EventsList.this, eventList, R.layout.list_row, new String[]{"eventID", "eName", "eDate"}, new int[]{R.id.eID, R.id.list_row_event_title, R.id.list_row_event_date});
                setListAdapter(adapter);
            } else {
                Toast.makeText(this, "No event!", Toast.LENGTH_SHORT).show();
            }
        }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.organiser_events);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);

        btnGetAll = (Button) findViewById(R.id.btnGetAll);
        btnGetAll.setOnClickListener(this);
    }
}
