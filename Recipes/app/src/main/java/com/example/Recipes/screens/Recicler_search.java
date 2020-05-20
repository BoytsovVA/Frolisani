package com.example.Recipes.screens;

import static com.example.Recipes.screens.home.EXTRA_REC6;
import static com.example.Recipes.screens.menu.drinks.EXTRA_REC5;
import static com.example.Recipes.screens.menu.kitchen.EXTRA_REC1;
import static com.example.Recipes.screens.menu.type_of_dish.EXTRA_REC2;
import static com.example.Recipes.screens.menu.type_of_food.EXTRA_REC3;
import static com.example.Recipes.screens.menu.type_of_product.EXTRA_REC4;
import static com.example.Recipes.screens.search.EXTRA_REC7;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.Recipes.R;
import com.example.Recipes.Recipes_class;
import com.example.Recipes.screens.OwnRecipes.RecipesView;
import java.util.ArrayList;

public class Recicler_search extends AppCompatActivity {
  public static final String EXTRA_RECIPE = "Recicler_search.EXTRA_RECIPE";

  private RecyclerView recyclerView;
  private DatabaseHelper mDBHelper;
  private SQLiteDatabase mDb;
  ArrayList<String> request;
  ArrayList<String> recipe;
  ArrayList rec;

  public static void start1(Activity caller, ArrayList<String> rep) {
    Intent intent = new Intent(caller, RecipesView.class);
    if (rep != null) {
      intent.putStringArrayListExtra(EXTRA_RECIPE, rep);
    }
    caller.startActivity(intent);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.list_of_search);
    mDBHelper = new DatabaseHelper(this);
    mDb = mDBHelper.getReadableDatabase();
    request = null;
    request = getIntent().getStringArrayListExtra(EXTRA_REC1);
    if (request == null) request = getIntent().getStringArrayListExtra(EXTRA_REC2);
    if (request == null) request = getIntent().getStringArrayListExtra(EXTRA_REC3);
    if (request == null) request = getIntent().getStringArrayListExtra(EXTRA_REC4);
    if (request == null) request = getIntent().getStringArrayListExtra(EXTRA_REC5);
    if (request == null) request = getIntent().getStringArrayListExtra(EXTRA_REC6);
    if (request == null) request = getIntent().getStringArrayListExtra(EXTRA_REC7);
    if (request == null) request = getIntent().getStringArrayListExtra(EXTRA_RECIPE);
    if (request == null) {
      ArrayList rec1 =
          mDBHelper.listRecipes(
              "SELECT * FROM app_recipes WHERE recipes_block =?", new String[] {"0"});
      ArrayList<Integer> products = new ArrayList<Integer>();
      products = mDBHelper.listProduct1();
      rec = comparison(rec1, products);
    }

    recyclerView = findViewById(R.id.recyclerView);
    LinearLayoutManager linearLayoutManager =
        new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
    recyclerView.setLayoutManager(linearLayoutManager);
    recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    if (request != null) {
      String querry = "";
      String[] where = new String[3];
      if (request.size() == 3) {
        querry = request.get(0);
        where = new String[] {request.get(1), request.get(2)};
      } else if (request.size() == 2) {
        querry = request.get(0);
        where = new String[] {request.get(1)};
      }

      rec = mDBHelper.listRecipes(querry, where);
    }
    final AdapterMainDB adapter = new AdapterMainDB(this, rec);

    recyclerView.setAdapter(adapter);
    adapter.notifyDataSetChanged();
  }

  private ArrayList comparison(ArrayList<Recipes_class> rec, ArrayList<Integer> products) {
    ArrayList recipes = new ArrayList();
    int sizerecipes = rec.size(); // кол-во всех рецептов
    int sizeproduct = products.size(); // кол-во необходимых продуктов

    for (int i = 0; i < sizerecipes; i++) {
      int count = 0;
      ArrayList<Integer> lol = rec.get(i).getProducts(); // список продуктов у каждого рецепта

      for (int h = 0; h < lol.size(); h++) {
        for (int g = 0; g < products.size(); g++) {
          int k = lol.get(h);
          int t = products.get(g);
          if (k == t) {
            count++;
          }
        }
      }

      if ((count == lol.size()) && (lol.size() != 0)) recipes.add(rec.get(i));
    }
    return recipes;
  }
}
