package br.com.iandev.midiaindoor.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;

import br.com.iandev.midiaindoor.R;


/**
 * Created by Lucas on 14/12/2016.
 */

public class NotificationUtil extends AppCompatActivity {
    private static final String TAG = NotificationUtil.class.getCanonicalName();

    public static void create(Context context, Intent intent, String title, String content) {
        create(0, context, intent, title, content);
    }

    public static void create(int id, Context context, Intent intent, String title, String content) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent notificatioIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                .setContentIntent(notificatioIntent)
                .setSmallIcon(R.drawable.ic_launcher_product)
                .setContentTitle(title)
                .setContentText(content)
                .setAutoCancel(true);

        Notification notification = notificationBuilder.build();

        notificationManager.notify(TAG, id, notification);
    }
}
