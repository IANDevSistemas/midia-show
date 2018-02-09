package br.com.iandev.midiaindoor.view.activity;

import android.os.Handler;

/**
 * Created by Lucas on 06/04/2017.
 * Changes:
 * Date        Responsible     Change
 * 06/04/2017  Lucas
 */

public abstract class AutoRefreshActivity extends BaseActivity {
    private final Handler handler = new Handler();

    protected abstract void refresh();

    protected void autoRefresh() {
        refresh();
        new Thread() {
            @Override
            public void run() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        autoRefresh();
                    }
                }, getDelay());
            }
        }.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        handler.removeCallbacksAndMessages(null);
    }

    protected long getDelay() {
        return 10000L;
    }
}
