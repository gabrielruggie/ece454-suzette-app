package com.example.suzette;

// RecipeActivity.java
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {

    // Array of sample texts
    private String[] sampleTexts = {
            "Sample Text 1",
            "Sample Text 2",
            "Sample Text 3",
            "Sample Text 4",
            "Sample Text 5",
            "Sample Text 6",
            "Sample Text 7",
            "Sample Text 8",
            "Sample Text 9",
            "Sample Text 10"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_activity);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        for (String text : sampleTexts) {
            ItemFragment fragment = ItemFragment.newInstance(text);
            fragmentTransaction.add(R.id.container, fragment);
        }

        fragmentTransaction.commit();
    }
}