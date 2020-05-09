package com.example.Recipes.screens;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.Recipes.R;
import com.example.Recipes.Recipes_class;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class AdapterMainDB extends RecyclerView.Adapter<AdapterMainDB.RecipesViewHolder> {

  protected final Context mContext;
  private ArrayList<Recipes_class> RECIPES;

  protected AdapterMainDB(Context context, ArrayList<Recipes_class> _RECIPES) {
    this.mContext = context;
    RECIPES = _RECIPES;
  }

  @Override
  public RecipesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view =
        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_list, parent, false);
    return new RecipesViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull RecipesViewHolder holder, int position) {
    final Recipes_class rec = RECIPES.get(position);
    holder.bind(rec);
  }

  @Override
  public int getItemCount() {
    return RECIPES.size();
  }

  public class RecipesViewHolder extends RecyclerView.ViewHolder {
    TextView recipes;
    TextView Time;
    TextView instock;
    TextView notinstock;
    ImageView Block;
    ImageView Favorite;
    ImageView Photo;
    Recipes_class recp;
    ArrayList<String> recipe;
    public DatabaseHelper mDBHelper = new DatabaseHelper(mContext);
    ContentValues cv = new ContentValues();

    public RecipesViewHolder(View view) {

      super(view);
      recipes = itemView.findViewById(R.id.recipe);
      Block = itemView.findViewById(R.id.block1);
      instock = itemView.findViewById(R.id.InStock);
      notinstock = itemView.findViewById(R.id.NotInStock);
      Time = itemView.findViewById(R.id.time);
      Favorite = itemView.findViewById(R.id.favorites);
      Photo = itemView.findViewById(R.id.photo);

      itemView.setOnClickListener(
          new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              recipe = new ArrayList<String>();
              recipe.add(0, recp.GetName());
              recipe.add(1, recp.getCharacter());
              recipe.add(2, recp.getInstruction());
              recipe.add(3, recp.getProduct());
              recipe.add(4, recp.getTime());
              recipe.add(5, recp.getLevel());
              recipe.add(6, String.valueOf(recp.getBlock()));
              recipe.add(7, String.valueOf(recp.getFavorites()));
              recipe.add(8, recp.getImage());

              Recicler_search.start1((Activity) itemView.getContext(), recipe);
            }
          });

      Block.setOnClickListener(
          new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              SQLiteDatabase db = mDBHelper.getWritableDatabase();
              if (recp.GetBlock() == 0) {
                cv.put("Recipes_block", 1);
                db.update(
                    "app_recipes",
                    cv,
                    "recipes_id = ?",
                    new String[] {String.valueOf(recp.GetId())});
                recp.setBlock(1);
                notifyItemRemoved(getAdapterPosition());
              } else {
                cv.put("Recipes_block", 0);
                db.update(
                    "app_recipes",
                    cv,
                    "recipes_id = ?",
                    new String[] {String.valueOf(recp.GetId())});
                recp.setBlock(0);
                RECIPES.remove(getAdapterPosition());
                notifyItemRemoved(getAdapterPosition());
              }
            }
          });
      ///
      Favorite.setOnClickListener(
          new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              SQLiteDatabase db = mDBHelper.getWritableDatabase();
              if (recp.GetFavorite() == 0) {
                cv.put("Recipes_favorites", 1);
                db.update(
                    "app_recipes",
                    cv,
                    "recipes_id = ?",
                    new String[] {String.valueOf(recp.GetId())});
                recp.setFavorites(1);
              } else {
                cv.put("Recipes_favorites", 0);
                db.update(
                    "app_recipes",
                    cv,
                    "recipes_id = ?",
                    new String[] {String.valueOf(recp.GetId())});
                recp.setFavorites(0);
              }
              notifyItemChanged(getAdapterPosition());
            }
          });
    }

    public void bind(Recipes_class rec) {
      this.recp = rec;
      recipes.setText(rec.GetName());
      Time.setText(rec.getTime());

      InputStream inputStream = null;
      try {
        if (rec.GetFavorite() == 1) inputStream = mContext.getAssets().open("звезда1.png");
        else inputStream = mContext.getAssets().open("Star-Favorites.png");
      } catch (IOException e) {
        e.printStackTrace();
      }
      Drawable d = Drawable.createFromStream(inputStream, null);
      Favorite.setImageDrawable(d);
      if (inputStream != null) {
        try {
          inputStream.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }

      if (rec.getImage() != null) {
        inputStream = null;
        try {
          inputStream = mContext.getAssets().open(recp.getImage());
        } catch (IOException e) {
          e.printStackTrace();
        }
        d = Drawable.createFromStream(inputStream, null);
        Photo.setImageDrawable(d);
        if (inputStream != null) {
          try {
            inputStream.close();
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
      }

      String InStock = "";
      String NotInStock = "";
      String selectQuery =
          "SELECT product_name "
              + "FROM app_product, app_entry "
              + "WHERE (product_id = p_id AND r_id = ? AND product_fridge = 1);"; // есть в
      // холодильнике
      String[] where = new String[] {String.valueOf(rec.GetId())};
      SQLiteDatabase db = mDBHelper.getWritableDatabase();
      Cursor cursor = db.rawQuery(selectQuery, where);
      if (cursor.moveToFirst()) {
        do {
          InStock += cursor.getString(0) + ", ";
        } while (cursor.moveToNext());
      }
      if (InStock.length() > 2) InStock = InStock.substring(0, InStock.length() - 2);
      instock.setText(InStock);
      selectQuery =
          "SELECT product_name "
              + "FROM app_product, app_entry "
              + "WHERE (product_id = p_id AND r_id = ? AND product_fridge = 0);"; // нет в
      // холодильнике
      cursor = db.rawQuery(selectQuery, where);
      if (cursor.moveToFirst()) {
        do {
          NotInStock += cursor.getString(0) + ", ";
        } while (cursor.moveToNext());
      }
      if (NotInStock.length() > 2) NotInStock = NotInStock.substring(0, NotInStock.length() - 2);
      notinstock.setText(NotInStock);
      db.close();
      cursor.close();
    }
  }
}
