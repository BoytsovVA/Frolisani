package com.example.Recipes.screens;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;


import androidx.room.Room;


import com.example.Recipes.R;
import com.example.Recipes.data_note.AppDao;
import com.example.Recipes.data_note.NoteDao;
import com.example.Recipes.data_note.NoteDataBase;
import com.example.Recipes.data_own_recipes.RecipesDao;
import com.example.Recipes.data_own_recipes.RecipesDataBase;

import static com.example.Recipes.data_note.NoteDataBase.getDatabase;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    ImageButton AddRecipes,Home,Search,Notes;

    private NoteDataBase database;
    private AppDao appDao;
   // private RecipesDao recipesDao;

    private static MainActivity instance;
private static MainActivity instanceRep;

    public static MainActivity getInstance() {
        return instance;
    }

    public static MainActivity getInstanceRep() { return instanceRep; }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        instance = this;
        instanceRep = this;
        database = getDatabase(getApplicationContext());
      // database = getDatabase(getApplicationContext());

        appDao = database.appDao();
        // recipesDao = database.recipesDao();


        AddRecipes = (ImageButton) findViewById(R.id.AddRecipes);
        AddRecipes.setOnClickListener(this);
        Home = (ImageButton) findViewById(R.id.Home);
        Home.setOnClickListener(this);
        Search = (ImageButton) findViewById(R.id.Search);
        Search.setOnClickListener(this);
        Notes = (ImageButton) findViewById(R.id.Notes);
        Notes.setOnClickListener(this);

        }


   // public NoteDataBase getDatabase(MainActivity mainActivity) {
   //     return database;
    //}

    public void setDatabase(NoteDataBase database) {
        this.database = database;
    }

    public AppDao getAppDao() {
        return appDao;
    }

    public void setNoteDao(AppDao AppDao) {
        this.appDao = AppDao;
    }


   // public RecipesDao getRepDao() {
     //   return recipesDao;
    //}

    //public void setRepDao(RecipesDao recipesDao) {
      //  this.recipesDao = recipesDao;
    //}


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.AddRecipes:
                Intent intent = new Intent(MainActivity.this, com.example.Recipes.screens.OwnRecipes.AddRecipes.class);
                startActivity(intent);
                break;
            case R.id.Notes:
                 intent = new Intent(MainActivity.this, com.example.Recipes.screens.Note.Main_note.class);
                startActivity(intent);
                break;
            case R.id.Search:
                 intent = new Intent(MainActivity.this, search.class);
                startActivity(intent);
                break;
            case R.id.Home:
                 intent = new Intent(MainActivity.this, home.class);
                startActivity(intent);
                break;
        }
    }
}
