package com.erif.CountDown;

import android.os.CountDownTimer;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CountDown {

    private static final int interval = 1000;
    private int duration = 0;
    private Boolean loop = false;
    private final CountDownListener callback;

    public CountDown(long millis, CountDownListener callback) {
        this.duration = (int) millis;
        this.callback = callback;
        startTimer();
    }

    public CountDown(int seconds, CountDownListener callback) {
        this.duration = seconds * 1000;
        this.callback = callback;
        startTimer();
    }

    public CountDown(int seconds, Boolean loop, CountDownListener callback) {
        this.duration = seconds * 1000;
        this.loop = loop;
        this.callback = callback;
        startTimer();
    }

    public CountDown(String date, String dateFormat, CountDownListener callback) {
        this.callback = callback;
        String currentDateTimeStr = currentDateTime(dateFormat);
        if (currentDateTimeStr != null) {
            Date firstDate = parseDate(date, dateFormat);
            Date currentDate = parseDate(currentDateTimeStr, dateFormat);
            if (firstDate != null && currentDate != null) {
                long count = firstDate.getTime() - currentDate.getTime();
                this.duration = (int) count;
                startTimer();
            }
        }
    }

    public CountDown(String date, String dateFormat, Locale locale, CountDownListener callback) {
        this.callback = callback;
        String currentDateTimeStr = currentDateTime(dateFormat, locale);
        if (currentDateTimeStr != null) {
            Date firstDate = parseDate(date, dateFormat, locale);
            Date currentDate = parseDate(currentDateTimeStr, dateFormat, locale);
            if (firstDate != null && currentDate != null) {
                long count = firstDate.getTime() - currentDate.getTime();
                this.duration = (int) count;
                startTimer();
            }
        }
    }

    private void startTimer() {
        if (duration > 0) {
            Timer timer = new Timer(duration, interval, loop, callback);
            timer.start();
        } else {
            callback.onFinish();
        }
    }

    private static class Timer extends CountDownTimer {
        private final CountDownListener callback;
        private final long millisInFuture;
        private final Boolean loop;
        private Timer(long millisInFuture, long countDownInterval, Boolean loop, CountDownListener callback) {
            super(millisInFuture, countDownInterval);
            this.millisInFuture = millisInFuture;
            this.callback = callback;
            this.loop = loop;
        }
        @Override
        public void onTick(long millis) {
            onTickTime(millis, callback);
        }
        @Override
        public void onFinish() {
            callback.onFinish();
            if (loop) {
                Timer timer = new Timer(millisInFuture, interval, true, callback);
                timer.start();
            }
        }

    }

    public static class Builder {

        private static final int interval = 1000;
        private int duration = 0;
        private Boolean loop = false;
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

        public Builder(int seconds, Boolean loop, CountDownListener callback) {
            this.duration = seconds * 1000;
            this.loop = loop;
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

        public Builder(String date, String dateFormat, Locale locale, CountDownListener callback) {
            this.callback = callback;
            useDate = true;
            String currentDateTimeStr = currentDateTime(dateFormat, locale);
            if (currentDateTimeStr != null) {
                Date firstDate = parseDate(date, dateFormat, locale);
                Date currentDate = parseDate(currentDateTimeStr, dateFormat, locale);
                if (firstDate != null && currentDate != null) {
                    long count = firstDate.getTime() - currentDate.getTime();
                    this.duration = (int) count;
                }
            }
        }

        private class Timer extends CountDownTimer {
            private final CountDownListener callback;
            private final Boolean loop;
            private Timer(long millisInFuture, long countDownInterval, Boolean loop, CountDownListener callback) {
                super(millisInFuture, countDownInterval);
                this.loop = loop;
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
                if (loop) {
                    timer = new Timer(duration, interval, true, callback);
                    timer.start();
                }
            }

        }

        public void start() {
            if (duration > 0) {
                if (!isRunning) {
                    timer = new Timer(duration, interval, loop, callback);
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
                        timer = new Timer(newDuration, interval, loop, callback);
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

    private static String currentDateTime(String format, Locale locale) {
        String result;
        try {
            SimpleDateFormat mFormat = new SimpleDateFormat(format, locale);
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

    private static Date parseDate(String dateTime, String format, Locale locale) {
        Date result;
        try {
            SimpleDateFormat mFormat = new SimpleDateFormat(format, locale);
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
