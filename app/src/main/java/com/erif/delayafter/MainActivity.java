package com.erif.delayafter;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.erif.CountDown.CountDown;
import com.erif.CountDown.CountDownListener;
import com.erif.CountDown.Times;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView txt = findViewById(R.id.txt);

        /* Normal
        new CountDown(CountDown.minutes(1.5), new CountDownListener() {
            @Override
            public void onFinish() {
                txt.setText("Finish");
            }
            @Override
            public void onTick(Times times) {
                String time = times.hoursStr()+":"+times.minutesStr()+":"+times.secondsStr();
                txt.setText(time);
            }
        });*/

        /* Builder
        CountDown.Builder countdown = new CountDown.Builder(
                CountDown.minutes(1), new CountDownListener() {
            @Override
            public void onFinish() {
                txt.setText("Finish");
            }

            @Override
            public void onTick(Times times) {
                String time = times.hoursStr()+":"+times.minutesStr()+":"+times.secondsStr();
                txt.setText(time);
            }
        });
        countdown.start();

        new CountDown(2, () -> {
            countdown.pause();
        });

        new CountDown(2, () -> {
            countdown.resume();
        });*/

        // Use Date
        new CountDown("2022-10-30 14:00:00",
                "yyyy-MM-dd HH:mm:ss", new CountDownListener() {
            @Override
            public void onFinish() {
                txt.setText("Finish");
            }

            @Override
            public void onTick(Times times) {
                String time = times.daysStr()+":"+times.hoursStr()+":"+times.minutesStr()+":"+times.secondsStr();
                txt.setText(time);
            }
        });

    }

}