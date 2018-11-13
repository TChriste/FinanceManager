package heg.financemanager.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import heg.financemanager.business.Compte;

public class ComptesDAO extends AbstractDAO{

    private static final String TABLE_NAME = "Comptes";
    private static final String KEY = "id";
    private static final String LIBELLE = "libelle";
    private static final String SOLDE = "solde";


    public ComptesDAO(Context pContext) {
        super(pContext);
    }

    public Compte insertCompte(Compte compte){
        ContentValues values = new ContentValues();
        values.put(LIBELLE, compte.getLibelle());
        values.put(SOLDE,compte.getSolde());

        db.beginTransaction();
        long newRowId = db.insert(TABLE_NAME,null,values);
        db.setTransactionSuccessful();

        compte.setId(newRowId);
        return compte;
    }

    public List<Compte> getComptes(){
        String[] projection = {"id", "libelle", "solde"};
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

        List<Compte> comptes = new ArrayList<>();
        while(cursor.moveToNext()) {
            long itemId = cursor.getLong(cursor.getColumnIndexOrThrow("id"));
            String itemLibelle = cursor.getString(cursor.getColumnIndexOrThrow("libelle"));
            float itemSolde = cursor.getFloat(cursor.getColumnIndexOrThrow("solde"));

            comptes.add(new Compte(itemId,itemLibelle,itemSolde));
        }
        cursor.close();

        return comptes;
    }

    public Compte updateCompte(Compte compte){
        ContentValues values = new ContentValues();
        values.put(SOLDE, compte.getSolde());
        values.put(LIBELLE, compte.getLibelle());

        String selection = KEY + " = ?";
        String[] selectionArgs = {String.valueOf(compte.getId())};

        int count = db.update(  TABLE_NAME,
                                values,
                                selection,
                                selectionArgs
                             );
        if(count > 0){
            return compte;
        }else{
            return null;
        }
    }

    public Compte deleteCompte(Compte compte){
        String selection = KEY + " = ?";
        String[] selectionArgs = {String.valueOf(compte.getId())};
        int deletedRows = db.delete(TABLE_NAME, selection, selectionArgs);

        if(deletedRows > 0){
            return compte;
        }else{
            return null;
        }

    }
}
