package br.com.iandev.midiaindoor.view.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import br.com.iandev.midiaindoor.App;
import br.com.iandev.midiaindoor.R;
import br.com.iandev.midiaindoor.view.TitleBar;
import br.com.iandev.midiaindoor.wrap.runners.Licensor;
import br.com.iandev.midiaindoor.wrap.settings.LicenseSettings;

/**
 * Created by Lucas on 17/12/2016.
 * Changes:
 * Date        Responsible     Change
 * 17/12/2016  Lucas
 */

public class LicenseActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registry);

        new TitleBar(this, findViewById(R.id.title_bar), getResources().getString(R.string.title_registry));

        final LicenseSettings licenseSettings = new LicenseSettings(App.getInstance());
        loadSettings(licenseSettings);

        findViewById(R.id.apply).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog progressDialog = ProgressDialog.show(getContext(), getResources().getString(R.string.message_doing_registry), getResources().getString(R.string.message_wait), false, true);
                saveSettings(licenseSettings);
                loadSettings(licenseSettings);

                new Thread(new Licensor(App.getInstance(), licenseSettings) {
                    public void onSuccess(LicenseSettings response) {
                        // Show success message
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getContext(), getResources().getString(R.string.message_success), Toast.LENGTH_LONG).show();
                            }
                        });
                    }

                    public void onError(Exception response) {
                        // Show error message
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getContext(), getResources().getString(R.string.message_error), Toast.LENGTH_LONG).show();
                            }
                        });
                    }

                    public void onComplete() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.dismiss();
                                loadSettings(licenseSettings);
                            }
                        });
                    }
                }).start();
            }
        });
    }

    private void loadSettings(LicenseSettings licenceSettings) {
        findViewById(R.id.deviceCode).setEnabled(false);
        ((TextView) findViewById(R.id.deviceCode)).setText(licenceSettings.getCode());

        findViewById(R.id.deviceID).setEnabled(false);
        ((TextView) findViewById(R.id.deviceID)).setText(licenceSettings.getDeviceId());

        ((TextView) findViewById(R.id.deviceDescription)).setText(licenceSettings.getDeviceDescription());
        ((TextView) findViewById(R.id.ownerID)).setText(licenceSettings.getOwnerId());
        ((TextView) findViewById(R.id.password)).setText(licenceSettings.getPassword());
        ((TextView) findViewById(R.id.url)).setText(licenceSettings.getURL());
    }

    private void saveSettings(LicenseSettings licenceSettings) {
        licenceSettings.setDeviceDescription(((TextView) findViewById(R.id.deviceDescription)).getText().toString());
        licenceSettings.setOwnerID(((TextView) findViewById(R.id.ownerID)).getText().toString());
        licenceSettings.setPassword(((TextView) findViewById(R.id.password)).getText().toString());
        licenceSettings.setURL(((TextView) findViewById(R.id.url)).getText().toString());
    }

    private Context getContext() {
        return this;
    }

}
