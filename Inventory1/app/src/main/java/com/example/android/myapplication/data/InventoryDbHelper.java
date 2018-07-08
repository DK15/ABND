package com.example.android.myapplication.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by lhu513 on 5/8/18.
 */

public class InventoryDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "inventory.db";

    private static final int DATABASE_VERSION = 1;

    public InventoryDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_INVENTORYLIST_TABLE = "CREATE TABLE " + InventoryContract.InventoryEntry.TABLE_NAME + " (" +
            InventoryContract.InventoryEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            InventoryContract.InventoryEntry.PRODUCT_NAME + " TEXT NOT NULL, " +
            InventoryContract.InventoryEntry.PRICE + " TEXT NOT NULL, " +
            InventoryContract.InventoryEntry.QUANTITY + " TEXT NOT NULL, " +
            InventoryContract.InventoryEntry.SUPPLIER + " TEXT NOT NULL, " +
            InventoryContract.InventoryEntry.SUPP_PH_NUM + " TEXT NOT NULL " +
            "); ";
        sqLiteDatabase.execSQL(SQL_CREATE_INVENTORYLIST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + InventoryContract.InventoryEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
