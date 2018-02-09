package br.com.iandev.midiaindoor;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.os.SystemClock;

import br.com.iandev.midiaindoor.core.TimeCounter;
import br.com.iandev.midiaindoor.core.services.ServiceAlarmReceiver;
import br.com.iandev.midiaindoor.util.IntervalUtil;

/**
 * Created by Lucas on 21/12/2016.
 */

public final class App {
    private static App instance;

    private final Context context;
    private final String versionName;
    private final Integer versionCode;
    private String name;
    private TimeCounter timeCounter;

    private App(Context context) {
        String versionName;
        int versionCode;

        this.context = context;
        this.name = context.getResources().getText(R.string.app_name).toString();

        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            versionName = packageInfo.versionName;
            versionCode = packageInfo.versionCode;
        } catch (Exception ex) {
            versionName = "";
            versionCode = 0;
        }

        this.versionName = versionName;
        this.versionCode = versionCode;
    }

    public static App getInstance(Context context) {
        if (instance == null) {
            instance = new App(context);
        }
        return instance;
    }

    public static App getInstance() {
        return instance;
    }

    public final void start() {
        Context context = this.getContext().getApplicationContext();

        // Initialize timeCounter
        TimeCounter timeCounter = new TimeCounter();
        this.setTimeCounter(timeCounter);

        // Initialize Service Alarm
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, ServiceAlarmReceiver.class);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        context.sendBroadcast(intent);

        alarmManager.cancel(alarmIntent);
//        alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME, IntervalUtil.parse("00:00:05"), IntervalUtil.parse("01:00:00"), alarmIntent);
        alarmManager.setRepeating(
                AlarmManager.ELAPSED_REALTIME,
                SystemClock.elapsedRealtime()
                /*IntervalUtil.parse("00:00:10")*/,
                IntervalUtil.parse("01:00:00"),
                alarmIntent
        );
    }

    public final void stop() {
//        alarmManager.cancel(alarmIntent);
    }

    public Context getContext() {
        return context;
    }

    public TimeCounter getTimeCounter() {
        return timeCounter;
    }

    public void setTimeCounter(TimeCounter timeCounter) {
        this.timeCounter = timeCounter;
    }

    public String getVersionName() {
        return versionName;
    }

    public Integer getVersionCode() {
        return versionCode;
    }

    public String getName() {
        return name;
    }
}


