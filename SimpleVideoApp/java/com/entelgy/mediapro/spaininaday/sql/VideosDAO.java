package com.entelgy.mediapro.spaininaday.sql;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class VideosDAO {

    public static final String TAG = "VideosDAO";

    private static VideosDAO ourInstance;
    private VideosDatabaseHelper dbHelper;

    public static synchronized VideosDAO getInstance(Context context) {
        if (ourInstance == null) {
            ourInstance = new VideosDAO(context);
        }
        return ourInstance;
    }

    public long save(Video video) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long newRowId = db.insert(
                VideosContract.VideosEntry.TABLE_NAME,
                null,
                video.toContentValues());

        return newRowId;
    }

    public List<Video> getAllVideos() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<Video> result = new ArrayList<Video>();
        String[] projection = {
                VideosContract.VideosEntry._ID,
                VideosContract.VideosEntry.COLUMN_NAME_TITLE,
                VideosContract.VideosEntry.COLUMN_NAME_CATEGORY,
                VideosContract.VideosEntry.COLUMN_NAME_SUBCATEGORY,
                VideosContract.VideosEntry.COLUMN_NAME_DESCRIPTION,
                VideosContract.VideosEntry.COLUMN_NAME_FILE_PATH,
                VideosContract.VideosEntry.COLUMN_NAME_OFFSET,
                VideosContract.VideosEntry.COLUMN_NAME_TOTAL_SIZE,
                VideosContract.VideosEntry.COLUMN_NAME_QUALITY,
                VideosContract.VideosEntry.COLUMN_NAME_SECONDS,
                VideosContract.VideosEntry.COLUMN_NAME_RESOLUTION,
                VideosContract.VideosEntry.COLUMN_NAME_URL,
                VideosContract.VideosEntry.COLUMN_NAME_USER_ID};

        // WHERE clauses
        String selection = null;
        String[] selectionArgs = {};
        // ORDER BY
        String sortOrder = VideosContract.VideosEntry._ID + " DESC";

        Cursor cursor = db.query(
                VideosContract.VideosEntry.TABLE_NAME,
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        Log.d(TAG, "Total de videos: " + cursor.getCount());

        cursor.moveToFirst();
        for (int i = 0; i < cursor.getCount(); i++) {
            result.add(new Video(cursor.getInt(cursor.getColumnIndex(VideosContract.VideosEntry._ID)),
                                 cursor.getString(cursor.getColumnIndex(VideosContract.VideosEntry.COLUMN_NAME_TITLE)),
                                 cursor.getString(cursor.getColumnIndex(VideosContract.VideosEntry.COLUMN_NAME_DESCRIPTION)),
                                 cursor.getString(cursor.getColumnIndex(VideosContract.VideosEntry.COLUMN_NAME_CATEGORY)),
                                 cursor.getString(cursor.getColumnIndex(VideosContract.VideosEntry.COLUMN_NAME_SUBCATEGORY)),
                                 cursor.getString(cursor.getColumnIndex(VideosContract.VideosEntry.COLUMN_NAME_FILE_PATH)),
                                 cursor.getInt(cursor.getColumnIndex(VideosContract.VideosEntry.COLUMN_NAME_OFFSET)),
                                 cursor.getInt(cursor.getColumnIndex(VideosContract.VideosEntry.COLUMN_NAME_TOTAL_SIZE)),
                                 cursor.getString(cursor.getColumnIndex(VideosContract.VideosEntry.COLUMN_NAME_QUALITY)),
                                 cursor.getString(cursor.getColumnIndex(VideosContract.VideosEntry.COLUMN_NAME_SECONDS)),
                                 cursor.getString(cursor.getColumnIndex(VideosContract.VideosEntry.COLUMN_NAME_RESOLUTION)),
                                 cursor.getString(cursor.getColumnIndex(VideosContract.VideosEntry.COLUMN_NAME_URL)),
                                 cursor.getString(cursor.getColumnIndex(VideosContract.VideosEntry.COLUMN_NAME_USER_ID))
                                )
            );
            cursor.moveToNext();
        }

        cursor.close();
        return result;
    }

    private VideosDAO(Context context) {
        this.dbHelper = new VideosDatabaseHelper(context);
    }

    private VideosDAO() {
    }
}
