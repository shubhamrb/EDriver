package com.example.driver.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.driver.model.LoginDetails;


/**
 * Created by Bhupesh Sen on 23-01-2021.
 */
 public  class SqliteDatabase extends SQLiteOpenHelper {
    private final String TABLE_LOGIN = "userLogin";
    private final String LOGIN_KEY_PHOTO = "photo";
    private final String LOGIN_KEY_NAME = "name";
    private final String LOGIN_KEY_EMAIL = "email";
    private final String LOGIN_KEY_PHONE = "phone";
    private final String LOGIN_KEY_SK = "sk";
    private final String LOGIN_KEY_MASTER_CAT_ID = "masterCatId";
    private final String LOGIN_KEY_USER_ID = "userId";
    private final String LOGIN_KEY_MASTER_CAT_NAME = "masterCatName";
    private final String LOGIN_KEY_HASH1 = "hash1";
    private final String LOGIN_KEY_HASH2 = "hash2";

    public SqliteDatabase(Context context) {
        super(context, "apkaadda-db-driver", null, 4);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sb = String.format("CREATE TABLE IF NOT EXISTS %s(%s VARCHAR,%s VARCHAR,%s VARCHAR,%s VARCHAR,%s VARCHAR,%s VARCHAR,%s VARCHAR,%s VARCHAR,%s VARCHAR,%s VARCHAR);", TABLE_LOGIN, LOGIN_KEY_NAME, LOGIN_KEY_EMAIL,LOGIN_KEY_PHONE,LOGIN_KEY_PHOTO, LOGIN_KEY_HASH1,LOGIN_KEY_HASH2,LOGIN_KEY_USER_ID,LOGIN_KEY_MASTER_CAT_ID,LOGIN_KEY_MASTER_CAT_NAME,LOGIN_KEY_SK);
        db.execSQL(sb);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGIN);
        onCreate(db);
    }

    public void removeLoginUser() {
        SQLiteDatabase db = getDB();
        db.execSQL("DELETE FROM " + TABLE_LOGIN);
    }

    public void addLogin(LoginDetails login) {
        SQLiteDatabase db = getDB();
        ContentValues cv = new ContentValues();
        cv.put(LOGIN_KEY_NAME, login.getName());
        cv.put(LOGIN_KEY_EMAIL, login.getEmail());
        cv.put(LOGIN_KEY_PHONE, login.getNumber());
        cv.put(LOGIN_KEY_PHOTO, login.getPhoto());
        cv.put(LOGIN_KEY_SK, login.getSk());
        cv.put(LOGIN_KEY_USER_ID, login.getUser_id());
        cv.put(LOGIN_KEY_MASTER_CAT_ID, login.getMasterCatId());
        cv.put(LOGIN_KEY_MASTER_CAT_NAME, login.getMastercatname());
        cv.put(LOGIN_KEY_HASH1, "");
        cv.put(LOGIN_KEY_HASH2, "");
        db.insert(TABLE_LOGIN, null, cv);
    }

    void updateLogin(LoginDetails login) {
        SQLiteDatabase db = getDB();
        ContentValues cv = new ContentValues();
        cv.put(LOGIN_KEY_NAME, login.getName());
        cv.put(LOGIN_KEY_EMAIL, login.getEmail());
        cv.put(LOGIN_KEY_PHONE, login.getNumber());
        cv.put(LOGIN_KEY_SK, login.getSk());
        cv.put(LOGIN_KEY_USER_ID, login.getUser_id());
        cv.put(LOGIN_KEY_MASTER_CAT_ID, login.getMasterCatId());
        cv.put(LOGIN_KEY_MASTER_CAT_NAME, login.getMastercatname());
        cv.put(LOGIN_KEY_PHOTO, login.getPhoto());
        db.update(TABLE_LOGIN, cv, null, null);
    }

    public LoginDetails getLogin() {
        LoginDetails login = null;
        SQLiteDatabase db = getDB();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_LOGIN, null);
        if (cursor.moveToNext()) {
                 login = new LoginDetails(cursor.getString(cursor.getColumnIndex(LOGIN_KEY_USER_ID)),cursor.getString(cursor.getColumnIndex(LOGIN_KEY_MASTER_CAT_ID)), cursor.getString(cursor.getColumnIndex(LOGIN_KEY_MASTER_CAT_NAME)),cursor.getString(cursor.getColumnIndex(LOGIN_KEY_NAME)), cursor.getString(cursor.getColumnIndex(LOGIN_KEY_EMAIL)), cursor.getString(cursor.getColumnIndex(LOGIN_KEY_PHONE)),  cursor.getString(cursor.getColumnIndex(LOGIN_KEY_SK)),cursor.getString(cursor.getColumnIndex(LOGIN_KEY_HASH1)), cursor.getString(cursor.getColumnIndex(LOGIN_KEY_HASH2)), cursor.getString(cursor.getColumnIndex(LOGIN_KEY_PHOTO)));



        }
//        if (cursor.moveToNext()) {
//            login = new LoginDetails(cursor.getString(cursor.getColumnIndex(LOGIN_KEY_NAME)), cursor.getString(cursor.getColumnIndex(LOGIN_KEY_EMAIL)), cursor.getString(cursor.getColumnIndex(LOGIN_KEY_PHONE)), cursor.getString(cursor.getColumnIndex(LOGIN_KEY_HASH1)), cursor.getString(cursor.getColumnIndex(LOGIN_KEY_HASH2)), cursor.getString(cursor.getColumnIndex(LOGIN_KEY_PHOTO)));
//        }
        cursor.close();
        return login;
    }

    public void dropDB() {
        SQLiteDatabase db = getDB();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGIN);
    }

    private SQLiteDatabase getDB() {
        return this.getWritableDatabase();
    }
}
