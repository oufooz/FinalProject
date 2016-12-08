package com.example.omaro.maptest;

/**
 * Created by omaro on 11/28/2016.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Pair;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by instructor on 10/21/2016.
 */

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lanceuppercut on 10/21/2016.
 */


// Our helper class used to make interacting with the DB easier
public class SQLhelper extends SQLiteOpenHelper {
        public static final String DATABASE_NAME = "ExampleDB.db";
        public static final String LOCATION_TABLE_NAME = "Locations";
        public static final String LOCATION_COLUMN_ID = "id";
        public static final String LOCATION_COLUMN_NICK =  "nick";
        public static final String LOCATION_COLUMN_LONG = "long";
        public static final String LOCATION_COLUMN_LAT = "lat";
         
        // Superclass constructor that requires the application context.
        // Note: you can modify it and pass your own DB name.
        // However, I didn't do it that way. I left it embedded in the helper.
        public SQLhelper(Context context) {
                super(context, DATABASE_NAME, null, 1);
        }

        // OnCreate method. Called when helper is created for the first time.
        @Override
        public void onCreate(SQLiteDatabase db) {

                // Note: CREATE TABLE IF NOT EXISTS might be better, right?
                // Otherwise, run risk of deleting your table.
                // Think about that whne doing your HW.

                // Note: do not use AUTO INCREMENT on a primary key integer
                db.execSQL("CREATE TABLE " + LOCATION_TABLE_NAME + " (" + LOCATION_COLUMN_ID + " INTEGER PRIMARY KEY NOT NULL, " +
                        LOCATION_COLUMN_NICK + " text, " + LOCATION_COLUMN_LAT + " BLOB, " + LOCATION_COLUMN_LONG + "  BLOB );");
        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                // Do stuff
                // Generally in here you want some event handling for the new versions
                // However, I do not anticipate needing this for your assignment
                // For your project, maybe? Look into it if need be.
        }
        public void updateEntry(String OldNick,String Nick, double LAT, double LONG){
                SQLiteDatabase db= this.getWritableDatabase();
                ContentValues content = new ContentValues();
                content.put(LOCATION_COLUMN_NICK,Nick);
                content.put(LOCATION_COLUMN_LAT,LAT);
                content.put(LOCATION_COLUMN_LONG,LONG);
                db.update(LOCATION_TABLE_NAME,content, LOCATION_COLUMN_NICK + "='" + OldNick+"'",null);
        }
        // Custom method to insert stuff
        public void insertEntry(String Nick, double LAT, double LONG){
                SQLiteDatabase db= this.getWritableDatabase();
                ContentValues content = new ContentValues();
                content.put(LOCATION_COLUMN_NICK,Nick);
                content.put(LOCATION_COLUMN_LAT,LAT);
                content.put(LOCATION_COLUMN_LONG,LONG);
                db.insert(LOCATION_TABLE_NAME,null,content);
        }


        // Custom method to return entries
        public Cursor getEntryById(int id){
                SQLiteDatabase db = this.getReadableDatabase();
                Cursor cursor = db.rawQuery("SELECT * FROM " + LOCATION_TABLE_NAME + " WHERE " + LOCATION_COLUMN_ID + "='" + id+ "'",null);
                return cursor;
        }
        public Cursor getEntryByNick(String nick){
                SQLiteDatabase db = this.getReadableDatabase();
                Cursor cursor = db.rawQuery("SELECT * FROM " + LOCATION_TABLE_NAME + " WHERE " + LOCATION_COLUMN_NICK + "= '" + nick + "' ",null);
                return cursor;
        }
        public Integer getIdByNick(String nick){
                SQLiteDatabase db = this.getReadableDatabase();
                Cursor cursor = db.rawQuery("SELECT * FROM " + LOCATION_TABLE_NAME + " WHERE " + LOCATION_COLUMN_NICK + "= '" + nick + "' ",null);
                cursor.moveToFirst();
                return cursor.getColumnIndex(LOCATION_COLUMN_ID);
        }

        public LatLng getEntryByNickLatLong(String nick)
        {
                Cursor cursor = getEntryByNick(nick);
                cursor.moveToFirst();
                return new LatLng(Double.parseDouble(cursor.getString(cursor.getColumnIndex(LOCATION_COLUMN_LAT))),
                                  Double.parseDouble(cursor.getString(cursor.getColumnIndex(LOCATION_COLUMN_LONG))));
        }
        public Pair<String,LatLng> getEntireEntry(String nick)
        {
                Cursor cursor = getEntryByNick(nick);
                cursor.moveToFirst();
                return new Pair<String,LatLng>(cursor.getString(cursor.getColumnIndex(LOCATION_COLUMN_NICK)) ,
                        new LatLng(Double.parseDouble(cursor.getString(cursor.getColumnIndex(LOCATION_COLUMN_LAT))),
                                   Double.parseDouble(cursor.getString(cursor.getColumnIndex(LOCATION_COLUMN_LONG)))));
        }

        public ArrayList<String> getEntireDataBase(){
                SQLiteDatabase db = this.getReadableDatabase();
                Cursor cursor = db.rawQuery("SELECT * FROM " + LOCATION_TABLE_NAME,null);
                cursor.moveToFirst();

                ArrayList<String> values = new ArrayList<>();

                while (cursor.moveToNext()){
                        String value = (String) cursor.getString(cursor.getColumnIndex(LOCATION_COLUMN_NICK)) ;
                        values.add(value);
                }

                return values;
        }

        public ArrayList<Pair<String,LatLng>>getEntireDataBaseMap(){
                ArrayList<Pair<String,LatLng>> temp = new ArrayList<>();
                SQLiteDatabase db = this.getReadableDatabase();

                Cursor cursor = db.rawQuery("SELECT * FROM " + LOCATION_TABLE_NAME,null);
                cursor.moveToFirst();

                while (cursor.moveToNext()){
                        temp.add(new Pair<String,LatLng>(cursor.getString(cursor.getColumnIndex("nick")),
                        new LatLng(Double.parseDouble(cursor.getString(cursor.getColumnIndex(LOCATION_COLUMN_LAT))),
                                        Double.parseDouble(cursor.getString(cursor.getColumnIndex(LOCATION_COLUMN_LONG))))));
                }

                return temp;
        }

        // Custom SQL query
        public Cursor customQuery(String query){
                SQLiteDatabase db = this.getReadableDatabase();
                Cursor cursor = db.rawQuery(query,null);
                return cursor;
        }

}
