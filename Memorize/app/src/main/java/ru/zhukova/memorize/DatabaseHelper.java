package ru.zhukova.memorize;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "cardstore.db"; // название бд
    private static final int SCHEMA = 1; // версия базы данных
    static final String TABLE = "cards"; // название таблицы в бд
    // названия столбцов
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_FRONT = "front";
    public static final String COLUMN_REVERSE = "reverse";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE cards (" + COLUMN_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_FRONT
                + " TEXT, " + COLUMN_REVERSE + " INTEGER);");
        // добавление начальных данных
        db.execSQL("INSERT INTO "+ TABLE +" (" + COLUMN_FRONT
                + ", " + COLUMN_REVERSE  + ") VALUES ('СЛОВО', 'WORD');");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,  int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE);
        onCreate(db);
    }
}
