package br.com.iandev.midiaindoor.view.activity;

import android.os.Bundle;

import br.com.iandev.midiaindoor.R;
import br.com.iandev.midiaindoor.view.WebView;
import br.com.iandev.midiaindoor.view.WebViewClient;

/**
 * Created by Lucas on 06/04/2017.
 * Changes:
 * Date        Responsible     Change
 * 06/04/2017  Lucas
 */

public class ClockActivity extends AutoRefreshActivity {
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
    }

    @Override
    protected void onStart() {
        super.onStart();
        webView = (WebView) findViewById(R.id.web_view);
        webView.init();
        webView.setWebViewClient(new WebViewClient());
        super.autoRefresh();
    }

    @Override
    protected void refresh() {
        webView.loadUrl("file:///android_asset/www/clock/index.html");
    }

    @Override
    protected long getDelay() {
        return 10000L;
    }
}
