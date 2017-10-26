package explorar.explorarv9000;

/**
 * Created by michaelliang on 25/9/17.
 */

import android.content.ContentValues;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;


public class DBInsertFakeData {

    public static void insertFakeData(SQLiteDatabase db){
        if(db != null){
            Log.i("Michael", "insertFakeData called but No fake data is being added");
            return;
        }
        Log.i("Michael", "InserFakeData is called and fake data is being added");


        //create a list of fake events
        List<ContentValues> list = new ArrayList<ContentValues>();

        //db: beginTransaction();
        db.beginTransaction();

        //db: clear database table
        db.delete (DbContracts.eventsDBentry.TABLE_NAME,null,null);

        //Add fake data into eventsDB
        ContentValues cv = new ContentValues();
        cv.put(DbContracts.eventsDBentry.COLUMN_NAME_EVENT, "SQL Workshop 101");
        cv.put(DbContracts.eventsDBentry.COLUMN_NAME_HOSTORG, "BITSA UNSW");
        cv.put(DbContracts.eventsDBentry.COLUMN_LOCATION_EVENT, "UNSW Business School G26");
        cv.put(DbContracts.eventsDBentry.COLUMN_DATE_EVENT, "2nd August 2017");
        cv.put(DbContracts.eventsDBentry.COLUMN_STARTTIME_EVENT, "12:00");
        cv.put(DbContracts.eventsDBentry.COLUMN_ENDTIME_EVENT, "2:00");
        cv.put(DbContracts.eventsDBentry.COLUMN_PRICE_EVENT, "FREE");
        cv.put(DbContracts.eventsDBentry.COLUMN_NAME_DESCRIPTION, "Best SQL Workshop at UNSW happening today!");
        cv.put(DbContracts.eventsDBentry.COLUMN_LATITUDE_EVENT, "-33.916710");
        cv.put(DbContracts.eventsDBentry.COLUMN_LONGITUDE_EVENT, "151.229658");
        cv.put(DbContracts.eventsDBentry.COLUMN_EVENT_TYPE , "WORKSHOP");
        list.add(cv);
        db.insert(DbContracts.eventsDBentry.TABLE_NAME, null, cv);

        cv.put(DbContracts.eventsDBentry.COLUMN_NAME_EVENT, "Learn R with WIT!");
        cv.put(DbContracts.eventsDBentry.COLUMN_NAME_HOSTORG, "Women In Technology (WIT)");
        cv.put(DbContracts.eventsDBentry.COLUMN_LOCATION_EVENT, "UNSW Law Building 101");
        cv.put(DbContracts.eventsDBentry.COLUMN_DATE_EVENT, "4th August 2017");
        cv.put(DbContracts.eventsDBentry.COLUMN_STARTTIME_EVENT, "2:00");
        cv.put(DbContracts.eventsDBentry.COLUMN_ENDTIME_EVENT, "4:00");
        cv.put(DbContracts.eventsDBentry.COLUMN_PRICE_EVENT, "$5");
        cv.put(DbContracts.eventsDBentry.COLUMN_NAME_DESCRIPTION, "Free Pizza!");
        cv.put(DbContracts.eventsDBentry.COLUMN_LATITUDE_EVENT, "-33.916950");
        cv.put(DbContracts.eventsDBentry.COLUMN_LONGITUDE_EVENT, "151.227974");
        cv.put(DbContracts.eventsDBentry.COLUMN_EVENT_TYPE , "WORKSHOP");
        list.add(cv);
        db.insert(DbContracts.eventsDBentry.TABLE_NAME, null, cv);

        cv.put(DbContracts.eventsDBentry.COLUMN_NAME_EVENT, "UNIT Director Meet and Greet");
        cv.put(DbContracts.eventsDBentry.COLUMN_NAME_HOSTORG, "University Network of Investment and Trading (UNIT)");
        cv.put(DbContracts.eventsDBentry.COLUMN_LOCATION_EVENT, "UNSW Library Lawn");
        cv.put(DbContracts.eventsDBentry.COLUMN_DATE_EVENT, "6th August 2017");
        cv.put(DbContracts.eventsDBentry.COLUMN_STARTTIME_EVENT, "11:00");
        cv.put(DbContracts.eventsDBentry.COLUMN_ENDTIME_EVENT, "12:00");
        cv.put(DbContracts.eventsDBentry.COLUMN_PRICE_EVENT, "FREE");
        cv.put(DbContracts.eventsDBentry.COLUMN_NAME_DESCRIPTION, "Director applications are open now! Take this opportunity to learn more about UNIT and meet some of our current and incoming executives.");
        cv.put(DbContracts.eventsDBentry.COLUMN_LATITUDE_EVENT, "-33.916905");
        cv.put(DbContracts.eventsDBentry.COLUMN_LONGITUDE_EVENT, "151.233509");
        cv.put(DbContracts.eventsDBentry.COLUMN_EVENT_TYPE , "SOCIAL");
        list.add(cv);
        db.insert(DbContracts.eventsDBentry.TABLE_NAME, null, cv);

        cv.put(DbContracts.eventsDBentry.COLUMN_NAME_EVENT, "UNSW BSOC Camp Sales");
        cv.put(DbContracts.eventsDBentry.COLUMN_NAME_HOSTORG, "UNSW Business Society (BSOC)");
        cv.put(DbContracts.eventsDBentry.COLUMN_LOCATION_EVENT, "UNSW Physics Lawn");
        cv.put(DbContracts.eventsDBentry.COLUMN_DATE_EVENT, "8th August 2017");
        cv.put(DbContracts.eventsDBentry.COLUMN_STARTTIME_EVENT, "8:00");
        cv.put(DbContracts.eventsDBentry.COLUMN_ENDTIME_EVENT, "10:00");
        cv.put(DbContracts.eventsDBentry.COLUMN_PRICE_EVENT, "FREE");
        cv.put(DbContracts.eventsDBentry.COLUMN_NAME_DESCRIPTION, "Tickets for our highly anticipated annual camp are on sale now! Jump on by our stall to grab your ones today. Get them before they sell out!");
        cv.put(DbContracts.eventsDBentry.COLUMN_LATITUDE_EVENT, "-33.918017");
        cv.put(DbContracts.eventsDBentry.COLUMN_LONGITUDE_EVENT, "151.230789");
        cv.put(DbContracts.eventsDBentry.COLUMN_EVENT_TYPE , "SOCIAL");
        list.add(cv);
        db.insert(DbContracts.eventsDBentry.TABLE_NAME, null, cv);

        //Add fake data into organisationsDB
        ContentValues cv2 = new ContentValues();

        cv2.put(DbContracts.organisationsDBentry.COLUMN_NAME_ORG, "WIT UNSW");
        cv2.put(DbContracts.organisationsDBentry.COLUMN_EMAIL_ORG, "1");
        cv2.put(DbContracts.organisationsDBentry.COLUMN_PASSWORD_ORG, "1");
        list.add(cv2);

        db.insert(DbContracts.organisationsDBentry.TABLE_NAME, null, cv2);

        //db: setTransactionSuccessful();
        db.setTransactionSuccessful();

        //db: endTransaction();
        db.endTransaction();

        //Success
        Log.i("Michael", "Fake Data has been inserted");


//            final String INSERT_TEST_DATA_ONE = "INSERT INTO " +
//                    DbContracts.eventsDBentry .TABLE_NAME + " (" +
//                    DbContracts.eventsDBentry ._ID + ", " +
//                    DbContracts.eventsDBentry .COLUMN_NAME_EVENT + ", " +
//                    DbContracts.eventsDBentry .COLUMN_NAME_HOSTORG + ", " +
//                    DbContracts.eventsDBentry .COLUMN_LOCATION_EVENT + ", " +
//                    DbContracts.eventsDBentry .COLUMN_DATE_EVENT + ", " +
//                    DbContracts.eventsDBentry .COLUMN_STARTTIME_EVENT + ", " +
//                    DbContracts.eventsDBentry .COLUMN_ENDTIME_EVENT + ", " +
//                    DbContracts.eventsDBentry .COLUMN_PRICE_EVENT + ", " +
//                    DbContracts.eventsDBentry .COLUMN_NAME_DESCRIPTION + ", " +
//                    DbContracts.eventsDBentry .COLUMN_LATITUDE_EVENT + "," +
//                    DbContracts.eventsDBentry .COLUMN_LONGITUDE_EVENT + "," +
//                    DbContracts.eventsDBentry .COLUMN_EVENT_TYPE + ")" +
//                    "VALUES (" +
//                    "SQL Workshop 102," +
//                    "UNSW Business Society," +
//                    "Alumni Lawn," +
//                    "31st December 2017," +
//                    "18:00," +
//                    "20:00," +
//                    "10.00," + //double? price
//                    "Learn to code in SQL and be amazing," +
//                    "-33.919728," +
//                    "151.234095," +
//                    "Workshop);"
//                    ;
//
//            db.execSQL(INSERT_TEST_DATA_ONE);

//
//            DbContracts.eventsDBentry .TABLE_NAME + " (" +
//                    DbContracts.eventsDBentry ._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
//                    DbContracts.eventsDBentry .COLUMN_NAME_EVENT + " TEXT NOT NULL, " +
//                    DbContracts.eventsDBentry .COLUMN_NAME_HOSTORG + " TEXT NOT NULL, " +
//                    DbContracts.eventsDBentry .COLUMN_LOCATION_EVENT + " TEXT NOT NULL," +
//                    DbContracts.eventsDBentry .COLUMN_DATE_EVENT + " TEXT NOT NULL," +
//                    DbContracts.eventsDBentry .COLUMN_STARTTIME_EVENT + " TEXT NOT NULL," +
//                    DbContracts.eventsDBentry .COLUMN_ENDTIME_EVENT + " TEXT NOT NULL," +
//                    DbContracts.eventsDBentry .COLUMN_PRICE_EVENT + " DOUBLE NOT NULL," +
//                    DbContracts.eventsDBentry .COLUMN_NAME_DESCRIPTION + " TEXT NOT NULL, " +
//                    DbContracts.eventsDBentry .COLUMN_LATITUDE_EVENT + " DOUBLE NOT NULL," +
//                    DbContracts.eventsDBentry .COLUMN_LONGITUDE_EVENT + " DOUBLE NOT NULL," +
//                    DbContracts.eventsDBentry .COLUMN_EVENT_TYPE + " TEXT NOT NULL" +
//                    ");";




        //insert all events in one transaction
//            try
//            {
//                db.beginTransaction();
//                //clear the table first
//                db.delete (DbContracts.eventsDBentry.TABLE_NAME,null,null);
//                //go through the list and add one by one
//                for(ContentValues c:list){
//                    db.insert(DbContracts.eventsDBentry.TABLE_NAME, null, c);
//                }
//                db.setTransactionSuccessful();
//            }
//            catch (SQLException e) {
//                //too bad :(
//            }
//            finally
//            {
//                db.endTransaction();
//            }

    }
}

