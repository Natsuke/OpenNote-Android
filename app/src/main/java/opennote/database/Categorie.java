package opennote.database;

import android.provider.BaseColumns;

/**
 * Created by Bwandy on 20/11/14.
 */
public final class Categorie {

    public Categorie() {}

    public static abstract class Cat implements BaseColumns{
        public static final String TABLE_NAME = "Categorie";
        public static final String COLUMN_NAME_CAT_ID = "id";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_USER_ID = "user_id";

        private static final String TEXT_TYPE = " TEXT";
        private static final String COMA_SEP = ",";
        private static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + TABLE_NAME + "(" +
                COLUMN_NAME_CAT_ID + " INTEGER PRIMARY KEY," +
                COLUMN_NAME_TITLE + TEXT_TYPE + COMA_SEP +
                COLUMN_NAME_USER_ID + " INTEGER";
        private static final String SQL_DELETE_QUERY =
                "DROP TABLE IF EXIST " + TABLE_NAME;
    }
}
