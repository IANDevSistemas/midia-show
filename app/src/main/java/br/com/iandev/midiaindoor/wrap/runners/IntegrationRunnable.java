package br.com.iandev.midiaindoor.wrap.runners;

import br.com.iandev.midiaindoor.App;
import br.com.iandev.midiaindoor.core.integration.bdo.BDOIntegrator;
import br.com.iandev.midiaindoor.wrap.settings.LicenseSettings;

/**
 * Created by Lucas on 04/04/2017.
 * Changes:
 * Date        Responsible     Change
 * 04/04/2017  Lucas
 */

public abstract class IntegrationRunnable<T> extends RunnableWrapper<T> {

    private LicenseSettings licenseSettings;

    public IntegrationRunnable(App app) {
        super(app);
    }

    public IntegrationRunnable(App app, LicenseSettings licenseSettings) {
        super(app);
        this.licenseSettings = licenseSettings;
    }

    protected void setUp() {
        BDOIntegrator.setIdDispositivo(getLicenseSettings().getDeviceId());
        BDOIntegrator.setDispositivo(getLicenseSettings().getDeviceDescription());
        BDOIntegrator.setCodigo(getLicenseSettings().getCode());
        BDOIntegrator.setIdPessoa(getLicenseSettings().getOwnerId());

        BDOIntegrator.setSiteFQDN(getLicenseSettings().getURL());
        BDOIntegrator.setSenha(getLicenseSettings().getPassword());
        BDOIntegrator.setVersao(getLicenseSettings().getVersion());
    }

    @Override
    public void error(Exception ex) {
        try {
            BDOIntegrator.error();
        } catch (Throwable ex2) {
            // Silence is golden
        }
        super.error(ex);
    }

    protected LicenseSettings getLicenseSettings() {
        if (licenseSettings == null) {
            licenseSettings = new LicenseSettings(super.getApp());
        }
        return licenseSettings;
    }
}
