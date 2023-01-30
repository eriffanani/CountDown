package com.erif.CountDown;

import android.os.CountDownTimer;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CountDown {

    private static final int interval = 1000;

    public CountDown(long millis, CountDownListener callback) {
        startTimer((int) millis, callback);
    }

    public CountDown(int seconds, CountDownListener callback) {
        int duration = seconds * 1000;
        startTimer(duration, callback);
    }

    public CountDown(String date, String dateFormat, CountDownListener callback) {
        String currentDateTimeStr = currentDateTime(dateFormat);
        if (currentDateTimeStr != null) {
            Date firstDate = parseDate(date, dateFormat);
            Date currentDate = parseDate(currentDateTimeStr, dateFormat);
            if (firstDate != null && currentDate != null) {
                long count = firstDate.getTime() - currentDate.getTime();
                int duration = (int) count;
                startTimer(duration, callback);
            }
        }
    }

    private void startTimer(int duration, CountDownListener callback) {
        if (duration > 0) {
            Timer timer = new Timer(duration, interval, callback);
            timer.start();
        } else {
            callback.onFinish();
        }
    }

    private static class Timer extends CountDownTimer {
        private final CountDownListener callback;
        private Timer(long millisInFuture, long countDownInterval, CountDownListener callback) {
            super(millisInFuture, countDownInterval);
            this.callback = callback;
        }
        @Override
        public void onTick(long millis) {
            onTickTime(millis, callback);
        }
        @Override
        public void onFinish() {
            callback.onFinish();
        }

    }

    public static class Builder {

        private int duration = 0;
        private final CountDownListener callback;
        private Timer timer;
        private int currentMillis = 0;
        private boolean isRunning = false;
        private boolean useDate = false;

        public Builder(long millis, CountDownListener callback) {
            this.duration = (int) millis;
            this.callback = callback;
        }

        public Builder(int seconds, CountDownListener callback) {
            this.duration = seconds * 1000;
            this.callback = callback;
        }

        public Builder(String date, String dateFormat, CountDownListener callback) {
            this.callback = callback;
            useDate = true;
            String currentDateTimeStr = currentDateTime(dateFormat);
            if (currentDateTimeStr != null) {
                Date firstDate = parseDate(date, dateFormat);
                Date currentDate = parseDate(currentDateTimeStr, dateFormat);
                if (firstDate != null && currentDate != null) {
                    long count = firstDate.getTime() - currentDate.getTime();
                    this.duration = (int) count;
                }
            }
        }

        private class Timer extends CountDownTimer {
            private final CountDownListener callback;
            private Timer(long millisInFuture, long countDownInterval, CountDownListener callback) {
                super(millisInFuture, countDownInterval);
                this.callback = callback;
            }
            @Override
            public void onTick(long millis) {
                isRunning = true;
                currentMillis = (int) millis;
                onTickTime(millis, callback);
            }
            @Override
            public void onFinish() {
                callback.onFinish();
                isRunning = false;
            }

        }

        public void start() {
            if (duration > 0) {
                if (!isRunning) {
                    timer = new Timer(duration, 1000, callback);
                    timer.start();
                }
            } else {
                if (callback != null) {
                    isRunning = false;
                    callback.onFinish();
                }
            }
        }

        public void pause() {
            if (!useDate) {
                if (timer != null) {
                    if (isRunning) {
                        isRunning = false;
                        timer.cancel();
                        if (callback != null)
                            callback.onPause();
                    }
                }
            }
        }

        public void stop() {
            if (timer != null) {
                if (isRunning) {
                    isRunning = false;
                    timer.cancel();
                    if (callback != null)
                        callback.onStop();
                }
            }
        }

        public void resume() {
            if (!useDate) {
                if (!isRunning) {
                    int reduce = duration - currentMillis;
                    int newDuration = duration - reduce;
                    if (duration > 0) {
                        timer = new Timer(newDuration, 1000, callback);
                        timer.start();
                        callback.onResume();
                    } else {
                        if (callback != null)
                            callback.onFinish();
                    }
                }
            }
        }
    }

    private static void onTickTime(long millis, CountDownListener callback) {
        int days = days(millis);
        String daysStr = str(days);

        int hours = hours(millis);
        String hoursStr = str(hours);

        int minutes = minutes(millis);
        String minutesStr = str(minutes);

        int seconds = seconds(millis);
        String secondsStr = str(seconds);

        callback.onTick(new Times(
                days, daysStr,
                hours, hoursStr,
                minutes, minutesStr,
                seconds, secondsStr,
                millis
        ));
    }

    private static int days(long millis) {
        long days = millis / (24 * 60 * 60 * 1000);
        return (int) days;
    }

    private static int hours(long millis) {
        long hours = millis / (60 * 60 * 1000) % 24;
        return (int) hours;
    }

    private static int minutes(long millis) {
        long minutes = millis / (60 * 1000) % 60;
        return (int) minutes;
    }

    private static int seconds(long millis) {
        long seconds = millis / 1000 % 60;
        return (int) seconds;
    }

    private static String str(int value) {
        return value < 10 ? "0"+value : ""+value;
    }

    public static long hour(int hour) {
        return minutes(hour) * 60;
    }

    public static long hour(double hour) {
        double count = minutes(hour) * 60;
        return (long) count;
    }

    public static long minutes(int minutes) {
        return (60 * 1000) * minutes;
    }

    public static long minutes(double minutes) {
        double count = (60 * 1000) * minutes;
        return (long) count;
    }

    private static String currentDateTime(String format) {
        String result;
        try {
            SimpleDateFormat mFormat = new SimpleDateFormat(format, Locale.getDefault());
            result = mFormat.format(new Date());
            return result;
        } catch (Exception e) {
            error(e.getMessage());
            return null;
        }
    }

    private static Date parseDate(String dateTime, String format) {
        Date result;
        try {
            SimpleDateFormat mFormat = new SimpleDateFormat(format, Locale.getDefault());
            result = mFormat.parse(dateTime);
            return result;
        } catch (ParseException e) {
            error(e.getMessage());
            return null;
        }
    }

    private static void error(String message) {
        Log.e("Countdown", message == null ? "Empty message" : message);
    }

}
