package br.com.iandev.midiaindoor.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import br.com.iandev.midiaindoor.App;
import br.com.iandev.midiaindoor.R;
import br.com.iandev.midiaindoor.model.Person;
import br.com.iandev.midiaindoor.util.ViewUtil;
import br.com.iandev.midiaindoor.view.TitleBar;
import br.com.iandev.midiaindoor.wrap.facede.NotFoundException;
import br.com.iandev.midiaindoor.wrap.facede.PersonFacede;

import static br.com.iandev.midiaindoor.R.id.person;

/**
 * Created by Lucas on 19/03/2017.
 * Changes:
 * Date        Responsible     Change
 * 19/03/2017  Lucas
 */

public class PersonActivity extends AutoRefreshActivity {
    private final PersonFacede personController = new PersonFacede(App.getInstance());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);

        new TitleBar(this, findViewById(R.id.title_bar), getResources().getString(R.string.title_person));

        super.autoRefresh();
    }

    @Override
    protected final void refresh() {
        final View view = findViewById(person);

        Person person;
        try {
            person = personController.get();
        } catch (NotFoundException e) {
            person = new Person(null);
        }

        ((TextView) view.findViewById(R.id.id)).setText(ViewUtil.getString(person.getId()));
        ((TextView) view.findViewById(R.id.name)).setText(ViewUtil.getString(person.getName()));
        ((TextView) view.findViewById(R.id.alias)).setText(ViewUtil.getString(person.getAlias()));
        ((TextView) view.findViewById(R.id.status)).setText(ViewUtil.getString(person.getStatus()));
    }
}
