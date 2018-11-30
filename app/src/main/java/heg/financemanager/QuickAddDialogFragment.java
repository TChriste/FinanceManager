package heg.financemanager;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import heg.financemanager.business.Categorie;
import heg.financemanager.business.Compte;
import heg.financemanager.dao.CategoriesDAO;

public class QuickAddDialogFragment extends DialogFragment {

    public Dialog onCreateDialog (Bundle savedInstanceState){
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.dialog_quick_add, null));

        builder.setPositiveButton("Valider", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // FIRE ZE MISSILES!
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

        String categories[] = getCategories();

        Spinner spinnerCategories = (Spinner) view.findViewById(R.id.spinnerCategories);
        ArrayAdapter<String> spinnerArrayAdapterCategories = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, categories);
        spinnerArrayAdapterCategories.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        spinnerCategories.setAdapter(spinnerArrayAdapterCategories);

        EditText editTextAmount = (EditText) view.findViewById(R.id.dialogAmountEditText);
        editTextAmount.setHint("0.00");

        alert.setView(view);
        return alert;
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