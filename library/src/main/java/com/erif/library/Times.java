package com.erif.library;

public class Times {

    private final long millis;
    private final int seconds;
    private final int minutes;
    private final int hours;
    private final int days;

    public Times(long millis, int seconds, int minutes, int hours, int days) {
        this.millis = millis;
        this.seconds = seconds;
        this.minutes = minutes;
        this.hours = hours;
        this.days = days;
    }

    public long getMillis() {
        return millis;
    }

    public int getSeconds() {
        return seconds;
    }

    public int getMinutes() {
        return minutes;
    }

    public int getHours() {
        return hours;
    }

    public int getDays() {
        return days;
    }
}
