package com.example.android.myapplication.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by lhu513 on 5/11/18.
 */

public class ProductProvider extends ContentProvider {

    public static final String LOG_TAG = ProductProvider.class.getSimpleName();

    private static final int PRODUCTS = 100;

    private static final int PRODUCTS_ID = 101;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(InventoryContract.CONTENT_AUTHORITY, InventoryContract.PATH_PRODUCTS, PRODUCTS);
        sUriMatcher.addURI(InventoryContract.CONTENT_AUTHORITY, InventoryContract.PATH_PRODUCTS + "/#", PRODUCTS_ID);
    }

    private InventoryDbHelper mDbHelper;

    @Override
    public boolean onCreate() {
        mDbHelper = new InventoryDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1,
        @Nullable String s1) {

        SQLiteDatabase database = mDbHelper.getReadableDatabase();
        Cursor cursor;
        int match = sUriMatcher.match(uri);
        switch (match) {
            case PRODUCTS:
                cursor = database.query(InventoryContract.InventoryEntry.TABLE_NAME, strings, s, strings1, null, null, s1);
                break;
            case PRODUCTS_ID:
                s = InventoryContract.InventoryEntry._ID + "=?";
                strings1 = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = database.query(InventoryContract.InventoryEntry.TABLE_NAME, strings, s, strings1, null, null,
                    s1);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {

        final int match = sUriMatcher.match(uri);
        switch (match) {
            case PRODUCTS:
                return InventoryContract.InventoryEntry.CONTENT_LIST_TYPE;
            case PRODUCTS_ID:
                return InventoryContract.InventoryEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case PRODUCTS:
                return insertProduct(uri, contentValues);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    private Uri insertProduct(Uri uri, ContentValues values) {
        String name = values.getAsString(InventoryContract.InventoryEntry.PRODUCT_NAME);
        if (name == null) {
            throw new IllegalArgumentException("Product name is required !!");
        }

        Integer price = values.getAsInteger(InventoryContract.InventoryEntry.PRICE);
        if (price != null && price < 0) {
            throw new IllegalArgumentException("Product price is required !!");
        }

        Integer quantity = values.getAsInteger(InventoryContract.InventoryEntry.QUANTITY);
        if (quantity != null && quantity < 0) {
            throw new IllegalArgumentException("Product quantity is required !!");
        }

        String supplier = values.getAsString(InventoryContract.InventoryEntry.SUPPLIER);
        if (supplier == null) {
            throw new IllegalArgumentException("Supplier name is required !!");
        }
        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        long id = database.insert(InventoryContract.InventoryEntry.TABLE_NAME, null, values);
        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        final int match = sUriMatcher.match(uri);
        int rowsDeleted;

        switch (match) {
            case PRODUCTS:
                rowsDeleted = database.delete(InventoryContract.InventoryEntry.TABLE_NAME, s, strings);
                break;
            case PRODUCTS_ID:
                s = InventoryContract.InventoryEntry._ID + "=?";
                strings = new String[]{String.valueOf(ContentUris.parseId(uri))};
                rowsDeleted = database.delete(InventoryContract.InventoryEntry.TABLE_NAME, s, strings);
                break;
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }


    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case PRODUCTS:
                return updateData(uri, contentValues, s, strings);
            case PRODUCTS_ID:
                s = InventoryContract.InventoryEntry._ID + "=?";
                strings = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return updateData(uri, contentValues, s, strings);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }

    /*
    This method updates the data while editing product details. Checks validation and prompts user to enter valid data.
     */

    private int updateData(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        if (values.containsKey(InventoryContract.InventoryEntry.PRODUCT_NAME)) {
            String name = values.getAsString(InventoryContract.InventoryEntry.PRODUCT_NAME);

            if (TextUtils.isEmpty(name)) {
                Toast.makeText(getContext(), "Product name is required !", Toast.LENGTH_SHORT).show();
            }
        }

        if (values.containsKey(InventoryContract.InventoryEntry.PRICE)) {
            Integer price = values.getAsInteger(InventoryContract.InventoryEntry.PRICE);
            if (price != null && price < 0) {
                throw new IllegalArgumentException("Product price is required !!");
            }
        }

        if (values.containsKey(InventoryContract.InventoryEntry.QUANTITY)) {
            Integer quantity = values.getAsInteger(InventoryContract.InventoryEntry.QUANTITY);
            if (quantity != null && quantity < 0) {
                throw new IllegalArgumentException("Product quantity is required !!");
            }
        }

        if (values.containsKey(InventoryContract.InventoryEntry.SUPPLIER)) {
            String supplierName = values.getAsString(InventoryContract.InventoryEntry.SUPPLIER);

            if (TextUtils.isEmpty(supplierName)) {
                Toast.makeText(getContext(), "Supplier name is required !", Toast.LENGTH_SHORT).show();
            }
        }

        if (values.size() == 0) {
            return 0;
        }

        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        int rowsUpdated = database.update(InventoryContract.InventoryEntry.TABLE_NAME, values, selection, selectionArgs);
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }
}
