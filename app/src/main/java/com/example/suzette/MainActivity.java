package com.example.suzette;

import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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