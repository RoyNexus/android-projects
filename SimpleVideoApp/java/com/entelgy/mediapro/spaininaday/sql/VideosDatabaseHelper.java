package com.entelgy.mediapro.spaininaday.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class VideosDatabaseHelper extends SQLiteOpenHelper {

    public static final String TAG = "VideosDatabaseHelper";
    public static final int DATABASE_VERSION = 3;
    public static final String DATABASE_NAME = "Spaininaday.db";

    private static final String TEXT_TYPE = " TEXT";
    private static final String INT_TYPE = " INTEGER";
    private static final String COMMA_SEP = ",";

    private static final String SQL_DROP = "DROP TABLE IF EXISTS " + VideosContract.VideosEntry.TABLE_NAME;
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + VideosContract.VideosEntry.TABLE_NAME + " (" +
                    VideosContract.VideosEntry._ID + INT_TYPE + " PRIMARY KEY AUTOINCREMENT" + COMMA_SEP +
                    VideosContract.VideosEntry.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP +
                    VideosContract.VideosEntry.COLUMN_NAME_CATEGORY + TEXT_TYPE + COMMA_SEP +
                    VideosContract.VideosEntry.COLUMN_NAME_SUBCATEGORY + TEXT_TYPE + COMMA_SEP +
                    VideosContract.VideosEntry.COLUMN_NAME_DESCRIPTION + TEXT_TYPE + COMMA_SEP +
                    VideosContract.VideosEntry.COLUMN_NAME_FILE_PATH + TEXT_TYPE + COMMA_SEP +
                    VideosContract.VideosEntry.COLUMN_NAME_OFFSET + INT_TYPE + COMMA_SEP +
                    VideosContract.VideosEntry.COLUMN_NAME_TOTAL_SIZE + INT_TYPE + COMMA_SEP +
                    VideosContract.VideosEntry.COLUMN_NAME_QUALITY + TEXT_TYPE + COMMA_SEP +
                    VideosContract.VideosEntry.COLUMN_NAME_SECONDS + TEXT_TYPE + COMMA_SEP +
                    VideosContract.VideosEntry.COLUMN_NAME_RESOLUTION + TEXT_TYPE + COMMA_SEP +
                    VideosContract.VideosEntry.COLUMN_NAME_URL + TEXT_TYPE + COMMA_SEP +
                    VideosContract.VideosEntry.COLUMN_NAME_USER_ID + TEXT_TYPE +
            " )";

    public VideosDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "onCreate");
        Log.d(TAG, "Executing SQL CREATE: " + SQL_CREATE_ENTRIES);
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "onUpgrade");
        Log.d(TAG, "Executing SQL UPGRADE: " + SQL_DROP);
        db.execSQL(SQL_DROP);
        onCreate(db);
    }

}
