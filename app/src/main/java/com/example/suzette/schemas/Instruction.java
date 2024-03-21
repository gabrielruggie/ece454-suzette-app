package com.example.suzette;

import java.util.List;

public class Instruction{

    int instructionID;
    String instructionDescription;
    double instructionTemperature;

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