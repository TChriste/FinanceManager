package heg.financemanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import heg.financemanager.business.Compte;
import heg.financemanager.dao.ComptesDAO;


public class Home extends AppCompatActivity {

    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mBalance = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getAccounts();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, mNames, mBalance);

        RecyclerView recyclerView = findViewById(R.id.recylerView);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void getAccounts() {
        final ComptesDAO comptesDAO = new ComptesDAO(getApplicationContext());
        comptesDAO.open();
        List<Compte> comptesList = comptesDAO.getComptes();
        if(!comptesList.isEmpty()){
            for(Compte compte : comptesList){
                mNames.add(compte.getLibelle());
                mBalance.add("CHF " + String.format("%.2f", compte.getSolde()));
            }
        }else{
            mNames.add("Aucun compte");
            mBalance.add("Veuillez en ajouter un ...");
        }
        comptesDAO.close();
    }

    public void onBtnCategories(View view) {
        Intent intent = new Intent(this, CategoriesActivity.class);
        startActivity(intent);
    }


    public void onBtnComptes(View view) {
        Intent intent = new Intent(this, ComptesActivity.class);
        startActivity(intent);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
