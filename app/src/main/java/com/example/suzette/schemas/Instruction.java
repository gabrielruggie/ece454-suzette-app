package com.example.suzette.schemas;

public class Instruction{

    private int instructionID;
    private String instructionDescription;
    private double instructionTemperature;

    Instruction(int id, String description, double temperature){
        this.instructionID = id;
        this.instructionDescription = description;
        this.instructionTemperature = temperature;
    }

    public int getID(){
        return this.instructionID;
    }

    public String getDescription(){
        return this.instructionDescription;
    }

    public double getTemperature(){
        return this.instructionTemperature;
    }

}