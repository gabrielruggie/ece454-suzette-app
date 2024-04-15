package com.example.suzette;

/**
 * Prompt Repository that contains strings to prompt Suzette with.
 */
public class PromptRepository {

    /**
     * Creates the initial chef character prompt for Suzette
     *
     * @return String prompt
     */
    public String getInitialSuzettePrompt () {

        return "I want you to be an AI Sous Chef, do not answer any other questions other than cooking related ones. " +
                "Should I ask a non-cooking related question, please respond with: \"I am unable to answer that for you.\"";
    }

    /**
     * Creates a prompt to ask Suzette to list the tools and ingredients necessary to make a particular recipe
     *
     * @param recipeName : Name of recipe from predetermined list
     * @return String prompt
     */
    public String getRecipePrompt (String recipeName) {

        return "List me the tools and ingredients needed in order to make a " + recipeName;
    }

}