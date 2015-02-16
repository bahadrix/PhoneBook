package me.bahadir.bil425.dbsample;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by bahadir on 09/02/15.
 */
public class DBHelper extends SQLiteOpenHelper {
    Context context;

    public DBHelper(Context context) {
        super(context, "sample.db", null, 2);
        this.context = context;
    }

    public void createDB(SQLiteDatabase db) {
        Log.i("db","Creating db");

        String[] sqlStatements = context.getResources().getStringArray(R.array.dbcreate);

        db.beginTransaction();

        try {
            for (String st : sqlStatements) {
                db.execSQL(st);
            }
            db.setTransactionSuccessful();
        } catch(Exception e) {
            Log.e("DB Creation error", e.getMessage());
        } finally {
            db.endTransaction();
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createDB(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        createDB(db);
    }
}
