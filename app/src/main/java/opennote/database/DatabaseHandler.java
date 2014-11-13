package opennote.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Bwandy on 12/11/14.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    private static final String NOTE_KEY = "id";
    private static final String NOTE_TITRE = "titre";
    private static final String NOTE_CATEGORY = "idCategory";
    private static final String NOTE_FILE = "fichier";

    private static final String NOTE_TABLE_NAME = "Note";
    private static final String NOTE_TABLE_CREATE =
            "CREATE TABLE " + NOTE_TABLE_NAME + " (" +
            NOTE_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT" +
            NOTE_TITRE + " TEXT NOT NULL" +
            NOTE_CATEGORY + " INTEGER" +
            NOTE_FILE + " BLOB NOT NULL);";
    private static final String NOTE_TABLE_DROP = "DROP TABLE IF EXIST " + NOTE_TABLE_NAME + ";";

    private static final String CATEGORY_KEY = "id";
    private static final String CATEGORY_INTITULE = "nom";
    private static final String CATEGORY_PARENT = "parent_id";

    private static final String CATEGORY_TABLE_NAME = "Category";
    private static final String CATEGORY_CREATE_TABLE =
            "CREATE TABLE " + CATEGORY_TABLE_NAME + " (" +
            CATEGORY_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT" +
            CATEGORY_INTITULE + " TEXT NOT NULL" +
            CATEGORY_PARENT + " INTEGER);";
    private static final String CATEGORY_TABLE_DROP = "DROP TABLE IF EXIST " + CATEGORY_TABLE_NAME + ";";

    public DatabaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CATEGORY_CREATE_TABLE);
        db.execSQL(NOTE_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(NOTE_TABLE_DROP);
        db.execSQL(CATEGORY_TABLE_DROP);
        onCreate(db);
    }
}
