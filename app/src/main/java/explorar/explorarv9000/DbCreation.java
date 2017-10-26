package explorar.explorarv9000;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import model.Organization;
import model.Student;

/**
 * Created by benja on 17/09/2017.
 */

// this class is responsible for doing anything with the database

public class DbCreation extends SQLiteOpenHelper {
    private Cursor cursor;
    private String[] data = new String [100];


    private static final int DATABASE_VERSION = 1; //remember to update the version number when any database changes are made
    private static final String DATABASE_NAME = "app.db";

    public DbCreation(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    public DbCreation(Context context)
    {
        super(context, DATABASE_NAME , null , DATABASE_VERSION);
    }

    // creates the database tables by running the sql queries e.g. "SQL_CREATE_STUDENT_TABLE"
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_STUDENT_TABLE = "CREATE TABLE " +
                DbContracts.studentDBentry.TABLE_NAME + " (" +
                DbContracts.studentDBentry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DbContracts.studentDBentry.COLUMN_zID + " TEXT NOT NULL, " +
                DbContracts.studentDBentry.COLUMN_NAME_STUDENT + " TEXT NOT NULL, " +
                DbContracts.studentDBentry.COLUMN_PASSWORD_STUDENT + " TEXT NOT NULL, " +
                DbContracts.studentDBentry.COLUMN_EMAIL_STUDENT + " TEXT NOT NULL, " +
                DbContracts.studentDBentry.COLUMN_DEGREE_STUDENT + "TEXT NOT NULL" +
                ");";

        final String SQL_CREATE_ORGANISATIONS_TABLE = "CREATE TABLE " +
                DbContracts.organisationsDBentry .TABLE_NAME + " (" +
                DbContracts.organisationsDBentry ._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DbContracts.organisationsDBentry .COLUMN_NAME_ORG + " TEXT NOT NULL, " +
                DbContracts.organisationsDBentry .COLUMN_PASSWORD_ORG + " TEXT NOT NULL, " +
                DbContracts.organisationsDBentry .COLUMN_EMAIL_ORG + " TEXT NOT NULL" +
                ");";

        //TODO: MIGHT NEED TO MAKE THIS A DATE FORMAT

        final String SQL_CREATE_EVENTS_TABLE = "CREATE TABLE " +
                DbContracts.eventsDBentry .TABLE_NAME + " (" +
                DbContracts.eventsDBentry ._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DbContracts.eventsDBentry .COLUMN_NAME_EVENT + " TEXT NOT NULL, " +
                DbContracts.eventsDBentry .COLUMN_NAME_HOSTORG + " TEXT NOT NULL, " +
                DbContracts.eventsDBentry .COLUMN_LOCATION_EVENT + " TEXT NOT NULL, " +
                DbContracts.eventsDBentry .COLUMN_DATE_EVENT + " TEXT NOT NULL, " +
                DbContracts.eventsDBentry .COLUMN_STARTTIME_EVENT + " TEXT NOT NULL, " +
                DbContracts.eventsDBentry .COLUMN_ENDTIME_EVENT + " TEXT NOT NULL, " +
                DbContracts.eventsDBentry .COLUMN_PRICE_EVENT + " DOUBLE NOT NULL, " +
                DbContracts.eventsDBentry .COLUMN_NAME_DESCRIPTION + " TEXT NOT NULL, " +
                DbContracts.eventsDBentry .COLUMN_LATITUDE_EVENT + " DOUBLE NOT NULL, " +
                DbContracts.eventsDBentry .COLUMN_LONGITUDE_EVENT + " DOUBLE NOT NULL, " +
                DbContracts.eventsDBentry .COLUMN_EVENT_TYPE + " TEXT NOT NULL" +
                ");";

//        final String SQL_CREATE_IMAGES_TABLE = "CREATE TABLE " +
//                DbContracts.organisationsDBentry.TABLE_NAME + " (" +
//                DbContracts.imageDBentry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
//                DbContracts.imageDBentry.COLUMN_IMAGE + " BLOB NOT NULL, " +
//                DbContracts.imageDBentry.COLUMN_DESCRIPTION + " TEXT NULL" +
//                ");";

        sqLiteDatabase.execSQL(SQL_CREATE_STUDENT_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_ORGANISATIONS_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_EVENTS_TABLE);
//        sqLiteDatabase.execSQL(SQL_CREATE_IMAGES_TABLE);

    }

    //Get organisation name based off input email
    public String getOrganisationNameFromEmail(String email){
        //Create and initialise DB + cursor
        SQLiteDatabase db = getWritableDatabase();

        cursor = db.rawQuery("SELECT " +
                DbContracts.organisationsDBentry.COLUMN_NAME_ORG +
                                    " FROM " +
            DbContracts.organisationsDBentry.TABLE_NAME + " WHERE " +
                DbContracts.organisationsDBentry.COLUMN_EMAIL_ORG + " = \"" +
                email +
                "\";"
                ,null);

//
//        for (int i = 0 ; i < 12; i++){
//            cursor.moveToPosition(i);
//
//            data[i] = cursor.getString(0);
//            Log.i("Michael", Arrays.toString(data));
//        }

        Log.i("Michael - dbc", String.valueOf("cursor number of rows is " + cursor.getCount()));
        Log.i("Michael - dbc", String.valueOf("cursor number of columns is"  + cursor.getColumnCount()));
        Log.i("Michael - dbc", String.valueOf("cursor position is " + cursor.getPosition()));

        //Retrieve result from cursor
        String result;

        //retrieve email from cursor
        cursor.moveToFirst();
        result = cursor.getString(0);
        Log.i("Michael", String.valueOf("what is the result? " + result));

        return result;
    }


    //insert a new organisation row
    public void insertOrganization(String oName, String oEmail, String oPassword) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DbContracts.organisationsDBentry.COLUMN_NAME_ORG, oName);
        values.put(DbContracts.organisationsDBentry.COLUMN_EMAIL_ORG, oEmail);
        values.put(DbContracts.organisationsDBentry.COLUMN_PASSWORD_ORG, oPassword);
        db.insert(DbContracts.organisationsDBentry.TABLE_NAME, null, values);
        db.close();
    }

    //insert a new event row
    public void insertEvent(String orgName, String eventName, String eventLocation, String eventDate, String eventStartTime, String eventEndTime, String eventPrice, String eventDescription, Double eventLatitude, Double eventLongitude) { //I changed the last two from string to double
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();

        List<ContentValues> list = new ArrayList<ContentValues>();

        db.beginTransaction();

        values.put(DbContracts.eventsDBentry.COLUMN_NAME_EVENT, eventName);
        values.put(DbContracts.eventsDBentry.COLUMN_NAME_HOSTORG, orgName);
        values.put(DbContracts.eventsDBentry.COLUMN_LOCATION_EVENT, eventLocation);
        values.put(DbContracts.eventsDBentry.COLUMN_DATE_EVENT, eventDate);
        values.put(DbContracts.eventsDBentry.COLUMN_STARTTIME_EVENT, eventStartTime);
        values.put(DbContracts.eventsDBentry.COLUMN_ENDTIME_EVENT, eventEndTime);
        values.put(DbContracts.eventsDBentry.COLUMN_PRICE_EVENT, eventPrice);
        values.put(DbContracts.eventsDBentry.COLUMN_NAME_DESCRIPTION, eventDescription);
        values.put(DbContracts.eventsDBentry.COLUMN_LATITUDE_EVENT, eventLatitude);
        values.put(DbContracts.eventsDBentry.COLUMN_LONGITUDE_EVENT, eventLongitude);
        values.put(DbContracts.eventsDBentry.COLUMN_EVENT_TYPE , "WORKSHOP");

        list.add(values);
        db.insert(DbContracts.eventsDBentry.TABLE_NAME, null, values);

        //db: setTransactionSuccessful();
        db.setTransactionSuccessful();

        //db: endTransaction();
        db.endTransaction();


    }

    //search username and password
    public boolean searchoPassword(String email, String pass)
    {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " +
        DbContracts.organisationsDBentry .TABLE_NAME +" WHERE "+
                DbContracts.organisationsDBentry .COLUMN_EMAIL_ORG +" =? AND "+
        DbContracts.organisationsDBentry .COLUMN_PASSWORD_ORG +"=?", new String[]{email,pass});
        if (cursor != null) {
            if(cursor.getCount() > 0)
            {
                return true;
            }
        }
        return false;
    }

    //delete row
    public void deleteRow(int eventID){
        SQLiteDatabase db = getWritableDatabase();

        db.delete(DbContracts.eventsDBentry.TABLE_NAME, DbContracts.eventsDBentry._ID + "=" + eventID, null);

        Log.i("Michael - deleteRow", "Row has been deleted");
    }

    // drops the current table and creates a new one when a new version is updated
    @Override
    public void onUpgrade (SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS  + " + DbContracts.studentDBentry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

}

    /*
      IMAGE DB


    //INSERT BITMAP
    public void insertBitmap(Bitmap bm) {
        // Convert the image into byte array
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, out);
        byte[] buffer = out.toByteArray();
        // Open the database for writing
        SQLiteDatabase db = this.getWritableDatabase();
        // Start the transaction.
        db.beginTransaction();
        ContentValues values;

        try {
            values = new ContentValues();
            values.put("img", buffer);
            values.put("description", "Image description");
            // Insert Row
            long i = db.insert(DbContracts.imageDBentry.TABLE_NAME, null, values);
            Log.i("Insert", i + "");
            // Insert into database successfully.
            db.setTransactionSuccessful();

        } catch (SQLiteException e) {
            e.printStackTrace();

        } finally {
            db.endTransaction();
            // End the transaction.
            db.close();
            // Close database
        }
    }

    //GET BITMAP
        public Bitmap getBitmap(int id){
            Bitmap bitmap = null;
            // Open the database for reading
            SQLiteDatabase db = this.getReadableDatabase();
            // Start the transaction.
            db.beginTransaction();

            try
            {
                String selectQuery = "SELECT * FROM "+ DbContracts.imageDBentry.TABLE_NAME +" WHERE id = " + id;
                Cursor cursor = db.rawQuery(selectQuery, null);
                if(cursor.getCount() >0)
                {
                    while (cursor.moveToNext()) {
                        // Convert blob data to byte array
                        byte[] blob = cursor.getBlob(cursor.getColumnIndex("img"));
                        // Convert the byte array to Bitmap
                        bitmap= BitmapFactory.decodeByteArray(blob, 0, blob.length);

                    }

                }
                db.setTransactionSuccessful();

            }
            catch (SQLiteException e)
            {
                e.printStackTrace();

            }
            finally
            {
                db.endTransaction();
                // End the transaction.
                db.close();
                // Close database
            }
            return bitmap;

        }

     */

