package br.com.iandev.midiaindoor.view.item;

import android.view.View;
import android.widget.TextView;

import br.com.iandev.midiaindoor.R;
import br.com.iandev.midiaindoor.dao.ConnectionFactory;
import br.com.iandev.midiaindoor.util.ViewUtil;

/**
 * Created by Lucas on 18/04/2017.
 * Changes:
 * Date        Responsible     Change
 * 18/04/2017  Lucas
 */

public class DatabaseItem extends Item {
    public DatabaseItem(View view) {
        super(view);
        ((TextView) view.findViewById(R.id.name)).setText(ViewUtil.getString(ConnectionFactory.DATABASE_NAME));
        ((TextView) view.findViewById(R.id.version)).setText(ViewUtil.getString(ConnectionFactory.DATABASE_VERSION));

    }
}
