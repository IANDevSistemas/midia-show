package br.com.iandev.midiaindoor.view.activity;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import br.com.iandev.midiaindoor.R;
import br.com.iandev.midiaindoor.view.TitleBar;

/**
 * Created by Lucas on 21/03/2017.
 */

public class ErrorActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_registry);
        new TitleBar(this, findViewById(R.id.title_bar), getResources().getString(R.string.title_registry));
        setContentView(R.layout.activity_error);

        WebViewClient webViewClient = new WebViewClient();
        WebView webView = (WebView) findViewById(R.id.web_view);

        webView.setInitialScale(1);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);

        webView.setWebViewClient(webViewClient);
        webView.loadUrl("file:///android_asset/www/error/index.html");
    }
}
