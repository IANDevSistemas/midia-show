package br.com.iandev.midiaindoor.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import br.com.iandev.midiaindoor.R;
import br.com.iandev.midiaindoor.view.WebView;
import br.com.iandev.midiaindoor.view.WebViewClient;

/**
 * Created by Lucas on 07/01/2017.
 */

public class StartActivity extends AppCompatActivity {
    private static long SPLASH_TIME_OUT = 5000L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
    }

    @Override
    protected void onStart() {
        super.onStart();

        WebView webView = (WebView) findViewById(R.id.web_view);
        webView.init();
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("file:///android_asset/www/start/index.html");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(StartActivity.this, MainActivity.class));
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
