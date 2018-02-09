package br.com.iandev.midiaindoor.wrap.runners;

import br.com.iandev.midiaindoor.App;

/**
 * Created by Lucas on 29/03/2017.
 * Changes:
 * Date        Responsible     Change
 * 29/03/2017  Lucas
 */

public class Starter extends RunnableWrapper<App> {
    public Starter(App app) {
        super(app);
    }

    @Override
    public final void start() {
        try {
            App app = super.getApp();
            app.start();
            super.success(app);
        } catch (Exception ex) {
            super.error(ex);
        }
    }

    public String getTag() {
        return "Starter";
    }
}
