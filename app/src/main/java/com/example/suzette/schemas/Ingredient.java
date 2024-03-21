package com.example.suzette.schemas;

public class Ingredient {

    private int ingredientID;
    private String ingredientName;
    private double ingredientMeasurement;

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
