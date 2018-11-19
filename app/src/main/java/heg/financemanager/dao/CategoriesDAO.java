package heg.financemanager.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import heg.financemanager.business.Categorie;

public class CategoriesDAO extends AbstractDAO {


    private static final String TABLE_NAME = "Categories";
    private static final String KEY = "id";
    private static final String LIBELLE = "libelle";


    public CategoriesDAO(Context pContext) {
        super(pContext);
    }

    public Categorie insertCategorie(Categorie categorie){
        ContentValues values = new ContentValues();
        values.put(LIBELLE, categorie.getLibelle());

        db.beginTransaction();
        long newRowId = db.insert(TABLE_NAME,null,values);
        db.setTransactionSuccessful();
        db.endTransaction();

        categorie.setId(newRowId);
        return categorie;
    }

    public List<Categorie> getCategories(){

        String[] projection = {"id", "libelle"};
        String sortOrder = LIBELLE + " DESC";

        Cursor cursor = db.query(
                TABLE_NAME,          // The table to query
                projection,          // The array of columns to return (pass null to get all)
                null,       // The columns for the WHERE clause
                null,    // The values for the WHERE clause
                null,       // don't group the rows
                null,        // don't filter by row groups
                sortOrder           // The sort order
        );

        List<Categorie> categories = new ArrayList<>();
        while(cursor.moveToNext()) {
            long itemId = cursor.getLong(cursor.getColumnIndexOrThrow("id"));
            String itemLibelle = cursor.getString(cursor.getColumnIndexOrThrow("libelle"));

            categories.add(new Categorie(itemId,itemLibelle));
        }
        cursor.close();

        return categories;
    }

    public Categorie getCategorieById(long id){
        String[] projection = {"id", "libelle"};

        String selection = KEY + " = ?";
        String[] selectionArgs = { String.valueOf(id) };

        Cursor cursor = db.query(
                TABLE_NAME,          // The table to query
                projection,          // The array of columns to return (pass null to get all)
                selection,       // The columns for the WHERE clause
                selectionArgs,    // The values for the WHERE clause
                null,       // don't group the rows
                null,        // don't filter by row groups
                null           // The sort order
        );

        while(cursor.moveToNext()) {
            long itemId = cursor.getLong(cursor.getColumnIndexOrThrow("id"));
            String itemLibelle = cursor.getString(cursor.getColumnIndexOrThrow("libelle"));

            return new Categorie(itemId,itemLibelle);
        }
        return null;
    }

    public Categorie updateCategorie(Categorie categorie){
        ContentValues values = new ContentValues();
        values.put(LIBELLE, categorie.getLibelle());

        String selection = KEY + " = ?";
        String[] selectionArgs = {String.valueOf(categorie.getId())};

        int count = db.update(  TABLE_NAME,
                values,
                selection,
                selectionArgs
        );
        if(count > 0){
            return categorie;
        }else{
            return null;
        }
    }

    public Categorie deleteCategorie(Categorie categorie){
        String selection = KEY + " = ?";
        String[] selectionArgs = {String.valueOf(categorie.getId())};
        int deletedRows = db.delete(TABLE_NAME, selection, selectionArgs);

        if(deletedRows > 0){
            return categorie;
        }else{
            return null;
        }
    }
}
