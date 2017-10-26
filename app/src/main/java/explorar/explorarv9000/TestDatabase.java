package explorar.explorarv9000;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.Arrays;

/**
 * Created by michaelliang on 26/10/17.
 */

public class TestDatabase extends AppCompatActivity{
    private SQLiteDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //DB: Create helper instance
        DbCreation dbCreation = new DbCreation(this);

        //DB: Get readable reference of database and store it in mDb
        mDb = dbCreation.getWritableDatabase();

        Cursor cursor = mDb.rawQuery("Select * from " + DbContracts.eventsDBentry.TABLE_NAME + ";", null);


        //DB: Print all data in cursor

        String[] data = new String[100];
        int m = 1;

        cursor.moveToPosition(0);

////        for (int i = 0 ; i < 5; i ++ ){
//            while (cursor.getPosition() <5){
        while (m < 12) {
            data[0] += " " + cursor.getString(m);
            Log.i("Michael - TestDatabase", "m is " + m);
//                Log.i("Michael - TestDatabase", "i is " + 0);
            Log.i("Michael - TestDatabase", Arrays.toString(data));
            m++;

        }
        m=1;

        cursor.moveToPosition(1);

////        for (int i = 0 ; i < 5; i ++ ){
//            while (cursor.getPosition() <5){
        while (m < 12) {
            data[1] += " " + cursor.getString(m);
            Log.i("Michael - TestDatabase", "m is " + m);
//                Log.i("Michael - TestDatabase", "i is " + 0);
            Log.i("Michael - TestDatabase", Arrays.toString(data));
            m++;

        }
        m=1;

        cursor.moveToPosition(2);

////        for (int i = 0 ; i < 5; i ++ ){
//            while (cursor.getPosition() <5){
        while (m < 12) {
            data[2] += " " + cursor.getString(m);
            Log.i("Michael - TestDatabase", "m is " + m);
//                Log.i("Michael - TestDatabase", "i is " + 0);
            Log.i("Michael - TestDatabase", Arrays.toString(data));
            m++;

        }

        m=1;

        cursor.moveToPosition(3);

////        for (int i = 0 ; i < 5; i ++ ){
//            while (cursor.getPosition() <5){
        while (m < 12) {
            data[3] += " " + cursor.getString(m);
            Log.i("Michael - TestDatabase", "m is " + m);
//                Log.i("Michael - TestDatabase", "i is " + 0);
            Log.i("Michael - TestDatabase", Arrays.toString(data));
            m++;

        }

        m=1;

        cursor.moveToPosition(4);

////        for (int i = 0 ; i < 5; i ++ ){
//            while (cursor.getPosition() <5){
        while (m < 12) {
            data[4] += " " + cursor.getString(m);
            Log.i("Michael - TestDatabase", "m is " + m);
//                Log.i("Michael - TestDatabase", "i is " + 0);
            Log.i("Michael - TestDatabase", Arrays.toString(data));
            m++;

        }
//
//        cursor.moveToNext();
//
//
//        while (m < 12) {
//            data[1] += " " + cursor.getString(j);
//            Log.i("Michael - TestDatabase", "m is " + m);
////            Log.i("Michael - TestDatabase", "i is " + 1);
//            Log.i("Michael - TestDatabase", Arrays.toString(data));
//            m++;
//
//        }


            }

//            if (cursor.getString(i) == null){
//                break;
//            } else{
//                data[i] = cursor.getString(0);
//                Log.i("Michael - TestDatabase", Arrays.toString(data));
//            }
        }






