package com.example.android.myapplication;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.myapplication.data.InventoryContract;


/**
 * Created by lhu513 on 5/12/18.
 */

// This class edits product details and is instantiated by tapping on list item.

public class EditProductActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>, View.OnClickListener {

    private static final int EXISTING_PRDT_LOADER = 0;

    // declare all views displayed on edit product screen.

    EditText mProductName;

    EditText mPrice;

    EditText mQuantity;

    EditText mSupplier;

    EditText mPhone;

    Button increment;

    Button decrement;

    Button phoneButton;

    private Uri mCurrentProductUri;

    private boolean mProductHasChanged = false;

    private View.OnTouchListener mTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            mProductHasChanged = true;
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editproduct_layout);

        //Get the URI of selected product.
        Intent intent = getIntent();
        if (intent != null) {
            mCurrentProductUri = intent.getData();
            if (mCurrentProductUri == null) {
                setTitle(R.string.add_product);
                //Hide actions not required for this mode
                invalidateOptionsMenu();
            } else {
                setTitle(R.string.edit_product);
                //start loader to get data of existing product
                getLoaderManager().initLoader(EXISTING_PRDT_LOADER, null, this);
            }
        }

        // instantiate all view objects here and set touch listener.

        mProductName = (EditText) findViewById(R.id.edit_product_layout);
        mPrice = (EditText) findViewById(R.id.edit_price_layout);
        mQuantity = (EditText) findViewById(R.id.edit_qty_layout);
        mSupplier = (EditText) findViewById(R.id.edit_supplier_name);
        mPhone = (EditText) findViewById(R.id.edit_phone_layout);
        increment = (Button) findViewById(R.id.increment);
        decrement = (Button) findViewById(R.id.decrement);
        phoneButton = (Button) findViewById(R.id.call);

        mProductName.setOnTouchListener(mTouchListener);
        mPrice.setOnTouchListener(mTouchListener);
        mQuantity.setOnTouchListener(mTouchListener);
        mSupplier.setOnTouchListener(mTouchListener);
        mPhone.setOnTouchListener(mTouchListener);

        increment.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                try {
                    String currentQuantity = mQuantity.getText().toString();
                    int value = Integer.parseInt(currentQuantity);
                    value = value + 1;
                    mQuantity.setText(String.valueOf(value));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        decrement.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                try {
                    String currentQuantity = mQuantity.getText().toString();
                    int value = Integer.parseInt(currentQuantity);
                    value = value - 1;
                    if (value == 0) {
                        return;
                    }
                    mQuantity.setText(String.valueOf(value));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });

        Button callButton = (Button) phoneButton;
        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNumber = mPhone.getText().toString().trim();
                if (TextUtils.isEmpty(phoneNumber)) {
                    return;
                }
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + phoneNumber));
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

        //Hide delete action if mode is "Add new product"
        if (mCurrentProductUri == null) {
            MenuItem menuItem = menu.findItem(R.id.action_delete);
            menuItem.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                saveData();
                return true;
            case R.id.action_delete:
                showDeleteDialog();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /*
    This method shows Alert dialog after user taps on Delete menu action and before deleting an item.
     */

    private void showDeleteDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.alert_title);
        builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                deleteData();
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
    This method deletes the data shown on Edit Product screen after user selects Delete from Alert dialog.
     */

    private void deleteData() {
        if (mCurrentProductUri != null) {
            int rowsDeleted = getContentResolver().delete(mCurrentProductUri, null, null);
            if (rowsDeleted == 0) {
                Toast.makeText(this, R.string.product_delete_error, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, R.string.product_delete_success, Toast.LENGTH_SHORT).show();
            }
            finish();
        }
    }

    /*
    This method saves / adds new product data into the InventoryList table.
     */

    private void saveData() {

        // Store the product details into string variables.

        String productString = mProductName.getText().toString().trim();
        String priceString = mPrice.getText().toString().trim();
        String qtyString = mQuantity.getText().toString().trim();
        String suppString = mSupplier.getText().toString().trim();
        String phString = mPhone.getText().toString().trim();

        // check for empty validations

        if (TextUtils.isEmpty(productString) || TextUtils.isEmpty(priceString) || TextUtils.isEmpty(qtyString) || TextUtils.isEmpty
            (phString) || TextUtils.isEmpty(suppString)) {
            Toast.makeText(this, R.string.validation_msg, Toast.LENGTH_SHORT).show();
            return;
        } else {

            ContentValues values = new ContentValues();
            values.put(InventoryContract.InventoryEntry.PRODUCT_NAME, productString);
            values.put(InventoryContract.InventoryEntry.PRICE, priceString);
            values.put(InventoryContract.InventoryEntry.QUANTITY, qtyString);
            values.put(InventoryContract.InventoryEntry.SUPPLIER, suppString);
            values.put(InventoryContract.InventoryEntry.SUPP_PH_NUM, phString);

            if (mCurrentProductUri == null) {
                Uri uri = getContentResolver().insert(InventoryContract.InventoryEntry.CONTENT_URI, values);
                if (uri == null) {
                    Toast.makeText(this, R.string.product_insert_error, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, R.string.product_insert_success, Toast.LENGTH_SHORT).show();
                }
            } else {
                int rows = getContentResolver().update(InventoryContract.InventoryEntry.CONTENT_URI, values, null, null);
                if (rows == 0) {
                    Toast.makeText(this, R.string.product_update_error, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, R.string.product_update_success, Toast.LENGTH_SHORT).show();
                }
            }
            finish();
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String[] columns = {InventoryContract.InventoryEntry._ID, InventoryContract.InventoryEntry.PRODUCT_NAME, InventoryContract
            .InventoryEntry.PRICE, InventoryContract
            .InventoryEntry.QUANTITY,
            InventoryContract.InventoryEntry.SUPPLIER,
            InventoryContract.InventoryEntry.SUPP_PH_NUM};

        return new CursorLoader(this, mCurrentProductUri, columns, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (cursor.moveToFirst()) {
            int productNameIndex = cursor.getColumnIndex((InventoryContract.InventoryEntry.PRODUCT_NAME));
            int priceIndex = cursor.getColumnIndex(InventoryContract.InventoryEntry.PRICE);
            int quantityIndex = cursor.getColumnIndex(InventoryContract.InventoryEntry.QUANTITY);
            int supplierIndex = cursor.getColumnIndex(InventoryContract.InventoryEntry.SUPPLIER);
            int phoneIndex = cursor.getColumnIndex(InventoryContract.InventoryEntry.SUPP_PH_NUM);

            String product = cursor.getString(productNameIndex);
            String price = cursor.getString(priceIndex);
            String quantity = cursor.getString(quantityIndex);
            String supplier = cursor.getString(supplierIndex);
            String phone = cursor.getString(phoneIndex);

            mProductName.setText(product);
            mPrice.setText(price);
            mQuantity.setText(quantity);
            mSupplier.setText(supplier);
            mPhone.setText(phone);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mProductName.setText("");
        mPrice.setText("");
        mQuantity.setText("");
        mSupplier.setText("");
        mPhone.setText("");
    }

    @Override
    public void onClick(View view) {

    }
}