package com.example.suzette;

// RecipeActivity.java
import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.os.Bundle;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {

    // Array of sample texts
    private String[] sampleRecipes = {
            "Sample Recipe 1",
            "Sample Recipe 2",
            "Sample Recipe 3",
            "Sample Recipe 4",
            "Sample Recipe 5",
            "Sample Recipe 6",
            "Sample Recipe 7",
            "Sample Recipe 8",
            "Sample Recipe 9",
            "Sample Recipe 10"
    };

    private String[] sampleDescriptions = {
            "Sample Description 1",
            "Sample Description 2",
            "Sample Description 3",
            "Sample Description 4",
            "Sample Description 5",
            "Sample Description 6",
            "Sample Description 7",
            "Sample Description 8",
            "Sample Description 9",
            "Sample Description 10"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_activity);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        EditText search = findViewById(R.id.searchEditText);
        for (int i = 0; i < sampleRecipes.length; i++) {
            if(sampleRecipes[i].toUpperCase().contains(search.getText().toString().toUpperCase())){
                ItemFragment fragment = ItemFragment.newInstance(sampleRecipes[i], sampleDescriptions[i]);
                fragmentTransaction.add(R.id.container, fragment);
            }
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