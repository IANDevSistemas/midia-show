package br.com.iandev.midiaindoor.view.activity;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.ArrayList;

import br.com.iandev.midiaindoor.R;
import br.com.iandev.midiaindoor.core.services.ManagementService;
import br.com.iandev.midiaindoor.core.services.PlayerService;
import br.com.iandev.midiaindoor.core.services.SyncService;
import br.com.iandev.midiaindoor.view.TitleBar;

/**
 * Created by Lucas on 30/12/2016.
 */

public class ServiceActivity extends AutoRefreshActivity {
    private ListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

        new TitleBar(this, findViewById(R.id.title_bar), getResources().getString(R.string.title_service));

        adapter = new ListAdapter();
        adapter.setList(new ArrayList<Class<?>>() {{

            add(ManagementService.class);
            add(PlayerService.class);
            add(SyncService.class);

        }});
        super.autoRefresh();
    }

    @Override
    protected void refresh() {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                ListView listView = (ListView) findViewById(R.id.list);
                listView.setAdapter(adapter);
            }
        });
    }

    private boolean isServiceRunning(Class clazz) {
        final ActivityManager activityManager = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo runningServiceInfo : activityManager.getRunningServices(Integer.MAX_VALUE)) {
            if (runningServiceInfo.service.equals(clazz)) {
                return true;
            }
        }
        return false;
    }

    private class ListAdapter extends br.com.iandev.midiaindoor.view.ListAdapter<Class<?>> {
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = ServiceActivity.this.getLayoutInflater().inflate(R.layout.item_service, null);

            final Class<?> clazz = super.getItem(position);

            ((TextView) view.findViewById(R.id.name)).setText(clazz.getSimpleName());

            ToggleButton toogleButton = (ToggleButton) view.findViewById(R.id.status);
            toogleButton.setChecked(isServiceRunning(clazz));
            toogleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        startService(new Intent(getApplicationContext(), clazz));
                    } else {
                        stopService(new Intent(getApplicationContext(), clazz));
                    }
                    ServiceActivity.super.autoRefresh();
                }
            });

            return view;
        }
    }
}


