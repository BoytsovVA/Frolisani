package com.example.Recipes.screens;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.Recipes.R;
import java.util.ArrayList;

public class Fridg extends AppCompatActivity {
  private RecyclerView recyclerView;
  private DatabaseHelper mDBHelper;
  private SQLiteDatabase mDb;
  public int flag = 0;
  public String req, NameProduct;
  ArrayList prod;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_fridg);
    final EditText editText = (EditText) findViewById(R.id.Search_pr);

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

    editText.setOnKeyListener(
        new View.OnKeyListener() {
          public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (event.getAction() == KeyEvent.ACTION_DOWN && (keyCode == KeyEvent.KEYCODE_ENTER)) {
              NameProduct = editText.getText().toString();
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
}
