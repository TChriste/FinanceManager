package heg.financemanager;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import heg.financemanager.business.Compte;
import heg.financemanager.dao.ComptesDAO;

public class ComptesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comptes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });



        // ------------- TEST BASE DE DONNEES SQLLITE  ---------------------------------------------
        ComptesDAO comptesDAO = new ComptesDAO(getApplicationContext());
        comptesDAO.open();
        List<Compte> comptesList = comptesDAO.getComptes();

        //TextView textView = findViewById(R.id.textView);
        //textView.setText(testBD);
    }

}
