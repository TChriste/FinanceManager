package heg.financemanager.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import heg.financemanager.business.Categorie;
import heg.financemanager.business.Compte;
import heg.financemanager.business.Transaction;

public class TransactionsDAO extends AbstractDAO {

    DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");

    private static final String TABLE_NAME = "Transactions";
    private static final String KEY = "id";
    private static final String DATE = "date";
    private static final String MONTANT = "montant";
    private static final String CATEGORIE_ID = "categorieId";
    private static final String COMPTE_ID = "compteId";




    private final String SELECT_QUERY = "SELECT t.id, t.date, t.montant, t.categorieId, ca.libelle AS categorieLibelle, t.compteId, co.libelle AS compteLibelle, co.solde" +
                                        "FROM Transactions t " +
                                        "INNER JOIN Comptes co ON co.id = t.compteId " +
                                        "INNER JOIN Categories ca ON ca.id = t.categorieId ";

    private final String UPDATE_QUERY = "SELECT t.id, t.date, t.montant, t.categorieId, ca.libelle AS categorieLibelle, t.compteId, co.libelle AS compteLibelle, co.solde" +
                                        "FROM Transactions t " +
                                        "INNER JOIN Comptes co ON co.id = t.compteId " +
                                        "INNER JOIN Categories ca ON ca.id = t.categorieId" +
                                        "WHERE t.id = ?";


    public TransactionsDAO(Context pContext) {
        super(pContext);
    }

    public Transaction insertTransaction(Transaction transaction){
        ContentValues values = new ContentValues();
        values.put(MONTANT, transaction.getMontant());
        values.put(CATEGORIE_ID,transaction.getCategorie().getId());
        values.put(COMPTE_ID,transaction.getCompte().getId());
        values.put(DATE,dateFormat.format(transaction.getDate()));

        db.beginTransaction();
        long newRowId = db.insert(TABLE_NAME,null,values);
        db.setTransactionSuccessful();
        db.endTransaction();

        transaction.setId(newRowId);
        return transaction;
    }

    public List<Transaction> getTransactions() throws ParseException {
        Cursor cursor = db.rawQuery(SELECT_QUERY,null);

        List<Transaction> transactions = new ArrayList<>();


        List<Compte> comptes = new ArrayList<>();
        List<Categorie> categories = new ArrayList<>();

        while(cursor.moveToNext()) {
            long itemId = cursor.getLong(cursor.getColumnIndexOrThrow("id"));
            Date itemDate = dateFormat.parse(cursor.getString(cursor.getColumnIndexOrThrow("date")));
            float itemMontant = cursor.getFloat(cursor.getColumnIndexOrThrow("montant"));

            long categorieId = cursor.getLong(cursor.getColumnIndexOrThrow("categorieId"));
            String categorieLibelle = cursor.getString(cursor.getColumnIndexOrThrow("categorieLibelle"));

            long compteId = cursor.getLong(cursor.getColumnIndexOrThrow("compteId"));
            String compteLibelle = cursor.getString(cursor.getColumnIndexOrThrow("compteLibelle"));
            Long compteSolde = cursor.getLong(cursor.getColumnIndexOrThrow("solde"));



            Compte compteToAddd = null;
            for(Compte compte : comptes){
                if(compte.getId() == compteId){
                    compteToAddd = compte;
                }
            }
            if(compteToAddd == null){
                compteToAddd = new Compte(compteId, compteLibelle,compteSolde);
            }

            Categorie categorieToAddd = null;
            for(Categorie categorie : categories){
                if(categorie.getId() == categorieId){
                    categorieToAddd = categorie;
                }
            }
            if(categorieToAddd == null){
                categorieToAddd = new Categorie(categorieId, categorieLibelle);
            }


            transactions.add(new Transaction(itemId, itemDate, compteToAddd, categorieToAddd, itemMontant));
        }
        cursor.close();

        return transactions;
    }

    public Transaction getTransactionById(long id) throws ParseException {

        Cursor cursor  = db.rawQuery(UPDATE_QUERY, new String[]{String.valueOf(id)});

        while(cursor.moveToNext()) {
            long itemId = cursor.getLong(cursor.getColumnIndexOrThrow("id"));
            Date itemDate = dateFormat.parse(cursor.getString(cursor.getColumnIndexOrThrow("date")));
            float itemMontant = cursor.getFloat(cursor.getColumnIndexOrThrow("montant"));

            long categorieId = cursor.getLong(cursor.getColumnIndexOrThrow("categorieId"));
            String categorieLibelle = cursor.getString(cursor.getColumnIndexOrThrow("categorieLibelle"));

            long compteId = cursor.getLong(cursor.getColumnIndexOrThrow("compteId"));
            String compteLibelle = cursor.getString(cursor.getColumnIndexOrThrow("compteLibelle"));
            Long compteSolde = cursor.getLong(cursor.getColumnIndexOrThrow("solde"));


            return new Transaction(itemId,itemDate,new Compte(compteId,compteLibelle,compteSolde), new Categorie(categorieId,categorieLibelle),itemMontant);
        }
        return null;
    }

    public Transaction deleteTransaction(Transaction transaction){
        String selection = KEY + " = ?";
        String[] selectionArgs = {String.valueOf(transaction.getId())};
        int deletedRows = db.delete(TABLE_NAME, selection, selectionArgs);

        if(deletedRows > 0){
            return transaction;
        }else{
            return null;
        }
    }
}
