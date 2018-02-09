package br.com.iandev.midiaindoor.view.activity;

import android.os.Bundle;
import android.view.View;

/**
 * Activity for full screen aplications
 */
public class FullscreenActivity extends BaseActivity {

    private final int contentViewId;
    private final int mainViewId;

    private View mainView;

    FullscreenActivity(int contentViewId, final int mainViewId) {
        this.contentViewId = contentViewId;
        this.mainViewId = mainViewId;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();

        setContentView(contentViewId);
        mainView = findViewById(mainViewId);

        mainView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

}
