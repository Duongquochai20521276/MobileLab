package com.example.bai02;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "contactsManager";
    // Contacts table name
    private static final String TABLE_SINHVIEN = "contacts";
    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_KHOA = "khoa";
    private static final String KEY_MSSV = "mssv";
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_contacts_table = String.format("CREATE TABLE %s(%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s TEXT, %s TEXT)", TABLE_SINHVIEN, KEY_ID, KEY_NAME, KEY_KHOA, KEY_MSSV);
        db.execSQL(create_contacts_table);
    }
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SINHVIEN);
        // Create tables again
        onCreate(db);
    }
    // delete all
    public void deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_SINHVIEN);
        db.close();
    }

    // get latest id
    public int getLatestId() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_SINHVIEN, null);
        int id = 0;
        if (cursor.moveToLast()) {
            id = cursor.getInt(0);
        }
        cursor.close();
        return id;
    }

    public void addSinhVien(SinhVien sinhVien) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, sinhVien.getName());
        values.put(KEY_KHOA, sinhVien.getKhoa());
        values.put(KEY_MSSV, sinhVien.getMssv());

        db.insert(TABLE_SINHVIEN, null, values);
        db.close();
    }

    public SinhVien getStudent(int studentId) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_SINHVIEN, null, KEY_ID + " = ?", new String[] { String.valueOf(studentId) },null, null, null);
        if(cursor != null)
            cursor.moveToFirst();
        SinhVien student = new SinhVien(cursor.getString(0), cursor.getString(1), cursor.getString(2));
        return student;
    }

    public List<SinhVien> getAllSinhVien() {
        List<SinhVien> ls = new ArrayList<SinhVien>();
        String query = "SELECT * FROM " + TABLE_SINHVIEN;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        while(cursor.isAfterLast() == false) {
            SinhVien sinhVien = new SinhVien(cursor.getInt(0), cursor.getString(1), cursor.getString(2),cursor.getString(3));
            ls.add(sinhVien);
            cursor.moveToNext();
        }
        return ls;
    }

    public void updateSinhVien(SinhVien sinhVien, int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, sinhVien.getName());
        values.put(KEY_KHOA, sinhVien.getKhoa());
        values.put(KEY_MSSV, sinhVien.getMssv());
        db.update(TABLE_SINHVIEN, values, KEY_ID + " = ?", new String[] { String.valueOf(id) });
        db.close();
    }

    public void deleteSinhVien(int contactId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_SINHVIEN, KEY_ID + " = ?", new String[] { String.valueOf(contactId) });
    }

}
