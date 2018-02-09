package br.com.iandev.midiaindoor.view.activity;

import android.content.Intent;
import android.os.Bundle;

import br.com.iandev.midiaindoor.App;
import br.com.iandev.midiaindoor.R;
import br.com.iandev.midiaindoor.wrap.License;
import br.com.iandev.midiaindoor.wrap.runners.Starter;

public class MainActivity extends BaseActivity {
    private static boolean started;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!started) {
            new Thread(new Starter(App.getInstance(this)) {
                @Override
                public void onSuccess(final App app) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (new License(app).isLicenceValid()) {
                                startActivity(new Intent(MainActivity.this, PlayerActivity.class));
                            } else {
                                startActivity(new Intent(MainActivity.this, LicenseActivity.class));
                            }
                        }
                    });
                }

                @Override
                public void onError(Exception ex) {
                    ex.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            startActivity(new Intent(MainActivity.this, ErrorActivity.class));
                            finish();
                        }
                    });
                }
            }).start();
            started = true;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isFinishing()) {
            started = false;
            App.getInstance().stop();
        }
    }
}
