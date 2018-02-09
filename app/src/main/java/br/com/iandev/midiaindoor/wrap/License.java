package br.com.iandev.midiaindoor.wrap;

import br.com.iandev.midiaindoor.App;
import br.com.iandev.midiaindoor.model.Device;
import br.com.iandev.midiaindoor.wrap.facede.DeviceFacede;
import br.com.iandev.midiaindoor.wrap.settings.LicenseSettings;

/**
 * Created by Lucas on 03/04/2017.
 * Changes:
 * Date        Responsible     Change
 * 03/04/2017  Lucas
 */

public class License extends Wrapper {
    private final LicenseSettings licenseSettings;

    public License(App app) {
        super(app);
        this.licenseSettings = new LicenseSettings(super.getApp());
    }

    public License(App app, LicenseSettings licenseSettings) {
        super(app);
        this.licenseSettings = licenseSettings;
    }

    public boolean isLicenceValid() {
        boolean isValid = false;
        try {
            Device device = new DeviceFacede(super.getApp()).get();
            isValid = device.getId().equals(Long.valueOf(getLicenseSettings().getDeviceId()));
        } catch (Exception ex) {
            //Silence is golden
        }
        return isValid;
    }

    private LicenseSettings getLicenseSettings() {
        return licenseSettings;
    }
}
