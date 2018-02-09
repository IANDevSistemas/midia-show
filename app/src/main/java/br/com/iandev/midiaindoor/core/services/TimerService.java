package br.com.iandev.midiaindoor.core.services;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;

import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

import br.com.iandev.midiaindoor.wrap.runners.RunnableWrapper;

/**
 * Created by Lucas on 20/12/2016.
 */

public abstract class TimerService extends Service {
    private static final Map<String, Propagation> propagationMap = new HashMap<>();
    private static final Logger logger = br.com.iandev.midiaindoor.wrap.log.Logger.getLogger(TimerService.class);
    private final String name;
    private Handler handler;

    public TimerService(String name) {
        super(name);
        this.name = name;
    }

    public static boolean mustWakeUp() {
        return true;
    }

    private synchronized Handler getHandler() {
        if (handler == null) {
            handler = new Handler(Looper.getMainLooper());
        }
        return handler;
    }

    protected synchronized final void schedule(final Propagation propagation, final RunnableWrapper runnableWrapper) {
        try {
            logger.info(String.format("%s: schedule(%s, %s)", runnableWrapper.getTag(), propagation.toString(), runnableWrapper.toString()));
            getHandler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (propagation.isPropagation()) {
                        new Thread(runnableWrapper).start();
                    }
                }
            }, getDelay());
        } catch (Exception ex) {
            // Silence is golden
        }
    }

    private synchronized void stopPropagation() {
        getPropagation().setPropagation(false);
        propagationMap.remove(name);
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }

    protected Context getContext() {
        return getApplicationContext();
    }

    @Override
    protected synchronized final void onHandleIntent(Intent intent) {
        stopPropagation();
        RunnableWrapper runnableWrapper = getRunnable(getPropagation());
        logger.info(String.format("%s: onHandleIntent(%s)", runnableWrapper.getTag(), intent.toString()));
        new Thread(runnableWrapper).start();
//        getRunnable().start();
//        getHandler().post(getRunnable());
//        getRunnable().start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private synchronized Propagation getPropagation() {
        if (!propagationMap.containsKey(name)) {
            propagationMap.put(name, new Propagation());
        }
        return propagationMap.get(name);
    }

    protected abstract RunnableWrapper getRunnable(final Propagation propagation);

    protected abstract long getDelay();

    protected class Propagation {
        private boolean propagation = true;

        public boolean isPropagation() {
            return propagation;
        }

        public void setPropagation(boolean propagation) {
            this.propagation = propagation;
        }

        @Override
        public String toString() {
            return String.format("Propagation[%s]", propagation);
        }
    }

}
