package com.example.Recipes.screens;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.Recipes.R;
import java.util.ArrayList;

public class Fridg extends AppCompatActivity implements View.OnClickListener {
  public static final String EXTRA_REC8 = "home.EXTRA_REC8";
  private RecyclerView recyclerView;
  private DatabaseHelper mDBHelper;
  private SQLiteDatabase mDb;
  public String req, NameProduct;
  Button search_rec;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_fridg);
    final EditText editText = (EditText) findViewById(R.id.Search_pr);
    search_rec = findViewById(R.id.search_recipes);
    search_rec.setOnClickListener(this);

    mDBHelper = new DatabaseHelper(this);
    recyclerView = findViewById(R.id.recyclerViewFridg);
    LinearLayoutManager linearLayoutManager =
        new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
    recyclerView.setLayoutManager(linearLayoutManager);
    recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

    mDb = mDBHelper.getReadableDatabase();
    final ArrayList<Product_class> prod = mDBHelper.listProduct();

    final Adapter_product adapter = new Adapter_product(this, prod);
    recyclerView.setAdapter(adapter);

    editText.setImeOptions(EditorInfo.IME_ACTION_DONE);
    editText.setOnEditorActionListener(
        new EditText.OnEditorActionListener() {
          @Override
          public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_DONE) {

              NameProduct = editText.getText().toString();
              req = "SELECT * FROM app_product WHERE Product_name=?;";
              String Where[] = {NameProduct};
              int size = prod.size();
              int position = 0;
              for (int i = 0; i < size; i++) {
                String name = prod.get(i).getName();

                if (prod.get(i).getName().equalsIgnoreCase(NameProduct))
                  position = prod.get(i).getPosition();
              }
              recyclerView.scrollToPosition(position);
              return true;
            }
            return false;
          }
        });
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.search_recipes:
        Intent intent = new Intent(Fridg.this, Recicler_search.class);
        String Request =
            "SELECT p_id FROM app_entry"
                + "WHERE p_priority =?"
                + "INTERSECT"
                + "SELECT product_id FROM app_product WHERE product_fridge =?;";
        String Where = "1";
        ArrayList<String> request = new ArrayList<String>();

        request.add(0, req);
        request.add(1, Where);
        request.add(2, Where);
        intent.putStringArrayListExtra(EXTRA_REC8, request);
        startActivity(intent);
        break;
    }
  }
}
