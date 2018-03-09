package br.com.iandev.midiaindoor.wrap.settings;

import java.util.UUID;

import br.com.iandev.midiaindoor.App;

/**
 * Created by Lucas on 29/03/2017.
 * Changes:
 * Date        Responsible     Change
 * 29/03/2017  Lucas
 */

public class LicenseSettings extends Settings {
    private static final String KEY_DEVICE_ID = "deviceId";
    private static final String KEY_DEVICE_CODE = "deviceCode";
    private static final String KEY_DEVICE_DESCRIPTION = "deviceName";
    private static final String KEY_OWNER_ID = "ownerID";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_URL = "url";

    public LicenseSettings(App app) {
        super(app, "LicenseSettings");
    }

    public String getDeviceId() {
        return super.getValue(KEY_DEVICE_ID, "");
    }

    public Settings setDeviceId(String value) {
        return super.setValue(KEY_DEVICE_ID, value);
    }

    public String getDeviceDescription() {
        return super.getValue(KEY_DEVICE_DESCRIPTION, "");
    }

    public Settings setDeviceDescription(String value) {
        return super.setValue(KEY_DEVICE_DESCRIPTION, value);
    }

    public String getOwnerId() {
        return super.getValue(KEY_OWNER_ID, "");
    }

    public Settings setOwnerID(String value) {
        return super.setValue(KEY_OWNER_ID, value);
    }

    public String getPassword() {
        return super.getValue(KEY_PASSWORD, "");
    }

    public Settings setPassword(String value) {
        return super.setValue(KEY_PASSWORD, value);
    }

    public String getURL() {
        String url = getValue(KEY_URL);
        if (url.trim().isEmpty()) {
            url = "http://www.iandev.com.br/projects/midiaindoor/servers.php";
        }
        return url;
    }

    public Settings setURL(String value) {
        return setValue(KEY_URL, value);
    }

    public String getCode() {
        String code = getValue(KEY_DEVICE_CODE);
        if ("".equals(code)) {
            code = UUID.randomUUID().toString();
            setValue(KEY_DEVICE_CODE, code);
            code = getValue(KEY_DEVICE_CODE);
        }
        return code;
    }

    public String getVersion() {
        String version = "";
        try {
            version = super.getApp().getVersionName();
        } catch (Exception ex) {
            // Silence is golden
        }
        return version;
    }
}
