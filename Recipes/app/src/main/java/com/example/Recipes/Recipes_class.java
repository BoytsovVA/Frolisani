package com.example.Recipes;

import java.util.ArrayList;

public class Recipes_class {
  private int id;
  private String name;
  private String character;
  private String instruction;
  private String product;
  private String time;
  private String level;
  private String image;
  private int block;
  private int favorites;
  private int position;
  ArrayList<Integer> products;

  public ArrayList<Integer> getProducts() {
    return products;
  }

  public void setProducts(ArrayList<Integer> products) {
    this.products = products;
  }

  public Recipes_class() {
    position = 0;
  }

  public Recipes_class(
      int id,
      String name,
      String character,
      String instruction,
      String product,
      String time,
      String level,
      int block,
      int favorites,
      int position) {
    this.id = id;
    this.name = name;
    this.character = character;
    this.instruction = instruction;
    this.product = product;
    this.time = time;
    this.level = level;
    this.block = block;
    this.favorites = favorites;
    this.position = position;
  }

  public int GetPosition() {
    return position;
  }

  public int GetId() {
    return id;
  }

  public String GetName() {
    return name;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getCharacter() {
    return character;
  }

  public String getInstruction() {
    return instruction;
  }

  public String getProduct() {
    return product;
  }

  public String getTime() {
    return time;
  }

  public String getLevel() {
    return level;
  }

  public int getBlock() {
    return block;
  }

  public int getFavorites() {
    return favorites;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setCharacter(String character) {
    this.character = character;
  }

  public void setInstruction(String instruction) {
    this.instruction = instruction;
  }

  public void setProduct(String product) {
    this.product = product;
  }

  public void setTime(String time) {
    this.time = time;
  }

  public void setLevel(String level) {
    this.level = level;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public void setBlock(int block) {
    this.block = block;
  }

  public void setFavorites(int favorites) {
    this.favorites = favorites;
  }

  public void setPosition(int position) {
    this.position = position;
  }

  public String getImage() {
    return image;
  }

  public int GetBlock() {
    return block;
  }

  public int GetFavorite() {
    return favorites;
  }
}
