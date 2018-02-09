package br.com.iandev.midiaindoor.view;

/**
 * Created by Lucas on 06/04/2017.
 * Changes:
 * Date        Responsible     Change
 * 06/04/2017  Lucas
 */

public class WebViewClient extends android.webkit.WebViewClient {
    // run onPageFinished using javascript injection
    @Override
    public void onPageFinished(android.webkit.WebView view, String url) {
        super.onPageFinished(view, url);
        view.loadUrl(String.format("javascript:onPageFinished(%s);", new PageArgs().toString()));
    }


}
