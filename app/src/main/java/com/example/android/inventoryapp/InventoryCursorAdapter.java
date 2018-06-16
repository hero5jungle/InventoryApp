package com.example.android.inventoryapp;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.inventoryapp.data.InventoryContract.InventoryEntry;

public class InventoryCursorAdapter extends CursorAdapter {

    public InventoryCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {
        // Find individual views that we want to modify in the list item layout
        TextView nameTextView = view.findViewById(R.id.product_name);
        TextView priceTextView = view.findViewById(R.id.product_price);
        final TextView quantityTextView = view.findViewById(R.id.product_quantity);
        Button saleButton = view.findViewById(R.id.sale_button);

        // Find the columns of inventory attributes that we're interested in
        int nameColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_PRODUCT_NAME);
        int priceColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_PRICE);
        int quantityColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_QUANTITY);

        // Read the inventory attributes from the Cursor for the current inventory
        String inventoryName = cursor.getString(nameColumnIndex);
        int inventoryPrice = cursor.getInt(priceColumnIndex);
        final int[] inventoryQuantity = {cursor.getInt(quantityColumnIndex)};

        // Update the TextViews with the attributes for the current inventory
        nameTextView.setText(inventoryName);
        priceTextView.setText(Integer.toString(inventoryPrice));

        // Set listener and logic for the sale button
        saleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inventoryQuantity[0] == 0) {
                    return;
                }
                inventoryQuantity[0]--;
                quantityTextView.setText(Integer.toString(inventoryQuantity[0]));
                // Updated and Save Quantity data
                ContentResolver cr = context.getContentResolver();
                ContentValues values = new ContentValues();
                values.put(InventoryEntry.COLUMN_QUANTITY, inventoryQuantity[0]);
                cr.update(InventoryEntry.CONTENT_URI, values, null, null);
                Toast.makeText(context.getApplicationContext(), "Congrats, You made a sale!", Toast.LENGTH_SHORT).show();
            }
        });
        quantityTextView.setText(Integer.toString(inventoryQuantity[0]));
    }
}