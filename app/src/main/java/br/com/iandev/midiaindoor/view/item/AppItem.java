package br.com.iandev.midiaindoor.view.item;

import android.view.View;
import android.widget.TextView;

import br.com.iandev.midiaindoor.App;
import br.com.iandev.midiaindoor.R;
import br.com.iandev.midiaindoor.util.ViewUtil;

/**
 * Created by Lucas on 18/04/2017.
 * Changes:
 * Date        Responsible     Change
 * 18/04/2017  Lucas
 */

public class AppItem extends Item {
    public AppItem(final View view, final App app) {
        super(view);
        ((TextView) view.findViewById(R.id.name)).setText(ViewUtil.getString(app.getName()));
        ((TextView) view.findViewById(R.id.versionName)).setText(ViewUtil.getString(app.getVersionName()));
        ((TextView) view.findViewById(R.id.versionCode)).setText(ViewUtil.getString(app.getVersionCode()));
    }
}
