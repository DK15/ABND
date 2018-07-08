package com.example.android.myapplication;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.myapplication.data.InventoryContract;

/**
 * Created by lhu513 on 5/11/18.
 */

public class ProductCursorAdapter extends CursorAdapter {

    public ProductCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, viewGroup, false);
    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {
        TextView nameTextView = view.findViewById(R.id.name);
        TextView priceTextView = view.findViewById(R.id.price);
        TextView quantityTextView = view.findViewById(R.id.quantity);
        TextView supperTextView = view.findViewById(R.id.supp_label_tv);

        int nameColumnIndex = cursor.getColumnIndex(InventoryContract.InventoryEntry.PRODUCT_NAME);
        int priceColumnIndex = cursor.getColumnIndex(InventoryContract.InventoryEntry.PRICE);
        int quantityColumnIndex = cursor.getColumnIndex(InventoryContract.InventoryEntry.QUANTITY);
        final int productId = cursor.getInt(cursor.getColumnIndex(InventoryContract.InventoryEntry._ID));
        int supplierColumnIndex = cursor.getColumnIndex(InventoryContract.InventoryEntry.SUPPLIER);
        int phoneColumnIndex = cursor.getColumnIndex(InventoryContract.InventoryEntry.SUPP_PH_NUM);

        String productName = cursor.getString(nameColumnIndex);
        final String price = cursor.getString(priceColumnIndex);
        final int quantity = cursor.getInt(quantityColumnIndex);
        String supplierName = cursor.getString(supplierColumnIndex);

        nameTextView.setText(productName);
        priceTextView.setText(price);
        quantityTextView.setText(String.valueOf(quantity));

        // Logic to decrement quantity count after every Sale button click, till quantity is 0.

        Button saleButton = view.findViewById(R.id.sale);
        saleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (quantity > 0) {
                    int newQuantity = quantity - 1;
                    Uri productUri = ContentUris.withAppendedId(InventoryContract.InventoryEntry.CONTENT_URI, productId);

                    ContentValues values = new ContentValues();
                    values.put(InventoryContract.InventoryEntry.QUANTITY, newQuantity);
                    context.getContentResolver().update(productUri, values, null, null);
                    Toast.makeText(context, R.string.sale_success, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, R.string.sale_error, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

