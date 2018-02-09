package br.com.iandev.midiaindoor.wrap;

import br.com.iandev.midiaindoor.App;


/**
 * Created by Lucas on 29/03/2017.
 * Changes:
 * Date        Responsible     Change
 * 29/03/2017  Lucas
 */

public abstract class Wrapper {
    private final App app;

    protected Wrapper(App app) {
        this.app = app;
    }

    protected App getApp() {
        return this.app;
    }
//
//    private DeviceFacede doAuthentication() {
//        try {
//            final OutputStream bous = new ByteArrayOutputStream();
//
//            DeviceFacede device = new DeviceFacede(null).parse(bous.format());
//
//            DeviceDao deviceDAO = new DeviceDao(getContext());
//
//            DeviceFacede deviceDB = deviceDAO.read(device);
//
//            device.setLastUpdateDate(deviceDB.getLastUpdateDate());
//            device.setLastUpdateDate(deviceDB.getLastUpdateDate());
//
//            if (1L == deviceDAO.update(device)) {
//                App.getInstance().setDevice(device);
//            } else {
//                throw new AuthenticatorException();
//            }
//            app.getLogger().put(R.string.event_authentication_attempting, R.string.message_success);
//            return device;
//        } catch (AuthenticatorException ex) {
////            ex.printStackTrace();
//            this.requestErrorOccurred();
//            app.getLogger().put(R.string.event_authentication_attempting, R.string.message_error);
//            throw new AuthenticatorException(ex.getMessage());
//        }
//    }


}
