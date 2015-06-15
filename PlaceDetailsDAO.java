package com.example.alok.test1;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by akp77 on 4/24/2015.
 */
public class PlaceDetailsDAO {
    private SQLiteDatabase database;
    private DbHelper dbHelper;

    private static final String TABLE_NAME = "PlaceDetails";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_PRIMARY_TEXT = "primaryText";
    private static final String COLUMN_SECONDARY_TEXT = "secondaryText";
    private static final String COLUMN_MAIN_TEXT = "mainText";
    private static final String COLUMN_IMAGE = "image";
    private static final String COLUMN_INMEMOIR = "inMemoir";

    public PlaceDetailsDAO(Context context) {
        dbHelper = new DbHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public List<PlaceDetails> getAllPlaceDetails() {
        List<PlaceDetails> placeDetailsList = new ArrayList<PlaceDetails>();
        String[] allColumns = {COLUMN_ID, COLUMN_PRIMARY_TEXT, COLUMN_SECONDARY_TEXT, COLUMN_IMAGE, COLUMN_INMEMOIR, COLUMN_MAIN_TEXT};
        Cursor cursor = database.query(TABLE_NAME,allColumns, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            placeDetailsList.add(cursorToPlaceDetails(cursor));
            cursor.moveToNext();
        }
        cursor.close();
        return placeDetailsList;
    }

    public PlaceDetails getPlaceDetail(int placeId) {
        PlaceDetails placeDetails = null;
        String whereClause = COLUMN_ID+"="+placeId;
        String[] allColumns = {COLUMN_ID, COLUMN_PRIMARY_TEXT, COLUMN_SECONDARY_TEXT, COLUMN_IMAGE, COLUMN_INMEMOIR, COLUMN_MAIN_TEXT};
        Cursor cursor = database.query(TABLE_NAME,allColumns, whereClause, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            placeDetails = cursorToPlaceDetails(cursor);
            cursor.moveToNext();
        }
        cursor.close();
        return placeDetails;
    }

    public void enableMemoir(int placeId) {
        String whereClause = COLUMN_ID+"="+placeId;
        ContentValues newValue = new ContentValues();
        newValue.put(COLUMN_INMEMOIR,1);
        database.update(TABLE_NAME, newValue, whereClause, null);
    }

    private PlaceDetails cursorToPlaceDetails(Cursor cursor) {
        PlaceDetails placeDetails = new PlaceDetails();
        placeDetails.setPlaceId(cursor.getInt(0));
        placeDetails.setPrimaryText(cursor.getString(1));
        placeDetails.setSecondaryText(cursor.getString(2));
        placeDetails.setImage(cursor.getString(3));
        placeDetails.setInMemoir(cursor.getInt(4));
        placeDetails.setMainText(cursor.getString(5));
        return placeDetails;
    }

}
