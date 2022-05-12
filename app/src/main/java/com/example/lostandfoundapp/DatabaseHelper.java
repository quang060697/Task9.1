package com.example.lostandfoundapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(@Nullable Context context) {
        super(context, "item_db",null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_ITEM_TABLE = "CREATE TABLE ITEMS(ITEMID INTEGER PRIMARY KEY AUTOINCREMENT ,NAME TEXT,TYPE TEXT, PHONE TEXT, DESCRIPTION TEXT,DATE TEXT,LOCATION TEXT)";
        sqLiteDatabase.execSQL(CREATE_ITEM_TABLE);

    }



    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String DROP_ITEM_TABLE = "DROP TABLE IF EXISTS ITEMS";
        sqLiteDatabase.execSQL(DROP_ITEM_TABLE);
        onCreate(sqLiteDatabase);

    }
    public long insertItem(Item item)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues =new ContentValues();
        contentValues.put("NAME",item.getName());
        contentValues.put("TYPE",item.getType());
        contentValues.put("PHONE",item.getPhone());
        contentValues.put("DESCRIPTION",item.getDescription());
        contentValues.put("DATE",item.getDate());
        contentValues.put("LOCATION",item.getLocation());

        long newRow = db.insert("ITEMS",null,contentValues);
        db.close();
        return newRow;
    }
    public List<Item> fetchAllItems (){
        List<Item> itemList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selectAll = " SELECT * FROM ITEMS";
        Cursor cursor = db.rawQuery(selectAll, null);

        if (cursor.moveToFirst()) {
            do {
                Integer itemid = cursor.getInt(0);
                String name = cursor.getString(1);
                String type = cursor.getString(2);
                String phone = cursor.getString(3);
                String description = cursor.getString(4);
                String date = cursor.getString(5);
                String location = cursor.getString(6);

                Item item = new Item(itemid,name,type,phone,description,date,location);
                itemList.add(item);

            } while (cursor.moveToNext());

        }

        return itemList;
    }
    public long deleteItem(Integer itemid)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = "ITEMID=?";
        String[] selectionArgs = { String.valueOf(itemid) };

        long result = db.delete("ITEMS",whereClause,selectionArgs);
        db.close();
        return result;


    }
}
