package br.com.iandev.midiaindoor.view.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import br.com.iandev.midiaindoor.App;
import br.com.iandev.midiaindoor.R;
import br.com.iandev.midiaindoor.model.Channel;
import br.com.iandev.midiaindoor.model.Device;
import br.com.iandev.midiaindoor.model.Person;
import br.com.iandev.midiaindoor.view.TitleBar;
import br.com.iandev.midiaindoor.view.item.DeviceItem;
import br.com.iandev.midiaindoor.wrap.facede.ChannelFacede;
import br.com.iandev.midiaindoor.wrap.facede.DeviceFacede;
import br.com.iandev.midiaindoor.wrap.facede.PersonFacede;
import br.com.iandev.midiaindoor.wrap.runners.Synchronizer;

/**
 * Created by Lucas on 19/03/2017.
 * Changes:
 * Date        Responsible     Change
 * 19/03/2017  Lucas
 */

public class DeviceActivity extends AutoRefreshActivity {
    private final DeviceFacede deviceController = new DeviceFacede(App.getInstance());
    private final ChannelFacede channelFacede = new ChannelFacede(App.getInstance());
    private final PersonFacede personController = new PersonFacede(App.getInstance());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device);

        new TitleBar(this, findViewById(R.id.title_bar), getResources().getString(R.string.title_device));

        findViewById(R.id.action_sync).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog progressDialog = ProgressDialog.show(DeviceActivity.this, getResources().getString(R.string.message_doing_sync), getResources().getString(R.string.message_wait), false, true);

                new Thread(new Synchronizer(App.getInstance()) {
                    @Override
                    public void onSuccess(Device device) {
                        // Show success message
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(DeviceActivity.this, getResources().getString(R.string.message_success), Toast.LENGTH_LONG).show();
                            }
                        });
                    }

                    @Override
                    public void onError(Exception response) {
                        // Show error message
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(DeviceActivity.this, getResources().getString(R.string.message_error), Toast.LENGTH_LONG).show();
                            }
                        });
                    }

                    @Override
                    public void onComplete() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.dismiss();
                                refresh();
                            }
                        });
                    }
                }).start();
            }
        });
        super.autoRefresh();
    }

    @Override
    protected final void refresh() {
        final View view = findViewById(R.id.device);

        App app = App.getInstance();

        Device device;
        try {
            device = deviceController.get();
        } catch (Exception ex) {
            device = new Device(null);
        }

        Channel channel;
        try {
            channel = channelFacede.get(device.getChannel());
        } catch (Exception ex) {
            channel = new Channel(null);
        }

        Person person;
        try {
            person = personController.get();
        } catch (Exception ex) {
            person = new Person(null);
        }

        new DeviceItem(view, app, device, channel, person);
    }

    @Override
    protected long getDelay() {
        return 5000L;
    }
}
