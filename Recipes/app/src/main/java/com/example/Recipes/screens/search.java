package com.example.Recipes.screens;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.Recipes.R;
import com.example.Recipes.screens.menu.drinks;
import com.example.Recipes.screens.menu.kitchen;
import com.example.Recipes.screens.menu.type_of_dish;
import com.example.Recipes.screens.menu.type_of_food;
import com.example.Recipes.screens.menu.type_of_product;
import java.util.ArrayList;

public class search extends AppCompatActivity implements View.OnClickListener {
  public static final String EXTRA_REC7 = "kitchen.EXTRA_REC7";
  ImageButton Kitchen, Type_of_food, Type_of_dish, Drinks, Main_product;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_search);
    final EditText editText = (EditText) findViewById(R.id.Search);
    Kitchen = (ImageButton) findViewById(R.id.kitchen);
    Kitchen.setOnClickListener(this);
    Type_of_food = (ImageButton) findViewById(R.id.type_of_food);
    Type_of_food.setOnClickListener(this);
    Type_of_dish = (ImageButton) findViewById(R.id.type_of_dish);
    Type_of_dish.setOnClickListener(this);
    Main_product = (ImageButton) findViewById(R.id.main_product);
    Main_product.setOnClickListener(this);
    Drinks = (ImageButton) findViewById(R.id.drinks);
    Drinks.setOnClickListener(this);

    editText.setImeOptions(EditorInfo.IME_ACTION_DONE);
    editText.setOnEditorActionListener(
        new EditText.OnEditorActionListener() {
          @Override
          public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
              String NameRecipes = editText.getText().toString();
              Intent intent = new Intent(search.this, Recicler_search.class);
              String req =
                  "WITH RECURSIVE tmp AS ( "
                      + "SELECT app.*, d_l.pr_id, d_l.ch_id "
                      + "FROM app_recipes app "
                      + "JOIN descriptor d_l "
                      + "ON (upper(app.recipes_name) = upper(?) AND app.recipes_id = d_l.pr_id) "
                      + "UNION "
                      + "SELECT app.*, d_l.pr_id, d_l.ch_id "
                      + "FROM app_recipes app "
                      + "JOIN descriptor d_l "
                      + "ON t.ch_id = d_l.pr_id "
                      + "JOIN tmp t "
                      + "ON app.recipes_id = t.ch_id "
                      + ") "
                      + "select tmp.* from tmp "
                      + "GROUP BY tmp.recipes_id ";
              String data1 = NameRecipes;
              ArrayList<String> request = new ArrayList<String>();
              request.add(req);
              request.add(data1);
              intent.putStringArrayListExtra(EXTRA_REC7, request);
              startActivity(intent);
              return true;
            }
            return false;
          }
        });
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.kitchen:
        Intent intent = new Intent(search.this, kitchen.class);
        startActivity(intent);
        break;
      case R.id.type_of_food:
        intent = new Intent(search.this, type_of_food.class);
        startActivity(intent);
        break;
      case R.id.type_of_dish:
        intent = new Intent(search.this, type_of_dish.class);
        startActivity(intent);
        break;
      case R.id.main_product:
        intent = new Intent(search.this, type_of_product.class);
        startActivity(intent);
        break;
      case R.id.drinks:
        intent = new Intent(search.this, drinks.class);
        startActivity(intent);
        break;
    }
  }
}
