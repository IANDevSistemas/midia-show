package br.com.iandev.midiaindoor.core.services;

import br.com.iandev.midiaindoor.util.IntervalUtil;
import br.com.iandev.midiaindoor.wrap.runners.Manager;
import br.com.iandev.midiaindoor.wrap.runners.RunnableWrapper;

/**
 * Created by Lucas on 14/12/2016.
 * Changes:
 * Date        Responsible     Change
 * 14/12/2016  Lucas
 */

public class ManagementService extends TimerService {
    private long delay = IntervalUtil.parse("00:01:00");

    public ManagementService() {
        super("ManagementService");
    }

    @Override
    protected RunnableWrapper getRunnable(final Propagation propagation) {
        return new Manager(ManagementService.super.getApp()) {
            @Override
            public void onComplete() {
                schedule(propagation, this);
            }
        };
    }

    @Override
    protected long getDelay() {
        return delay;
    }
}
