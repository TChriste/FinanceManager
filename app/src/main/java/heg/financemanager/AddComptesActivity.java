package heg.financemanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import heg.financemanager.business.Compte;
import heg.financemanager.dao.ComptesDAO;

public class AddComptesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_comptes);
    }

    public void saveBankAccount(View view) {
        EditText editText = (EditText) findViewById(R.id.inputBankAccountName);
        String bankAccountName = editText.getText().toString();

        ComptesDAO comptesDAO = new ComptesDAO(getApplicationContext());
        comptesDAO.open();
        comptesDAO.insertCompte(new Compte(bankAccountName));

        Intent intent = new Intent(this, ComptesActivity.class);
        startActivity(intent);
    }
}
