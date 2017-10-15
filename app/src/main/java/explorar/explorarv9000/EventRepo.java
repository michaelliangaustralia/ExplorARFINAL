package explorar.explorarv9000;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;

import model.Event;
import static explorar.explorarv9000.OrganizationLogin.loggedUser;

/**
 * Created by carregliu on 15/10/2017.
 */

public class EventRepo {
    private DbCreation dbHelper;

    public EventRepo(Context context) { dbHelper = new DbCreation(context);}

    public int insert(Event event) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DbContracts.eventsDBentry.COLUMN_NAME_EVENT, event.eName);
        values.put(DbContracts.eventsDBentry.COLUMN_DATE_EVENT, event.eDesc);
        values.put(DbContracts.eventsDBentry.COLUMN_STARTTIME_EVENT, event.eStartTime);
        values.put(DbContracts.eventsDBentry.COLUMN_ENDTIME_EVENT, event.eEndTime);
        values.put(DbContracts.eventsDBentry.COLUMN_NAME_DESCRIPTION, event.eDesc);
        values.put(DbContracts.eventsDBentry.COLUMN_PRICE_EVENT, event.ePrice);

        //Inserting Row
        long eID = sqLiteDatabase.insert(DbContracts.eventsDBentry.TABLE_NAME, null, values);
        sqLiteDatabase.close();
        return (int) eID;
    }

    public void delete(int eID) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        sqLiteDatabase.delete(DbContracts.eventsDBentry.TABLE_NAME, DbContracts.eventsDBentry._ID + "= ?", new String[] { String.valueOf(eID) });
        sqLiteDatabase.close();
    }

    public void update(Event event) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DbContracts.eventsDBentry.COLUMN_NAME_EVENT, event.eName);
        values.put(DbContracts.eventsDBentry.COLUMN_DATE_EVENT, event.eDesc);
        values.put(DbContracts.eventsDBentry.COLUMN_STARTTIME_EVENT, event.eStartTime);
        values.put(DbContracts.eventsDBentry.COLUMN_ENDTIME_EVENT, event.eEndTime);
        values.put(DbContracts.eventsDBentry.COLUMN_NAME_DESCRIPTION, event.eDesc);
        values.put(DbContracts.eventsDBentry.COLUMN_PRICE_EVENT, event.ePrice);

        sqLiteDatabase.update(DbContracts.eventsDBentry.TABLE_NAME, values, DbContracts.eventsDBentry._ID + "= ?", new String[] { String.valueOf(event.eID) });
        sqLiteDatabase.close();
    }

    public ArrayList<HashMap<String, String>> getEventList() {
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT " +
                DbContracts.eventsDBentry._ID + " , " +
                DbContracts.eventsDBentry .COLUMN_NAME_EVENT + " , " +
                DbContracts.eventsDBentry .COLUMN_LOCATION_EVENT + " ," +
                DbContracts.eventsDBentry .COLUMN_DATE_EVENT + " ," +
                DbContracts.eventsDBentry .COLUMN_STARTTIME_EVENT + " ," +
                DbContracts.eventsDBentry .COLUMN_ENDTIME_EVENT + " ," +
                DbContracts.eventsDBentry .COLUMN_PRICE_EVENT + " ," +
                DbContracts.eventsDBentry .COLUMN_NAME_DESCRIPTION +
                " FROM " + DbContracts.eventsDBentry.TABLE_NAME + "WHERE " +DbContracts.eventsDBentry.COLUMN_NAME_HOSTORG + "=" +loggedUser;

        ArrayList<HashMap<String, String>> eventList = new ArrayList<HashMap<String, String>>();
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> event = new HashMap<String, String>();
                event.put("eID", cursor.getString(cursor.getColumnIndex(DbContracts.eventsDBentry._ID)));
                event.put("eName", cursor.getString(cursor.getColumnIndex(DbContracts.eventsDBentry.COLUMN_NAME_EVENT)));
                event.put("eLocation", cursor.getString(cursor.getColumnIndex(DbContracts.eventsDBentry.COLUMN_LOCATION_EVENT)));
                event.put("eDate", cursor.getString(cursor.getColumnIndex(DbContracts.eventsDBentry.COLUMN_DATE_EVENT)));
                event.put("eStartTime", cursor.getString(cursor.getColumnIndex(DbContracts.eventsDBentry.COLUMN_STARTTIME_EVENT)));
                event.put("eEndTime", cursor.getString(cursor.getColumnIndex(DbContracts.eventsDBentry.COLUMN_ENDTIME_EVENT)));
                event.put("ePrice", cursor.getString(cursor.getColumnIndex(DbContracts.eventsDBentry.COLUMN_PRICE_EVENT)));
                event.put("eDesc", cursor.getString(cursor.getColumnIndex(DbContracts.eventsDBentry.COLUMN_NAME_DESCRIPTION)));
                eventList.add(event);
            } while (cursor.moveToNext());
        }
        cursor.close();
        sqLiteDatabase.close();
        return eventList;
    }

    public Event getEventbyID(int eID){
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT " +
                DbContracts.eventsDBentry._ID + " , " +
                DbContracts.eventsDBentry .COLUMN_NAME_EVENT + " , " +
                DbContracts.eventsDBentry .COLUMN_LOCATION_EVENT + " ," +
                DbContracts.eventsDBentry .COLUMN_DATE_EVENT + " ," +
                DbContracts.eventsDBentry .COLUMN_STARTTIME_EVENT + " ," +
                DbContracts.eventsDBentry .COLUMN_ENDTIME_EVENT + " ," +
                DbContracts.eventsDBentry .COLUMN_PRICE_EVENT + " ," +
                DbContracts.eventsDBentry .COLUMN_NAME_DESCRIPTION +
                " FROM " + DbContracts.eventsDBentry.TABLE_NAME
                + " WHERE " +
                DbContracts.eventsDBentry.COLUMN_EVENT_ID + "=?";

        int iCount =0;
        Event event = new Event();

        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, new String[] { String.valueOf(eID)});
        if (cursor.moveToFirst()) {
            do {
                event.eID =cursor.getInt(cursor.getColumnIndex(DbContracts.eventsDBentry._ID));
                event.eName =cursor.getString(cursor.getColumnIndex(DbContracts.eventsDBentry.COLUMN_NAME_EVENT));
                event.eLocation =cursor.getString(cursor.getColumnIndex(DbContracts.eventsDBentry.COLUMN_LOCATION_EVENT));
                event.eDate =cursor.getString(cursor.getColumnIndex(DbContracts.eventsDBentry.COLUMN_DATE_EVENT));
                event.eStartTime =cursor.getString(cursor.getColumnIndex(DbContracts.eventsDBentry.COLUMN_STARTTIME_EVENT));
                event.eEndTime =cursor.getString(cursor.getColumnIndex(DbContracts.eventsDBentry.COLUMN_ENDTIME_EVENT));
                event.ePrice =cursor.getString(cursor.getColumnIndex(DbContracts.eventsDBentry.COLUMN_PRICE_EVENT));
                event.eDesc =cursor.getString(cursor.getColumnIndex(DbContracts.eventsDBentry.COLUMN_NAME_DESCRIPTION));
            } while (cursor.moveToNext());
        }
        cursor.close();
        sqLiteDatabase.close();
        return event;
    }
}
