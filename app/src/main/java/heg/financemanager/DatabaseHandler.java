package heg.financemanager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {

    // Compte
    // -------------------
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
    // ---------------------

    // Catégorie
    // -----------------
    public static final String CATEGORIES_KEY = "id";
    public static final String CATEGORIES_LIBELLE = "libelle";

    public static final String CATEGORIES_TABLE_NAME = "Categories";

    public static final String CATEGORIES_TABLE_CREATE =
            "CREATE TABLE " + CATEGORIES_TABLE_NAME + " (" +
                    CATEGORIES_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    CATEGORIES_LIBELLE + " TEXT);";

    public static final String CATEGORIES_TABLE_DROP = "DROP TABLE IF EXISTS " + CATEGORIES_TABLE_NAME + ";";
    // ---------------------

    // Transactions
    // -----------------
    public static final String TRANSACTIONS_KEY = "id";
    public static final String TRANSACTIONS_MONTANT = "montant";
    public static final String TRANSACTIONS_COMPTEID = "compteId";
    public static final String TRANSACTIONS_CATEGORIEID = "categorieId";
    public static final String TRANSACTIONS_DATE = "date";

    public static final String TRANSACTIONS_TABLE_NAME = "Transactions";

    public static final String TRANSACTIONS_TABLE_CREATE =
            "CREATE TABLE " + TRANSACTIONS_TABLE_NAME + " (" +
                    TRANSACTIONS_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    TRANSACTIONS_MONTANT + " REAL, " +
                    TRANSACTIONS_DATE + " DATE, "+
                    TRANSACTIONS_COMPTEID + " INTEGER, " +
                    TRANSACTIONS_CATEGORIEID + " INTEGER," +
                    " FOREIGN KEY("+ TRANSACTIONS_COMPTEID +") REFERENCES "+ COMPTES_TABLE_NAME +"("+ COMPTES_KEY +")," +
                    " FOREIGN KEY("+ TRANSACTIONS_CATEGORIEID +") REFERENCES "+ CATEGORIES_TABLE_NAME +"("+ CATEGORIES_KEY +") );";

    public static final String TRANSACTIONS_TABLE_DROP = "DROP TABLE IF EXISTS " + TRANSACTIONS_TABLE_NAME + ";";
    // ---------------------




    public DatabaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("PRAGMA foreign_keys=ON;");
        db.execSQL(COMPTES_TABLE_CREATE);
        db.execSQL(CATEGORIES_TABLE_CREATE);
        db.execSQL(TRANSACTIONS_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("PRAGMA foreign_keys=ON;");
        db.execSQL(COMPTES_TABLE_DROP);
        db.execSQL(CATEGORIES_TABLE_DROP);
        db.execSQL(TRANSACTIONS_TABLE_DROP);
        onCreate(db);
    }

}

