package br.com.iandev.midiaindoor.view;

import org.json.JSONException;
import org.json.JSONObject;

import br.com.iandev.midiaindoor.App;
import br.com.iandev.midiaindoor.core.TimeCounter;
import br.com.iandev.midiaindoor.wrap.facede.DeviceFacede;
import br.com.iandev.midiaindoor.wrap.facede.NotFoundException;

/**
 * Created by Lucas on 06/04/2017.
 * Changes:
 * Date        Responsible     Change
 * 06/04/2017  Lucas
 */

public class PageArgs extends JSONObject {
    public PageArgs() {
        App app = App.getInstance();
        if (app != null) {
            TimeCounter timeCounter = app.getTimeCounter();
            try {
                this.put("date", timeCounter.getTime());
            } catch (JSONException ex) {
                // Silence is golden
            }

            try {
                this.put("timeZone", new DeviceFacede(app).get().getTimeZone().getRawOffset());
            } catch (JSONException ex) {
                // Silence is golden
            } catch (NotFoundException ex) {
                // Silence is golden
            }
        }
    }
}
