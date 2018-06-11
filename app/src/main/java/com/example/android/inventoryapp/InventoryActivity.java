package com.example.android.inventoryapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.android.inventoryapp.data.InventoryContract;
import com.example.android.inventoryapp.data.InventoryDbHelper;
import com.example.android.inventoryapp.data.InventoryContract.InventoryEntry;

import org.w3c.dom.Text;

public class InventoryActivity extends AppCompatActivity {

    private InventoryDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void insertData(){
        // Insert into database.
    }

    private Cursor queryData(){
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

        TextView displayView = (TextView) findViewById(R.id.text_view);
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
            // Use that index to extract the String or Int value of the word
            // at the current row the cursor is on.
            int currentID = cursor.getInt(idColumnIndex);
            String currentName = cursor.getString(nameColumnIndex);
            int currentPrice = cursor.getInt(priceColumnIndex);
            int currentQuantity = cursor.getInt(quantityColumnIndex);
            String currentSupplierName = cursor.getString(supplierNameColumnIndex);
            int currentSupplierPhone = cursor.getInt(supplierPhoneColumnIndex);
            // Display the values from each column of the current row in the cursor in the TextView
            displayView.append(("\n" + currentID + " - " +
                    currentName + " - " +
                    currentPrice + " - " +
                    currentQuantity + " - " +
                    currentSupplierName + " - " +
                    currentSupplierPhone));
        }
        cursor.close();

    }
}
