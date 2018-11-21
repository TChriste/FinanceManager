package heg.financemanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import heg.financemanager.business.Categorie;
import heg.financemanager.business.Compte;
import heg.financemanager.dao.CategoriesDAO;
import heg.financemanager.dao.ComptesDAO;

public class CategoriesActivity extends AppCompatActivity {

    public static final String EXTRA_ID = "heg.financemanager.ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CategoriesActivity.this, EditCategoryActivity.class));
            }
        });


        final CategoriesDAO categoriesDAO = new CategoriesDAO(getApplicationContext());
        categoriesDAO.open();
        final List<Categorie> categorieList = categoriesDAO.getCategories();
        List<String> categoriesLibelles = new ArrayList<>();
        if(!categorieList.isEmpty()){
            for(Categorie categorie : categorieList){
                categoriesLibelles.add(categorie.getLibelle());
            }
        }
        String[] categoriesLibelleArray = categoriesLibelles.toArray(new String[0]);

        ListView mListView = (ListView) findViewById(R.id.list_categories);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(CategoriesActivity.this, android.R.layout.simple_list_item_1, categoriesLibelleArray);
        mListView.setAdapter(adapter);
        mListView.setEmptyView(findViewById(R.id.empty_list_item));
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(CategoriesActivity.this, EditCategoryActivity.class);
                intent.putExtra(EXTRA_ID, categorieList.get(position).getId());
                startActivity(intent);
            }
        });
    }

}
