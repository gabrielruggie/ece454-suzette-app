package com.example.suzette;

import java.util.List;

public class Recipe{

    int recipeID;
    String recipeName;
    String recipeDescription;
    List<String> recipeTags;
    String recipeGenre;
    List<String> recipeInstructions;
    List<String> recipeIngredients;

    Recipe(int id, String name, String description, List<String> tags, String genre, List<String> instructions, List<String> ingredients){
        this.recipeID = id;
        this.recipeName = name;
        this.recipeDescription = description;
        this.recipeTags = tags;
        this.recipeGenre = genre;
        this.recipeInstructions = instructions;
        this.recipeIngredients = ingredients;
    }

    public int getID(){
        return this.recipeID;
    }

    public String getName(){
        return this.recipeName;
    }

    public String getDescription(){
        return this.recipeDescription;
    }

    public List<String> getTags(){
        return this.recipeTags;
    }

    public String getGenre(){
        return this.recipeGenre;
    }

    public List<String> getInstructions(){
        return this.recipeInstructions;
    }

    public List<String> getIngredients(){
        return this.recipeIngredients;
    }

}
