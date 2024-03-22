package com.example.suzette.schemas;

import java.util.List;

public class Recipe{

    private int recipeID;
    private String recipeName;
    private String recipeDescription;
    private List<String> recipeTags;
    private String recipeGenre;
    private List<String> recipeInstructions;
    private List<String> recipeIngredients;

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
