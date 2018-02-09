package br.com.iandev.midiaindoor.core.services;

import br.com.iandev.midiaindoor.model.Device;
import br.com.iandev.midiaindoor.util.IntervalUtil;
import br.com.iandev.midiaindoor.wrap.runners.RunnableWrapper;
import br.com.iandev.midiaindoor.wrap.runners.Synchronizer;

/**
 * Created by Lucas on 14/12/2016.
 */

public class SyncService extends TimerService {
    private static long SHORT_TIMEOUT = IntervalUtil.parse("00:00:30");

    private long delay = SHORT_TIMEOUT;

    public SyncService() {
        super("SyncService");
    }

    @Override
    protected long getDelay() {
        return delay;
    }

    @Override
    protected RunnableWrapper getRunnable(final Propagation propagation) {
        return new Synchronizer(SyncService.super.getApp()) {
            @Override
            public void onSuccess(Device response) {
                delay = response.getUpdateInterval();
            }

            @Override
            public void onError(Exception response) {
                delay = SHORT_TIMEOUT;
            }

            @Override
            public void onComplete() {
                schedule(propagation, this);
            }
        };
    }
}

