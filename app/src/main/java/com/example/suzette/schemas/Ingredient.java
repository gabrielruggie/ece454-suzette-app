package com.example.suzette;

public class Ingredient {

    int ingredientID;
    String ingredientName;
    double ingredientMeasurement;

    Ingredient(int id, String name, double measurement){
        this.ingredientID = id;
        this.ingredientName = name;
        this.ingredientMeasurement = measurement;
    }

    public int getID(){
        return ingredientID;
    }
    public String getName(){
        return ingredientName;
    }
    public double getMeasurement(){
        return ingredientMeasurement;
    }
}
