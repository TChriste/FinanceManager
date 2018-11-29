package heg.financemanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import heg.financemanager.business.Categorie;
import heg.financemanager.dao.CategoriesDAO;

public class EditCategoryActivity extends AppCompatActivity {

    CategoriesDAO categoriesDAO;
    Categorie categorie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_category);
        categoriesDAO = new CategoriesDAO(getApplicationContext());

        Long categoryId = getIntent().getLongExtra(CategoriesActivity.EXTRA_ID,-1);
        Button btnSave = findViewById(R.id.btnSaveCategory);

        if(categoryId >= 0){
            setTitle(getString(R.string.title_edit_category));
            findViewById(R.id.btnDeleteCategory).setVisibility(View.VISIBLE);

            categoriesDAO.open();
            categorie = categoriesDAO.getCategorieById(categoryId);

            if(categorie != null){
                EditText editText = (EditText) findViewById(R.id.inputCategoryName);
                editText.setText(categorie.getLibelle());
            }else{
                Toast.makeText(EditCategoryActivity.this,"Une erreur est survenue",Toast.LENGTH_SHORT).show();
            }

            btnSave.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    saveCategory(v);
                }
            });
        }else{
            setTitle(getString(R.string.title_add_category));
            findViewById(R.id.btnDeleteCategory).setVisibility(View.GONE);
            btnSave.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    saveNewCategory(v);
                }
            });
        }
    }

    private void saveCategory(View v) {
        EditText editText = (EditText) findViewById(R.id.inputCategoryName);
        String categorieName = editText.getText().toString();

        categorie.setLibelle(categorieName);

        categoriesDAO.open();
        categoriesDAO.updateCategorie(categorie);

        Toast.makeText(EditCategoryActivity.this,"Modification effectuée",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, CategoriesActivity.class);
        startActivity(intent);
    }

    public void saveNewCategory(View view) {
        EditText editText = (EditText) findViewById(R.id.inputCategoryName);
        String catetegoryName = editText.getText().toString();

        categoriesDAO = new CategoriesDAO(getApplicationContext());
        categoriesDAO.open();
        categoriesDAO.insertCategorie(new Categorie(catetegoryName));

        Toast.makeText(EditCategoryActivity.this,"Catégorie ajoutée",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, CategoriesActivity.class);
        startActivity(intent);
    }

    public void deleteCategory(View view) {
        categoriesDAO.open();
        categoriesDAO.deleteCategorie(categorie);

        Toast.makeText(EditCategoryActivity.this,"Suppression effectuée",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, CategoriesActivity.class);
        startActivity(intent);
    }
}
