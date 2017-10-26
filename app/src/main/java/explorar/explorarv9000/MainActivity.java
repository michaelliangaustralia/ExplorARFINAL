package explorar.explorarv9000;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private SQLiteDatabase mDb;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.action_bar);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        View view = getSupportActionBar().getCustomView();
        TextView textView = (TextView) findViewById(R.id.toolbar_title);
        textView.setText("Home");

        ImageButton imageButton = (ImageButton) view.findViewById(R.id.action_bar_back);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button viewmap = (Button) findViewById(R.id.activity_main_view_map_button);
        viewmap.setOnClickListener(this); // calling onClick() method
        Button organiser = (Button) findViewById(R.id.activity_main_organiser_button);
        organiser.setOnClickListener(this);

        /*
        Inform users why location permissions are required
         */
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Location permissions are required to use ExplorAR", Toast.LENGTH_LONG).show();
        }

        //initialiseDB
        initialiseDB();

        //insertFakeData
        insertFakeData();

        //initiliaseCursor
        initialiseCursor();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            //         Permission to access the location is missing.
            PermissionUtils.requestPermission(this, 1,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);

        }
    }

    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.activity_main_view_map_button:
                Intent i = new Intent(MainActivity.this, MapsActivity.class);
                startActivity(i);
                break;

            case R.id.activity_main_organiser_button:
                Intent l = new Intent(MainActivity.this, OrganizationLogin.class);
                startActivity(l);
                break;

            default:
                break;
        }

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
    }

    private void initialiseCursor(){
        //DB: call getEventName() and put it in a cursor variable
        cursor = mDb.rawQuery("Select * from " + DbContracts.eventsDBentry.TABLE_NAME + ";",null);
        Log.i("Michael", "DB data has been inserted into cursor");
    }
}



//    ListView list;
//    String[] web = {
//            "Google Plus",
//            "Twitter",
//            "Swag",
//
//    } ;
//    Integer[] imageId = {
//            R.drawable.ic_logo_vertical_01,
//            R.drawable.ic_logo_vertical_01,
//            R.drawable.ic_logo_horizontal_02,
//    };
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.organiser_events);
//
//        CustomList adapter1 = new
//                CustomList(MainActivity.this, web, imageId);
//        list=(ListView)findViewById(R.id.organiser_events_upcoming_list);
//        list.setAdapter(adapter1);
//        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view,
//                                    int position, long id) {
//                Toast.makeText(MainActivity.this, "You Clicked at " +web[+ position], Toast.LENGTH_SHORT).show();
//
//            }
//        });
//
//        CustomList adapter2 = new
//                CustomList(MainActivity.this, web, imageId);
//        list=(ListView)findViewById(R.id.organiser_events_past_list);
//        list.setAdapter(adapter2);
//        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view,
//                                    int position, long id) {
//                Toast.makeText(MainActivity.this, "You Clicked at " +web[+ position], Toast.LENGTH_SHORT).show();
//
//            }
//        });
//
//    }



