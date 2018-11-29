package heg.financemanager;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.LauncherActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import heg.financemanager.business.Compte;
import heg.financemanager.dao.ComptesDAO;

public class ComptesActivity extends AppCompatActivity {

    public static final String EXTRA_ID = "heg.financemanager.ID";

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
                startActivity(new Intent(ComptesActivity.this, EditComptesActivity.class));
            }
        });
        final ComptesDAO comptesDAO = new ComptesDAO(getApplicationContext());
        comptesDAO.open();
        final List<Compte> comptesList = comptesDAO.getComptes();
        List<String> comptesLibelles = new ArrayList<>();
        if(!comptesList.isEmpty()){
            for(Compte compte : comptesList){
                comptesLibelles.add(compte.getLibelle());
            }
        }
        String[] comptesLibelleArray = comptesLibelles.toArray(new String[0]);

        ListView mListView = (ListView) findViewById(R.id.list_comptes);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(ComptesActivity.this, android.R.layout.simple_list_item_1, comptesLibelleArray);
        mListView.setAdapter(adapter);
        mListView.setEmptyView(findViewById(R.id.empty_list_item));
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ComptesActivity.this, EditComptesActivity.class);
                intent.putExtra(EXTRA_ID, comptesList.get(position).getId());
                startActivity(intent);
            }
        });
    }

}
