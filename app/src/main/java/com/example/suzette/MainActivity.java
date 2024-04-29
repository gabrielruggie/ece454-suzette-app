package com.example.suzette;

// RecipeActivity.java

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


public class MainActivity extends AppCompatActivity {

    // Array of sample text
    private String[] sampleRecipes = {
            "Ceasar Salad",
            "Omelette",
            "Mac & Cheese",
            "Seseame Chicken",
            "Churros",
    };
  
    private String[] sampleDescriptions = {
            "Ingredients: Lettuce, Croutons, Parmesan Cheese, Caesar Dressing, Olive Oil, Lemon Juice, Garlic, Mustard, Salt, Pepper",
            "Ingredients: Eggs, Butter, Salt, Pepper",
            "Ingredients: Pasta, Milk, Cheese, Butter, Flour, Salt, Pepper, Paprika, Garlic Powder ",
            "Ingredients: Chicken Breasts, Cornstarch, Eggs, Salt, Pepper, Soy Sauce, Brown Sugar, Sesame Oil, Minced Garlic Apple Cider Vinegar, Vegetable Oil",
            "Ingredients: Sugar, Salt, Butter, Flour, Cinnamon, Flour",
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_activity);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

//        EditText search = findViewById(R.id.searchEditText);
//        for (int i = 0; i < sampleRecipes.length; i++) {
//            ItemFragment fragment = ItemFragment.newInstance(sampleRecipes[i], sampleDescriptions[i]);
//            fragmentTransaction.add(R.id.container, fragment);
//        }
        for (int i = 0; i < sampleRecipes.length; i++) {
            ItemFragment fragment = ItemFragment.newInstance(sampleRecipes[i], sampleDescriptions[i]);
            fragmentTransaction.add(R.id.container, fragment);
        }

        fragmentTransaction.commit();
    }

    protected void onStart() {
        super.onStart();
    }
    protected void onRestart(){
        super.onRestart();
    }
    protected void onResume() {
        super.onResume();
    }
    protected void onPause() {
        super.onPause();
    }
    protected void onStop() {
        super.onStop();
    }
    protected void onDestroy() {
        super.onDestroy();
    }
    public void startRecipeActivity(View v) {
        Intent intent = new Intent(MainActivity.this, RecipeActivity.class);
        startActivity(intent);
    }
    public void startCookingActivity(View v) {
        Intent intent = new Intent(MainActivity.this, CookingActivity.class);
        startActivity(intent);
        //setContentView(R.layout.activity_cooking_activity);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        for (int i = 0; i < sampleRecipes.length; i++) {
            ItemFragment fragment = ItemFragment.newInstance(sampleRecipes[i], sampleDescriptions[i]);
            fragmentTransaction.add(R.id.container, fragment);
        }

        fragmentTransaction.commit();
    }

    /**
     * Create a temporary Timer view as instructed by User
     * The idea is that the User will command Suzette to set a timer. We can parse the time out of
     * message and set a Timer on the backend.
     *
     * @param timeInMillis : Set time requested in milliseconds
     */
    private void startCountdownTimer(long timeInMillis) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.timer_dialog, null);

        final TextView timerTextView = dialogView.findViewById(R.id.timerTextView);

        final CountDownTimer countDownTimer = new CountDownTimer(timeInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int seconds = (int) (millisUntilFinished / 1000);
                int minutes = seconds / 60;
                seconds %= 60;

                timerTextView.setText(String.format("%02d:%02d", minutes, seconds));
            }

            @Override
            public void onFinish() {
                timerTextView.setText("00:00");
                builder.show().dismiss();
            }
        };

        builder.setView(dialogView)
                .setTitle("Countdown Timer")
                .setPositiveButton("Start", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        countDownTimer.start();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        countDownTimer.cancel();
                    }
                })
                .show();

    }
}