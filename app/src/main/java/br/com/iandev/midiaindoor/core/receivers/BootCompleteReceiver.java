package br.com.iandev.midiaindoor.core.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import br.com.iandev.midiaindoor.R;
import br.com.iandev.midiaindoor.view.activity.MainActivity;

/**
 * Created by Lucas on 13/11/2016.
 */

public class BootCompleteReceiver extends BroadcastReceiver {
    private static final String TAG = BootCompleteReceiver.class.getCanonicalName();

    @Override
    public void onReceive(Context context, Intent intent) {
        //C:\Users\Lucas\AppData\Local\Android\sdk\platform-tools\adb shell am broadcast -a android.intent.action.BOOT_COMPLETED
        Toast.makeText(context,
                String.format("%s : %s",
                        context.getString(R.string.app_name),
                        context.getString(R.string.message_starting)),
                Toast.LENGTH_LONG)
                .show();
        Intent mainActivityIntent = new Intent(context, MainActivity.class);
        mainActivityIntent.setFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(mainActivityIntent);
    }
}