package heg.financemanager;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import heg.financemanager.business.Categorie;
import heg.financemanager.business.Compte;
import heg.financemanager.business.Transaction;
import heg.financemanager.dao.CategoriesDAO;
import heg.financemanager.dao.ComptesDAO;
import heg.financemanager.dao.TransactionsDAO;

public class QuickAddDialogFragment extends DialogFragment {

    public Dialog onCreateDialog (Bundle savedInstanceState){
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.dialog_quick_add, null));

        builder.setPositiveButton("Valider", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Dialog dialog2 = Dialog.class.cast(dialog);

                        Spinner spinnerTypeAction = (Spinner) dialog2.findViewById(R.id.spinnerTypeAction);
                        String selectedAction = spinnerTypeAction.getSelectedItem().toString();

                        Spinner spinnerCategories = (Spinner) dialog2.findViewById(R.id.spinnerCategories);
                        Categorie selectedCategory = (Categorie) spinnerCategories.getSelectedItem();

                        Spinner spinnerAccounts = (Spinner) dialog2.findViewById(R.id.spinnerAccounts);
                        Compte selectedAccount = (Compte) spinnerAccounts.getSelectedItem();

                        EditText editTextAmount = (EditText) dialog2.findViewById(R.id.dialogAmountEditText);
                        double amount = Double.parseDouble(editTextAmount.getText().toString());

                        if(selectedAction == "Revenu"){
                            addNewEarn(selectedAccount.getId(),selectedCategory.getId(), amount);
                        }else{
                            addNewExpense(selectedAccount.getId(), selectedCategory.getId(),amount);
                        }
                        Intent intent = new Intent(getContext(), Home.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        // Create the AlertDialog object
        AlertDialog alert = builder.create();

        View view = inflater.inflate(R.layout.dialog_quick_add, null);

        String actionsTypes[] = {"Dépense", "Revenu"};
        Spinner spinnerActions = (Spinner) view.findViewById(R.id.spinnerTypeAction);
        ArrayAdapter<String> spinnerArrayAdapterActions = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, actionsTypes);
        spinnerArrayAdapterActions.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        spinnerActions.setAdapter(spinnerArrayAdapterActions);

        CategoriesDAO categoriesDAO = new CategoriesDAO(getContext());
        categoriesDAO.open();
        List<Categorie> categorieList = categoriesDAO.getCategories();
        Spinner spinnerCategories = (Spinner) view.findViewById(R.id.spinnerCategories);
        ArrayAdapter<Categorie> adapter = new ArrayAdapter<Categorie>(getContext(), android.R.layout.simple_spinner_dropdown_item, categorieList);
        spinnerCategories.setAdapter(adapter);

        ComptesDAO comptesDAO = new ComptesDAO(getContext());
        comptesDAO.open();
        List<Compte> comptesList = comptesDAO.getComptes();
        Spinner spinnerComptes = (Spinner) view.findViewById(R.id.spinnerAccounts);
        ArrayAdapter<Compte> adapter2 = new ArrayAdapter<Compte>(getContext(), android.R.layout.simple_spinner_dropdown_item, comptesList);
        spinnerComptes.setAdapter(adapter2);

        EditText editTextAmount = (EditText) view.findViewById(R.id.dialogAmountEditText);
        editTextAmount.setHint("0.00");

        alert.setView(view);
        return alert;
    }

    private void addNewEarn(long compteId, long categoryId, double amount) {
        TransactionsDAO transactionsDAO = new TransactionsDAO(getContext());
        ComptesDAO comptesDAO = new ComptesDAO(getContext());
        CategoriesDAO categoriesDAO = new CategoriesDAO(getContext());

        Toast.makeText(getContext(),String.valueOf(compteId), Toast.LENGTH_SHORT).show();
        comptesDAO.open();
        Compte compte = comptesDAO.getCompteById(compteId);
        compte.addToSolde((float) amount);

        categoriesDAO.open();
        Categorie categorie = categoriesDAO.getCategorieById(categoryId);
        categoriesDAO.close();

        transactionsDAO.open();
        transactionsDAO.insertTransaction(new Transaction(new Date(),compte,categorie,amount));
        transactionsDAO.close();

        comptesDAO.updateCompte(compte);
        comptesDAO.close();
    }

    private void addNewExpense(long compteId, long categoryId, double amount) {
        TransactionsDAO transactionsDAO = new TransactionsDAO(getContext());
        ComptesDAO comptesDAO = new ComptesDAO(getContext());
        CategoriesDAO categoriesDAO = new CategoriesDAO(getContext());

        comptesDAO.open();
        Compte compte = comptesDAO.getCompteById(compteId);

        if((compte.getSolde() - amount) > 0){
            compte.removeToSolde((float) amount);

            categoriesDAO.open();
            Categorie categorie = categoriesDAO.getCategorieById(categoryId);
            categoriesDAO.close();

            transactionsDAO.open();
            transactionsDAO.insertTransaction(new Transaction(new Date(),compte,categorie,amount));
            transactionsDAO.close();

            comptesDAO.updateCompte(compte);
        }else{
            Toast.makeText(getContext(),"Solde insuffisant !", Toast.LENGTH_LONG).show();
        }
        comptesDAO.close();
    }


    private String[] getCategories(){
        CategoriesDAO categoriesDAO = new CategoriesDAO(getContext());
        categoriesDAO.open();

        List<Categorie> categorieList = categoriesDAO.getCategories();
        List<String> categorieLabelList = new ArrayList<>();

        for(Categorie cat : categorieList){
            categorieLabelList.add(cat.getLibelle());
        }

        String[] stringArray = categorieLabelList.toArray(new String[0]);
        categoriesDAO.close();

        return stringArray;
    }
}