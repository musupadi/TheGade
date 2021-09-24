package com.ascendant.thegade.SharedPreferance;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DB_Helper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "thegade.db";
    private static final int DATABASE_VERSION = 1;
    //Account
    public static final String TABLE_NAME_ACCOUNT = "account";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_NAMA = "nama";
    public static final String COLUMN_STATUS = "status";
    public static final String COLUMN_NIK = "nik";

    public DB_Helper(Context context){super(
            context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+TABLE_NAME_ACCOUNT+" (" +
                COLUMN_ID+" TEXT NOT NULL, "+
                COLUMN_USERNAME+" TEXT NOT NULL, "+
                COLUMN_NAMA+" TEXT NOT NULL, "+
                COLUMN_STATUS+" TEXT NOT NULL, "+
                COLUMN_NIK+" TEXT NOT NULL);"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_ACCOUNT);
        this.onCreate(db);
    }

    //Save
    public void SaveUser(String ID,String username,String nama,String status,String nik){
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, ID);
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_NAMA, nama);
        values.put(COLUMN_STATUS, status);
        values.put(COLUMN_NIK, nik);
        db.insert(TABLE_NAME_ACCOUNT,null,values);
        db.close();
    }

    //CHECKER
    public Cursor checkUser(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query ="SELECT * FROM "+TABLE_NAME_ACCOUNT;
        Cursor cursor = db.rawQuery(query,null);
        return cursor;
    }
    //delete
    public void Logout(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+TABLE_NAME_ACCOUNT+"");
    }
}
