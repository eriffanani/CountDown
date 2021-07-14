package com.erif.library;

import android.os.CountDownTimer;

public class Delay {

    private static final int interval = 1000;
    private int duration = 1000;

    public Delay(int minutes) {
        this.duration = minutes * 60000;
    }

    public Delay(double minutes) {
        double count = minutes * 60000;
        this.duration = (int) count;
    }

    public Delay(int seconds, DelayCallback callback) {
        int duration = seconds * 1000;
        startTimer(duration, callback);
    }

    public Delay(double seconds, DelayCallback callback) {
        double duration = seconds * 1000;
        startTimer((int) duration, callback);
    }

    private void startTimer(int duration, DelayCallback callback) {
        Timer timer = new Timer(duration, interval, callback);
        timer.start();
    }

    private static class Timer extends CountDownTimer {
        private final DelayCallback callback;
        private Timer(long millisInFuture, long countDownInterval, DelayCallback callback) {
            super(millisInFuture, countDownInterval);
            this.callback = callback;
        }
        @Override
        public void onTick(long millis) {
            int seconds = toSeconds(millis);
            int minutes = toMinutes(millis);
            int hours = toHour(millis);
            int days = toDays(millis);
            callback.onTick(
                    new Times(millis, seconds, minutes, hours, days)
            );
        }
        @Override
        public void onFinish() {
            callback.onFinish();
        }
    }

    public void action(DelayCallback callback) {
        startTimer(duration, callback);
    }

    public static int toSeconds(long millis) {
        long seconds = millis / 1000 % 60;
        return (int) seconds;
    }

    public static int toMinutes(long millis) {
        long minutes = millis / (60 * 1000) % 60;
        return (int) minutes;
    }

    public static int toHour(long millis) {
        long hours = millis / (60 * 60 * 1000) % 24;
        return (int) hours;
    }

    public static int toDays(long millis) {
        long days = millis / (24 * 60 * 60 * 1000);
        return (int) days;
    }

}
