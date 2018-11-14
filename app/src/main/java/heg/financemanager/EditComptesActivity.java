package heg.financemanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import heg.financemanager.business.Compte;
import heg.financemanager.dao.ComptesDAO;

public class EditComptesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_comptes);

        Intent intent = getIntent();
        long bankAccountId = intent.getLongExtra(ComptesActivity.EXTRA_ID,-1);

        if(bankAccountId >= 0){
            ComptesDAO comptesDAO = new ComptesDAO(getApplicationContext());
            comptesDAO.open();
            Compte compte = comptesDAO.getCompteById(bankAccountId);

            if(compte != null){
                EditText editText = (EditText) findViewById(R.id.inputBankAccountName);
                editText.setText(compte.getLibelle());
            }else{
                Toast.makeText(EditComptesActivity.this,"Une erreur est survenue",Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(EditComptesActivity.this,"Une erreur est survenue",Toast.LENGTH_SHORT).show();
        }
    }
}
