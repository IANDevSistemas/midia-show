package br.com.iandev.midiaindoor.wrap.facede;

import java.util.List;

import br.com.iandev.midiaindoor.App;
import br.com.iandev.midiaindoor.dao.DeviceDao;
import br.com.iandev.midiaindoor.model.Device;
import br.com.iandev.midiaindoor.wrap.settings.LicenseSettings;

/**
 * Created by Lucas on 30/03/2017.
 * Changes:
 * Date        Responsible     Change
 * 30/03/2017  Lucas
 */

public final class DeviceFacede extends Facede<Device> {
    private static List<Device> cache;

    public DeviceFacede(App app) {
        super(app, new DeviceDao(app.getContext()));
    }

    @Override
    protected List<Device> getCache() {
        return cache;
    }

    @Override
    protected void setCache(List<Device> cache) {
        DeviceFacede.cache = cache;
    }

    @Override
    public Device get() throws NotFoundException {
        Device device;
        try {
            LicenseSettings licenseSettings = new LicenseSettings(super.getApp());
            Long deviceId = Long.valueOf(licenseSettings.getDeviceId());

            device = super.get(new Device(deviceId));
            if (!deviceId.equals(device.getId())) {
                throw new NotFoundException("It wasn't found");
            }
        } catch (Exception ex) {
            throw new NotFoundException(ex.getMessage());
        }
        return device;
    }

    @Override
    public Device get(Device entity) throws NotFoundException {
        throw new NotFoundException("It wasn't found");
    }

    @Override
    public List<Device> list() throws NotFoundException {
        throw new NotFoundException("It wasn't found");
    }

    @Override
    public long set(Device entity) throws IllegalStateException {
        try {
            LicenseSettings licenseSettings = new LicenseSettings(super.getApp());
            Long deviceId = Long.valueOf(licenseSettings.getDeviceId());

            if (!deviceId.equals(entity.getId())) {
                throw new IllegalStateException("Its id isn't valid");
            }

            entity.setCode(licenseSettings.getCode());

            try {
                Device device = this.get();
                if (entity.getLastUpdateDate() == null) {
                    entity.setLastUpdateDate(device.getLastUpdateDate());
                }

                if (entity.getLastUpdateAttemptDate() == null) {
                    entity.setLastUpdateAttemptDate(device.getLastUpdateAttemptDate());
                }

                if (entity.getLastAuthenticationDate() == null) {
                    entity.setLastAuthenticationDate(device.getLastAuthenticationDate());
                }
            } catch (NotFoundException ex) {
                // Silence is golden
            }

            super.delete();
            super.set(entity);
            entity = this.get();

        } catch (Exception ex) {
            throw new IllegalStateException(ex.getMessage());
        }

        return entity.getId();
    }

    @Override
    public void set(List<Device> entity) throws IllegalStateException {
        throw new IllegalStateException("Operation isn't valid");
    }
}
