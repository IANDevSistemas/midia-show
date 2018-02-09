package br.com.iandev.midiaindoor.core.services;

import android.app.IntentService;

import br.com.iandev.midiaindoor.App;

/**
 * Created by Lucas on 31/03/2017.
 * Changes:
 * Date        Responsible     Change
 * 31/03/2017  Lucas
 */

public abstract class Service extends IntentService {
    private final App app;

    public Service(String name) {
        super(name);
        app = App.getInstance();
    }

    protected App getApp() {
        return app;
    }
}
