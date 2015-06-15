package com.example.alok.test1;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by akp77 on 4/23/2015.
 */
public class PlaceDAO {
    private SQLiteDatabase database;
    private DbHelper dbHelper;
    private Resources resources;
    private static final String TABLE_NAME = "Places";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_PLACE_NAME = "placeName";
    private static final String COLUMN_LONGITUDE = "longitude";
    private static final String COLUMN_LATITUDE = "latitude";
    private static final String COLUMN_BITMAP = "bitmap";
    private static final String PACKAGE_NAME = "com.example.alok.test1";
    private static final String RESOURCE_TYPE = "drawable";

    public PlaceDAO(Context context, Resources resources) {
        dbHelper = new DbHelper(context);
        this.resources = resources;
    }

    public void open() throws SQLException{
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public List<Place> getAllPlaces() {
        List<Place> places = new ArrayList<Place>();
        String[] allColumns = {COLUMN_ID, COLUMN_PLACE_NAME, COLUMN_LONGITUDE, COLUMN_LATITUDE, COLUMN_BITMAP};
        Cursor cursor = database.query(TABLE_NAME,allColumns, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Place place = cursorToPlace(cursor);
            places.add(place);
            cursor.moveToNext();
        }

        cursor.close();
        return places;
    }

    private Place cursorToPlace(Cursor cursor) {
        Place place = new Place();
        place.setId(cursor.getInt(0));
        place.setPlaceName(cursor.getString(1));
        place.setLongitude(cursor.getDouble(2));
        place.setLatitude(cursor.getDouble(3));
        place.setBitmap(BitmapFactory.decodeResource(resources, resources.getIdentifier(cursor.getString(4),RESOURCE_TYPE, PACKAGE_NAME)));
        return place;
    }
}
