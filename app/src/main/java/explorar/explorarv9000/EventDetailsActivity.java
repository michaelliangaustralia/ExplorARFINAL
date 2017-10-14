package explorar.explorarv9000;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by michaelliang on 25/9/17.
 */

public class EventDetailsActivity extends AppCompatActivity {

    private SQLiteDatabase mDb;
    private int cursorPosition;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_detail);

        /*
        Intents
         */

        //Intent: Get Intent
        Intent mapsActivityIntentThatStartedActivity = getIntent();

        //Intent: Get Information that was packaged in it
        if (mapsActivityIntentThatStartedActivity.hasExtra(Intent.EXTRA_TEXT)) {
            cursorPosition = Integer.parseInt(mapsActivityIntentThatStartedActivity.getStringExtra(Intent.EXTRA_TEXT));
            Log.i("Michael", "The cursorPosition is " + cursorPosition);
        }

        /*
        Database
        */

        //DB: Create helper instance
        DbCreation dbCreation = new DbCreation(this);

        //DB: Get readable reference of database and store it in mDb
        mDb = dbCreation.getWritableDatabase();
        Log.i("Michael", "WritableDatabase has been created");

        //DB: call getEventName() and put it in a cursor variable
        Cursor cursor = mDb.rawQuery("Select * from " + DbContracts.eventsDBentry.TABLE_NAME + ";", null);
        Log.i("Michael", "DB data has been inserted into cursor");

        /*
        Database getting data TODO: PUT THIS AS A BACKGROUND TASK - too much work on the main thread
         */

        //DB Data: Move cursor to the row that your data is on
        cursor.moveToPosition(cursorPosition);

        //DB Data: eventName
        String eventName = cursor.getString(cursor.getColumnIndex(DbContracts.eventsDBentry.COLUMN_NAME_EVENT));
        Log.i("Michael", "eventName extracted is " + eventName);

        //DB Data: hostOrg
        String hostOrg = cursor.getString(cursor.getColumnIndex(DbContracts.eventsDBentry.COLUMN_NAME_HOSTORG));
        Log.i("Michael", "hostOrganisation extracted is " + hostOrg);

        //DB Data: location (final is declared because it needs to be used by the openMaps Intent)
        final String location = cursor.getString(cursor.getColumnIndex(DbContracts.eventsDBentry.COLUMN_LOCATION_EVENT));
        Log.i("Michael", "location extracted is " + location);

        //DB Data: date
        String date = cursor.getString(cursor.getColumnIndex(DbContracts.eventsDBentry.COLUMN_DATE_EVENT));
        Log.i("Michael", "Date extracted is " + date);

        //DB Data: startTime
        String startTime = cursor.getString(cursor.getColumnIndex(DbContracts.eventsDBentry.COLUMN_STARTTIME_EVENT));
        Log.i("Michael", "startTime extracted is " + startTime);

        //DB Data: endTime
        String endTime = cursor.getString(cursor.getColumnIndex(DbContracts.eventsDBentry.COLUMN_ENDTIME_EVENT));
        Log.i("Michael", "endTime extracted is " + endTime);

        //DB Data: price
        String price = cursor.getString(cursor.getColumnIndex(DbContracts.eventsDBentry.COLUMN_PRICE_EVENT));
        Log.i("Michael", "price extracted is " + price);

        //DB Data: description
        String description = cursor.getString(cursor.getColumnIndex(DbContracts.eventsDBentry.COLUMN_NAME_DESCRIPTION));
        Log.i("Michael", "price extracted is " + price);

        //DB Data: latitude (final is declared because it needs to be used by the openMaps Intent)
        final String latitude = cursor.getString(cursor.getColumnIndex(DbContracts.eventsDBentry.COLUMN_LATITUDE_EVENT));
        Log.i("Michael", "latitude extracted is " + latitude);

        //DB Data: longitude (final is declared because it needs to be used by the openMaps Intent)
        final String longitude = cursor.getString(cursor.getColumnIndex(DbContracts.eventsDBentry.COLUMN_LONGITUDE_EVENT));
        Log.i("Michael", "longitude extracted is " + longitude);

        //Close cursor
        cursor.close();

        /*
        Set TextViews with Data from DB
         */

        //eventname: Declare textview_event_detail_event_name field and set TextView to markerTitle
        final TextView textview_event_detail_event_name = (TextView) findViewById(R.id.event_detail_event_name);
        textview_event_detail_event_name.setText(eventName);

        //hostOrg: Declare textview_event_detail_organiser_name field and set TextView to hostOrg
        final TextView textview_event_detail_organiser_name = (TextView) findViewById(R.id.event_detail_organiser_name);
        textview_event_detail_organiser_name.setText(hostOrg);

        //location: Declare textview_event_detail_location field and set TextView to location
        final TextView textview_event_detail_location = (TextView) findViewById(R.id.event_detail_event_address);
        textview_event_detail_location.setText(location);

        //date: Declare textview_event_detail_date field and set TextView to date
        final TextView textview_event_detail_date = (TextView) findViewById(R.id.event_detail_event_date);
        textview_event_detail_date.setText(date);

        //time: Declare textview_event_detail_time field and set TextView to startTime - endTime
        final TextView textview_event_detail_time = (TextView) findViewById(R.id.event_detail_event_time);
        textview_event_detail_time.setText(startTime + " - " + endTime);

        //price: Declare textview_event_detail_price field and set TextView to price
        final TextView textview_event_detail_price = (TextView) findViewById(R.id.event_detail_event_price);
        textview_event_detail_price.setText(price);

        //description: Declare textview_event_detail_description field and set TextView to description
        final TextView textview_event_detail_description = (TextView) findViewById(R.id.event_detail_description);
        textview_event_detail_description.setText(description);



        /*
        IMAGE HERO HEAD
         */

        //IMAGE: initialise ImageView
        final ImageView imageview_event_details_hero = (ImageView) findViewById(R.id.event_detail_hero_image);

        //IMAGE: set Content of imageview_event_details_hero based off what the location was
        if (location.contains("Business School")){
            imageview_event_details_hero.setImageResource(R.drawable.unsw_business_school);
        }
        else if (location.contains("Law Building")){
            imageview_event_details_hero.setImageResource(R.drawable.law_building);
        }
        else if (location.contains("Globe Lawn")){
            imageview_event_details_hero.setImageResource(R.drawable.globe_lawn);
        }
        else if (location.contains("Library Lawn")){
            imageview_event_details_hero.setImageResource(R.drawable.library_lawn);
        }
        else {
            imageview_event_details_hero.setImageResource(R.drawable.unsw);
        }

        //IMAGE: Stretch image to fit the imageview
        imageview_event_details_hero.setScaleType(ImageView.ScaleType.FIT_XY);


        /*
        Create event_detail_button - Maps Directions
         */

        //eventdetailbutton: Declare event_detail_button
        final Button event_detail_button = (Button) findViewById(R.id.event_detail_button);

        //eventdetailbutton: Setting action of event_detail_button
        event_detail_button.setOnClickListener(new View.OnClickListener() {
                                                   public void onClick(View v) {
                                                       //Build URI
                                                       Uri latLngIntentUri = Uri.parse("http://maps.google.com/maps?q=loc:" + latitude + "," + longitude + "(" + location + ")");

                                                       //Intent to open up Google Maps with directions
                                                       Intent openMapintent = new Intent(Intent.ACTION_VIEW, latLngIntentUri);

                                                       //Start openMapIntent activity and check for application that can resolve it
                                                       if (openMapintent.resolveActivity(getPackageManager()) != null) {
                                                           startActivity(openMapintent);
                                                       } else {
                                                           Toast.makeText(getBaseContext(), "No applications found to resolve activity", Toast.LENGTH_LONG).show();
                                                       }
                                                   }
                                               }
        );



    }
}


/*
           IMAGE DB

in main
//        downloadImage("https://static.pexels.com/photos/7174/summer-grass.jpg");



// AsynnTask to run download an image in background
class BackTask extends AsyncTask<String, Void, Bitmap> {

    protected void onPreExecute() {
        Log.i("Michael","Backtask is executing - woohoo!");
    }

    protected Bitmap doInBackground(String... params) {
        Bitmap bitmap = null;
        try {
            // Download the image
            URL url = new URL(params[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream is = connection.getInputStream();
            // Decode image to get smaller image to save memory
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = false;
            options.inSampleSize = 4;
            bitmap = BitmapFactory.decodeStream(is, null, options);
            is.close();
        } catch (IOException e) {
            return null;
        }
        return bitmap;
    }

    protected void onPostExecute(Bitmap result) {
        // Insert bitmap to the database

        //DB: Create helper instance
        DbCreation dbCreation = new DbCreation(getBaseContext());

        //DB: Get readable reference of database and store it in mDb
        mDb = dbCreation.getWritableDatabase();
        Log.i("Michael", "IMAGE DB: WritableDatabase has been created");


        mDb.insert(DbContracts.imageDBentry.TABLE_NAME,null,result.bitmap);
        helper.insertBitmap(result);
        Log.i("Michael", "IMAGE DB: Bitmap is" + result);
        Log.i("Michael", "IMAGE DB: Bitmap inserted!");
    }
}

    public void downloadImage(String url) {
        BackTask bt = new BackTask();
        bt.execute(url); //Not sure if this is legit
        Log.i("Michael", "Image Downloaded!");
    }
 */


