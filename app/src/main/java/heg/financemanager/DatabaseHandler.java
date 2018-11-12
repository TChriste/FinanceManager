package heg.financemanager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {
    public static final String COMPTES_KEY = "id";
    public static final String COMPTES_LIBELLE = "libelle";
    public static final String COMPTES_SOLDE = "solde";

    public static final String COMPTES_TABLE_NAME = "Comptes";

    public static final String COMPTES_TABLE_CREATE =
            "CREATE TABLE " + COMPTES_TABLE_NAME + " (" +
                    COMPTES_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COMPTES_LIBELLE + " TEXT, " +
                    COMPTES_SOLDE + " REAL);";

    public static final String COMPTES_TABLE_DROP = "DROP TABLE IF EXISTS " + COMPTES_TABLE_NAME + ";";


    public DatabaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(COMPTES_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(COMPTES_TABLE_DROP);
        onCreate(db);
    }

}

