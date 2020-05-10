package com.example.Recipes.screens;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.Recipes.Recipes_class;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

class DatabaseHelper extends SQLiteOpenHelper {
  private static String DB_NAME = "main_db1.sqlite";
  private static String DB_PATH = "";
  private static final int DB_VERSION = 5;

  private SQLiteDatabase mDataBase;
  private final Context mContext;
  private boolean mNeedUpdate = false;

  public DatabaseHelper(Context context) {
    super(context, DB_NAME, null, DB_VERSION);
    if (android.os.Build.VERSION.SDK_INT >= 17)
      DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
    else DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
    this.mContext = context;

    copyDataBase();

    this.getReadableDatabase();
  }

  public void updateDataBase() throws IOException {
    if (mNeedUpdate) {
      File dbFile = new File(DB_PATH + DB_NAME);
      if (dbFile.exists()) dbFile.delete();

      copyDataBase();

      mNeedUpdate = false;
    }
  }

  private boolean checkDataBase() {
    File dbFile = new File(DB_PATH + DB_NAME);
    return dbFile.exists();
  }

  private void copyDataBase() {
    if (!checkDataBase()) {
      this.getReadableDatabase();
      this.close();
      try {
        copyDBFile();
      } catch (IOException mIOException) {
        throw new Error("ErrorCopyingDataBase");
      }
    }
  }

  private void copyDBFile() throws IOException {
    InputStream mInput = mContext.getAssets().open(DB_NAME);
    // InputStream mInput = mContext.getResources().openRawResource(R.raw.info);
    OutputStream mOutput = new FileOutputStream(DB_PATH + DB_NAME);
    byte[] mBuffer = new byte[1024];
    int mLength;
    while ((mLength = mInput.read(mBuffer)) > 0) mOutput.write(mBuffer, 0, mLength);
    mOutput.flush();
    mOutput.close();
    mInput.close();
  }

  public boolean openDataBase() throws SQLException {
    mDataBase =
        SQLiteDatabase.openDatabase(DB_PATH + DB_NAME, null, SQLiteDatabase.CREATE_IF_NECESSARY);
    return mDataBase != null;
  }

  @Override
  public synchronized void close() {
    if (mDataBase != null) mDataBase.close();
    super.close();
  }

  @Override
  public void onCreate(SQLiteDatabase db) {}

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    if (newVersion > oldVersion) mNeedUpdate = true;
  }

  public ArrayList<Product_class> listProduct() {
    ArrayList<Product_class> products = new ArrayList<>();
    String selectQuery = "SELECT * FROM app_product ORDER BY product_name;";
    SQLiteDatabase db = this.getWritableDatabase();
    // Cursor cursor = db.query("app_product",new String[]
    // {"product_name"},null,null,null,null,"product_name");
    int i = 0;
    Cursor cursor = db.rawQuery(selectQuery, null);
    if (cursor.moveToFirst()) {
      do {
        Product_class product = new Product_class();
        product.setId(cursor.getInt(0));
        product.setName(cursor.getString(1));
        product.setIsExit(cursor.getInt(2));
        product.setPosition(i);
        i++;
        products.add(product);
      } while (cursor.moveToNext());
    }
    db.close();
    cursor.close();
    return products;
  }

  public ArrayList<Integer> listProduct1() {
    ArrayList<Integer> products = new ArrayList<>();
    String selectQuery =
        "SELECT p_id FROM app_entry WHERE p_priority =? INTERSECT SELECT product_id FROM"
            + " app_product WHERE product_fridge =?;";
    SQLiteDatabase db = this.getWritableDatabase();
    Cursor cursor = db.rawQuery(selectQuery, new String[] {"1", "1"});
    if (cursor.moveToFirst()) {
      do {

        products.add(cursor.getInt(0));

      } while (cursor.moveToNext());
    }
    db.close();
    cursor.close();
    return products;
  }

  ///////////////////// запросы
  public ArrayList<Recipes_class> listRecipes(String _selectQuere, String[] _where) {
    ArrayList<Recipes_class> recipes = new ArrayList<>();

    String selectQuery = _selectQuere;
    String[] Where = _where;
    SQLiteDatabase db = this.getWritableDatabase();
    Cursor cursor = db.rawQuery(selectQuery, Where);
    int i = 0;
    if (cursor.moveToFirst()) {
      do {
        Recipes_class recipe = new Recipes_class();
        recipe.setId(cursor.getInt(0));
        recipe.setName(cursor.getString(1));
        recipe.setCharacter(cursor.getString(2));
        recipe.setInstruction(cursor.getString(3));
        recipe.setProduct(cursor.getString(4));
        recipe.setFavorites(cursor.getInt(5));
        recipe.setBlock(cursor.getInt(6));
        recipe.setLevel(cursor.getString(7));
        recipe.setTime(cursor.getString(8));
        recipe.setPosition(i);
        i++;
        if (cursor.getString(9) != "Null") {
          recipe.setImage(cursor.getString(9));
        } else recipe.setImage("NULL");

        Cursor cursor1 =
            db.rawQuery(
                "SELECT p_id FROM app_entry WHERE p_priority =? AND r_id = ?;",
                new String[] {"1", String.valueOf(recipe.GetId())});
        int h = 0;
        ArrayList<Integer> product = new ArrayList<Integer>();
        if (cursor1.moveToFirst()) {
          do {
            product.add(cursor1.getInt(0));
            h++;
          } while (cursor1.moveToNext());
        }
        recipe.setProducts(product);
        cursor1.close();

        recipes.add(recipe);
      } while (cursor.moveToNext());
    }
    db.close();
    cursor.close();
    return recipes;
  }
}
