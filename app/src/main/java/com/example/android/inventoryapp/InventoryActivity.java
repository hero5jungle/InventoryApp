package com.example.android.inventoryapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.android.inventoryapp.data.InventoryDbHelper;
import com.example.android.inventoryapp.data.InventoryContract.InventoryEntry;

public class InventoryActivity extends AppCompatActivity {

    private InventoryDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDbHelper = new InventoryDbHelper(this);
        insertData();
        queryData();
    }

    private void insertData() {
        // Insert into database.
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(InventoryEntry.COLUMN_PRODUCT_NAME, "Laptop");
        values.put(InventoryEntry.COLUMN_PRICE, 500);
        values.put(InventoryEntry.COLUMN_QUANTITY, 1);
        values.put(InventoryEntry.COLUMN_SUPPLIER_NAME, "Dell");
        values.put(InventoryEntry.COLUMN_SUPPLIER_PHONE_NUMBER, "714-213-5486");

        long newRowId = db.insert(InventoryEntry.TABLE_NAME, null, values);
    }

    private Cursor queryData() {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                InventoryEntry._ID,
                InventoryEntry.COLUMN_PRODUCT_NAME,
                InventoryEntry.COLUMN_PRICE,
                InventoryEntry.COLUMN_QUANTITY,
                InventoryEntry.COLUMN_SUPPLIER_NAME,
                InventoryEntry.COLUMN_SUPPLIER_PHONE_NUMBER};

        Cursor cursor = db.query(
                InventoryEntry.TABLE_NAME,
                projection,
                null, null,
                null, null,
                null);

        TextView displayView = findViewById(R.id.data_text);
        displayView.append(InventoryEntry._ID + " - " +
                InventoryEntry.COLUMN_PRODUCT_NAME + " - " +
                InventoryEntry.COLUMN_PRICE + " - " +
                InventoryEntry.COLUMN_QUANTITY + " - " +
                InventoryEntry.COLUMN_SUPPLIER_NAME + " - " +
                InventoryEntry.COLUMN_SUPPLIER_PHONE_NUMBER + "\n");


        int idColumnIndex = cursor.getColumnIndex(InventoryEntry._ID);
        int nameColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_PRODUCT_NAME);
        int priceColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_PRICE);
        int quantityColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_QUANTITY);
        int supplierNameColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_SUPPLIER_NAME);
        int supplierPhoneColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_SUPPLIER_PHONE_NUMBER);

        // Iterate through all the returned rows in the cursor
        while (cursor.moveToNext()) {

            int currentID = cursor.getInt(idColumnIndex);
            String currentName = cursor.getString(nameColumnIndex);
            int currentPrice = cursor.getInt(priceColumnIndex);
            int currentQuantity = cursor.getInt(quantityColumnIndex);
            String currentSupplierName = cursor.getString(supplierNameColumnIndex);
            String currentSupplierPhone = cursor.getString(supplierPhoneColumnIndex);
            // Display the values from each column of the current row in the cursor in the TextView
            displayView.append(("\n" + currentID + " - " +
                    currentName + " - " +
                    currentPrice + " - " +
                    currentQuantity + " - " +
                    currentSupplierName + " - " +
                    currentSupplierPhone));
        }
        cursor.close();
        return cursor;
    }
}
