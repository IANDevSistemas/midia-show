package br.com.iandev.midiaindoor.core.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Lucas on 13/04/2017.
 * Changes:
 * Date        Responsible     Change
 * 13/04/2017  Lucas
 */

public class ServiceAlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (LicenseService.mustWakeUp()) {
            context.startService(new Intent(context, LicenseService.class));
        }
        if (SyncService.mustWakeUp()) {
            context.startService(new Intent(context, SyncService.class));
        }
        if (ManagementService.mustWakeUp()) {
            context.startService(new Intent(context, ManagementService.class));
        }
    }
}
