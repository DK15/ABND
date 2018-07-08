package com.example.android.myapplication;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.android.myapplication.data.InventoryContract.InventoryEntry;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int PRODUCT_LOADER = 0;

    private ProductCursorAdapter productCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EditProductActivity.class);
                startActivity(intent);
            }
        });

        // Define listview that displays all products in MainActivity class.

        ListView inventoryView = (ListView) findViewById(R.id.list);

        View emptyView = findViewById(R.id.empty_view);
        inventoryView.setEmptyView(emptyView);

        // Declare adapter and set listview as data source

        productCursorAdapter = new ProductCursorAdapter(this, null);
        inventoryView.setAdapter(productCursorAdapter);

        inventoryView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, EditProductActivity.class);
                Uri currentProductUri = ContentUris.withAppendedId(InventoryEntry.CONTENT_URI, id);
                intent.setData(currentProductUri);
                startActivity(intent);
            }
        });

        getLoaderManager().initLoader(PRODUCT_LOADER, null, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the Options Menu we specified in XML
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete_all_entries:
                showDeleteDialog();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /*
    This method presents Alert dialog to user after selecting Delete all option from menu.
     */

    private void showDeleteDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.alert_title);
        builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                deleteAllData();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    /*
    This method deletes all data from the listview after user selects Delete from Alert dialog.
     */

    private void deleteAllData() {

        int rowsToDelete = getContentResolver().delete(InventoryEntry.CONTENT_URI, null, null);
        if (rowsToDelete == 0) {
            Toast.makeText(this, R.string.delete_all_success + rowsToDelete, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, R.string.delete_all_error + rowsToDelete, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public android.content.Loader<Cursor> onCreateLoader(int i, Bundle bundle) {

        String[] projection = {
            InventoryEntry._ID,
            InventoryEntry.PRODUCT_NAME,
            InventoryEntry.PRICE,
            InventoryEntry.QUANTITY,
            InventoryEntry.SUPPLIER,
            InventoryEntry.SUPP_PH_NUM};

        return new CursorLoader(this,
            InventoryEntry.CONTENT_URI,
            projection,
            null,
            null,
            null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        productCursorAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        productCursorAdapter.swapCursor(null);
    }
}
