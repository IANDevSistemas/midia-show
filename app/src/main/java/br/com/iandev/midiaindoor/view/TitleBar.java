package br.com.iandev.midiaindoor.view;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import br.com.iandev.midiaindoor.R;

/**
 * Created by Lucas on 30/12/2016.
 */

public class TitleBar {
    public TitleBar(final Activity activity, final View bar, final String title) {
        ((TextView) bar.findViewById(R.id.title)).setText(title);
        bar.findViewById(R.id.button_goto_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.onBackPressed();
            }
        });
    }
}
