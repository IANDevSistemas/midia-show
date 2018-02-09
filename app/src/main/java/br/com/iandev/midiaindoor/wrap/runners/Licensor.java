package br.com.iandev.midiaindoor.wrap.runners;

import org.json.JSONObject;

import br.com.iandev.midiaindoor.App;
import br.com.iandev.midiaindoor.core.TimeCounter;
import br.com.iandev.midiaindoor.model.Device;
import br.com.iandev.midiaindoor.model.Person;
import br.com.iandev.midiaindoor.util.JSONUtil;
import br.com.iandev.midiaindoor.wrap.facede.DeviceFacede;
import br.com.iandev.midiaindoor.wrap.facede.PersonFacede;
import br.com.iandev.midiaindoor.wrap.settings.LicenseSettings;

/**
 * Created by Lucas on 29/03/2017.
 * Changes:
 * Date        Responsible     Change
 * 29/03/2017  Lucas
 */

public class Licensor extends IntegrationRunnable<LicenseSettings> {
    public Licensor(App app) {
        super(app);
    }

    public Licensor(App app, LicenseSettings licenseSettings) {
        super(app, licenseSettings);
    }

    @Override
    public final void start() {
        try {
            super.setUp();
            App app = super.getApp();

            br.com.iandev.midiaindoor.core.integration.bdo.Licensor licensor = new br.com.iandev.midiaindoor.core.integration.bdo.Licensor();

            JSONObject jsonObject = licensor.doLicensing();

            app.setTimeCounter(new TimeCounter(JSONUtil.getDate(jsonObject, "date")));

            Person person = new Person(null).parse(jsonObject.getJSONObject("person"));
            Device device = new Device(null).parse(jsonObject.getJSONObject("device"));

            getLicenseSettings().setOwnerID(person.getId().toString());
            getLicenseSettings().setDeviceId(device.getId().toString());

            PersonFacede personController = new PersonFacede(app);
            DeviceFacede deviceController = new DeviceFacede(app);

            personController.set(person);
            deviceController.set(device);

            super.success(getLicenseSettings());
        } catch (Exception ex) {
            super.error(ex);
        }
    }

    public String getTag() {
        return "Licensor";
    }
}
