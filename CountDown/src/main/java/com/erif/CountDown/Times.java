package com.erif.CountDown;

public class Times {

    private final int days;
    private final String daysStr;

    private final int hours;
    private final String hoursStr;

    private final int minutes;
    private final String minutesStr;

    private final int seconds;
    private final String secondsStr;

    private final long millis;

    public Times(
            int days, String daysStr,
            int hours, String hoursStr,
            int minutes, String minutesStr,
            int seconds, String secondsStr,
            long millis
    ) {
        this.days = days;
        this.daysStr = daysStr;

        this.hours = hours;
        this.hoursStr = hoursStr;

        this.minutes = minutes;
        this.minutesStr = minutesStr;

        this.seconds = seconds;
        this.secondsStr = secondsStr;

        this.millis = millis;
    }

    public int days() {return days;}
    public String daysStr() {return daysStr;}

    public int hours() {return hours;}
    public String hoursStr() {return hoursStr;}

    public int minutes() {
        return minutes;
    }
    public String minutesStr() {
        return minutesStr;
    }

    public int seconds() {
        return seconds;
    }
    public String secondsStr() {
        return secondsStr;
    }

    public long millis() {
        return millis;
    }

}
