package br.com.iandev.midiaindoor.view;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;

/**
 * Created by Lucas on 06/04/2017.
 * Changes:
 * Date        Responsible     Change
 * 06/04/2017  Lucas
 */

public class WebView extends android.webkit.WebView {
    public WebView(Context context) {
        super(context);
    }

    public WebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void init() {
        this.setInitialScale(1);
        this.getSettings().setJavaScriptEnabled(true);
        this.getSettings().setUseWideViewPort(true);
        this.getSettings().setDomStorageEnabled(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR_MR1) {
            this.getSettings().setLoadWithOverviewMode(true);
        }
//        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
//        webView.setScrollbarFadingEnabled(false);

        // Used to allow autoplay on html5's video tag
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            this.getSettings().setMediaPlaybackRequiresUserGesture(false);
        }
    }

}
