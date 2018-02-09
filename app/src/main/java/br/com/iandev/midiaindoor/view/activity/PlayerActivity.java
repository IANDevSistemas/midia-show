package br.com.iandev.midiaindoor.view.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.WindowManager;

import br.com.iandev.midiaindoor.R;
import br.com.iandev.midiaindoor.core.services.PlayerService;
import br.com.iandev.midiaindoor.view.WebView;
import br.com.iandev.midiaindoor.view.WebViewClient;

import static br.com.iandev.midiaindoor.core.services.PlayerService.MESSAGE_LOAD_URL;

/**
 * Created by Lucas on 13/11/2016.
 * Changes:
 * Date        Responsible     Change
 * 13/11/2016  Lucas
 */

public class PlayerActivity extends FullscreenActivity {
    public static final String INDEX_URL = "file:///android_asset/www/clock/index.html";
    LocalBroadcastManager localBroadcastManager;
    private WebView webView;
    private BroadcastReceiver changeURLBroadcastReceiver;

    public PlayerActivity() {
        super(R.layout.activity_player, R.id.web_view);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    @Override
    protected void onStart() {
        super.onStart();
        startBroadcastReceiver();
        startWebView();
        startService(new Intent(getContext(), PlayerService.class));
    }

    @Override
    protected void onPause() {
        try {
            stopBroadcastReceiver();
        } catch (Exception ex) {
            // Silence is golden
        }

        try {
            stopWebView();
        } catch (Exception ex) {
            // Silence is golden
        }

        try {
            stopService(new Intent(getContext(), PlayerService.class));
        } catch (Exception ex) {
            // Silence is golden
        }

        super.onPause();
        super.finish();
    }

    private void stopWebView() {
        loadURL("about:blank");
//        webView.getSettings().setJavaScriptEnabled(false);
//        webView.clearHistory();
//        webView.clearCache(true);
//        webView.loadUrl("about:blank");
//        webView.pauseTimers();
//        webView.onPause();
//        webView = null;
//        webView.destroy();
    }

    private void startBroadcastReceiver() {
        localBroadcastManager = LocalBroadcastManager.getInstance(this);
        changeURLBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String url = intent.getStringExtra(MESSAGE_LOAD_URL);
                loadURL(url.equals("about:blank") ? INDEX_URL : url);
            }
        };
        registerReceiver(changeURLBroadcastReceiver, new IntentFilter(MESSAGE_LOAD_URL));
    }

    private void stopBroadcastReceiver() {
        unregisterReceiver(changeURLBroadcastReceiver);
    }

    private void loadURL(String url) {
        webView.loadUrl(url);
    }

    private void startWebView() {
        webView = (WebView) findViewById(R.id.web_view);
        webView.init();
        webView.setWebViewClient(new WebViewClient());
        loadURL(INDEX_URL);
    }

    private Context getContext() {
        return this;
    }
}
