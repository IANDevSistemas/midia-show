package br.com.iandev.midiaindoor.core;

import android.os.SystemClock;

import java.util.Date;

/**
 * Created by Lucas on 20/03/2017.
 */

public class TimeCounter {
    private Date date;
    private long elapsedTime;

    public TimeCounter() {
        this.setDate(new Date());
        this.setElapsedTime(SystemClock.elapsedRealtime());
    }

    public TimeCounter(Date date) {
        if (date == null) {
            throw new IllegalArgumentException("date must not be null");
        }
        this.setDate(date);
        this.setElapsedTime(SystemClock.elapsedRealtime());
    }

    public long getTime() {
        return new Date(getDate().getTime() + SystemClock.elapsedRealtime() - this.getElapsedTime()).getTime();
    }

    public Date getDate() {
        return new Date(date.getTime() + SystemClock.elapsedRealtime() - this.getElapsedTime());
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(long elapsedTime) {
        this.elapsedTime = elapsedTime;
    }
}
