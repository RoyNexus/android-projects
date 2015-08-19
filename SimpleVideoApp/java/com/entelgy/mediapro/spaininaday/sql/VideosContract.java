package com.entelgy.mediapro.spaininaday.sql;

import android.provider.BaseColumns;

public final class VideosContract {
    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public VideosContract() {}

    /* Inner class that defines the table contents */
    public static abstract class VideosEntry implements BaseColumns {
        public static final String TABLE_NAME = "videos";
        public static final String COLUMN_NAME_TITLE = "titulo";
        public static final String COLUMN_NAME_CATEGORY = "categoria";
        public static final String COLUMN_NAME_SUBCATEGORY = "subcategoria";
        public static final String COLUMN_NAME_DESCRIPTION = "descripcion";
        public static final String COLUMN_NAME_FILE_PATH = "file_path";
        public static final String COLUMN_NAME_OFFSET = "offset";
        public static final String COLUMN_NAME_TOTAL_SIZE = "total_size";
        public static final String COLUMN_NAME_USER_ID = "user_id";
        public static final String COLUMN_NAME_URL = "url";
        public static final String COLUMN_NAME_QUALITY = "calidad";
        public static final String COLUMN_NAME_SECONDS = "segundos";
        public static final String COLUMN_NAME_RESOLUTION = "resolucion";

    }
}