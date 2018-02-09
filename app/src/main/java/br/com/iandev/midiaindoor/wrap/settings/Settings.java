package br.com.iandev.midiaindoor.wrap.settings;

import android.content.Context;
import android.content.SharedPreferences;

import br.com.iandev.midiaindoor.App;
import br.com.iandev.midiaindoor.wrap.Wrapper;

/**
 * Created by Lucas on 21/12/2016.
 */
public class Settings extends Wrapper {
    private static final String TAG = Settings.class.getSimpleName();

    private final SharedPreferences sharedPreferences;
    private final Context context;

    protected Settings(App app, String tag) {
        super(app);
        this.context = super.getApp().getContext();
        sharedPreferences = context.getSharedPreferences(tag, 0);
    }

    protected String getValue(String key) {
        return getValue(key, "");
    }

    protected String getValue(String key, String defaultValue) {
        return sharedPreferences.getString(key, defaultValue);
    }

    Settings setValue(String key, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if ("".equals(value)) {
            editor.remove(key);
        } else {
            editor.putString(key, value);
        }

        editor.commit();
        return this;
    }

}
