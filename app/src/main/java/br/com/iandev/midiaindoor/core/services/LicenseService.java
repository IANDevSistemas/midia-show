package br.com.iandev.midiaindoor.core.services;

import br.com.iandev.midiaindoor.util.IntervalUtil;
import br.com.iandev.midiaindoor.wrap.runners.Licensor;
import br.com.iandev.midiaindoor.wrap.runners.RunnableWrapper;
import br.com.iandev.midiaindoor.wrap.settings.LicenseSettings;

/**
 * Created by Lucas on 18/12/2016.
 */

public class LicenseService extends TimerService {
    private static long SHORT_TIMEOUT = IntervalUtil.parse("00:01:00");
    private static long LONG_TIMEOUT = IntervalUtil.parse("24:00:00");

    private long delay = SHORT_TIMEOUT;

    public LicenseService() {
        super("LicenseService");
    }

    @Override
    protected long getDelay() {
        return delay;
    }

    @Override
    protected RunnableWrapper getRunnable(final Propagation propagation) {
        return new Licensor(LicenseService.super.getApp()) {
            @Override
            public void onSuccess(LicenseSettings response) {
                delay = LONG_TIMEOUT;
            }

            @Override
            public void onError(Exception response) {
                delay = SHORT_TIMEOUT;
            }

            @Override
            public void onComplete() {
//                schedule(propagation, this);
            }
        };
    }
}
