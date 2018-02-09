package br.com.iandev.midiaindoor.view.activity;

import android.os.Bundle;

import br.com.iandev.midiaindoor.App;
import br.com.iandev.midiaindoor.R;
import br.com.iandev.midiaindoor.view.TitleBar;
import br.com.iandev.midiaindoor.view.item.AppItem;
import br.com.iandev.midiaindoor.view.item.DatabaseItem;
import br.com.iandev.midiaindoor.view.item.FilesItem;

/**
 * Created by Lucas on 18/04/2017.
 * Changes:
 * Date        Responsible     Change
 * 18/04/2017  Lucas
 */

public class InfoActivity extends AutoRefreshActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        new TitleBar(this, findViewById(R.id.title_bar), getResources().getString(R.string.title_info));

        super.autoRefresh();
    }

    @Override
    protected void refresh() {
        new AppItem(findViewById(R.id.app), App.getInstance());
        new FilesItem(findViewById(R.id.files));
        new DatabaseItem(findViewById(R.id.database));
    }
}