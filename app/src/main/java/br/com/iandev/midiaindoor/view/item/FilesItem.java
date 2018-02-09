package br.com.iandev.midiaindoor.view.item;

import android.view.View;
import android.widget.TextView;

import br.com.iandev.midiaindoor.R;
import br.com.iandev.midiaindoor.util.Files;
import br.com.iandev.midiaindoor.util.ViewUtil;

/**
 * Created by Lucas on 18/04/2017.
 * Changes:
 * Date        Responsible     Change
 * 18/04/2017  Lucas
 */

public class FilesItem extends Item {
    public FilesItem(View view) {
        super(view);
        ((TextView) view.findViewById(R.id.location)).setText(ViewUtil.getString(Files.getAppDirectory()));
        ((TextView) view.findViewById(R.id.size)).setText(ViewUtil.getString(Files.size()));
    }
}
