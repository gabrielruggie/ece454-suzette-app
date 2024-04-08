package com.example.suzette;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class CookingActivity extends AppCompatActivity {
    public void onCreate(Bundle savedInstanceState){

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
        Intent intent = new Intent(CookingActivity.this, RecipeActivity.class);
        startActivity(intent);
    }
    public void startMainActivity(View v) {
        Intent intent = new Intent(CookingActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
