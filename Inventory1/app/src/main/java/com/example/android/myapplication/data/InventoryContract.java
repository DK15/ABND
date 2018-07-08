package com.example.android.myapplication.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by lhu513 on 5/8/18.
 */

public class InventoryContract {

    public static final String CONTENT_AUTHORITY = "com.example.android.myapplication";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_PRODUCTS = "myapplication";

    private InventoryContract() {

    }

    public static final class InventoryEntry implements BaseColumns {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_PRODUCTS);

        public static final String CONTENT_LIST_TYPE =
            ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PRODUCTS;

        public static final String CONTENT_ITEM_TYPE =
            ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PRODUCTS;

        public static final String TABLE_NAME = "InventoryList";

        public static final String _ID = BaseColumns._ID;

        public static final String PRODUCT_NAME = "productName";

        public static final String PRICE = "price";

        public static final String QUANTITY = "qty";

        public static final String SUPPLIER = "supplier";

        public static final String SUPP_PH_NUM = "phoneNumber";
    }

}
