package heg.financemanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import heg.financemanager.business.Compte;
import heg.financemanager.dao.ComptesDAO;

public class EditComptesActivity extends AppCompatActivity {

    Compte compte;
    ComptesDAO comptesDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_comptes);
        comptesDAO = new ComptesDAO(getApplicationContext());

        Intent intent = getIntent();
        long bankAccountId = intent.getLongExtra(ComptesActivity.EXTRA_ID,-1);

        if(bankAccountId >= 0){
            comptesDAO.open();
            compte = comptesDAO.getCompteById(bankAccountId);

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

    public void saveBankAccount(View view) {
        EditText editText = (EditText) findViewById(R.id.inputBankAccountName);
        String bankAccountName = editText.getText().toString();

        compte.setLibelle(bankAccountName);

        comptesDAO.open();
        comptesDAO.updateCompte(compte);

        Toast.makeText(EditComptesActivity.this,"Modification effectuée",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, ComptesActivity.class);
        startActivity(intent);
    }


    public void deleteBankAccount(View view) {
        comptesDAO.open();
        comptesDAO.deleteCompte(compte);

        Toast.makeText(EditComptesActivity.this,"Suppression effectuée",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, ComptesActivity.class);
        startActivity(intent);
    }
}
