package com.example.alok.test1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Created by akp77 on 4/23/2015.
 */
public class DbHelper extends SQLiteAssetHelper {
    private static final String DATABASE_NAME = "visitcornell.db";
    private static final int DATABASE_VERSION = 1;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
}
