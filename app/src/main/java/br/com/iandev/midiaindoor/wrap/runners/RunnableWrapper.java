package br.com.iandev.midiaindoor.wrap.runners;

import org.apache.log4j.Logger;

import br.com.iandev.midiaindoor.App;
import br.com.iandev.midiaindoor.wrap.Wrapper;

/**
 * Created by Lucas on 30/03/2017.
 * Changes:
 * Date        Responsible     Change
 * 30/03/2017  Lucas
 */

public abstract class RunnableWrapper<T> extends Wrapper implements Runnable {
    private static final Logger logger = br.com.iandev.midiaindoor.wrap.log.Logger.getLogger(RunnableWrapper.class);

    protected RunnableWrapper(App app) {
        super(app);
    }

    protected abstract void start();

    public abstract String getTag();

    @Override
    public final void run() {
        try {
            logger.info(String.format("%s: run()", this.getTag()));
        } catch (Exception ex) {
            //Silence is golden
        }
        start();
    }

    protected final void success(T response) {
        try {
            logger.info(String.format("%s: success(%s)", this.getTag(), response.toString()));
        } catch (Exception ex) {
            //Silence is golden
        }
        try {
            onSuccess(response);
        } catch (Exception ex) {
            error(ex);
        } finally {
            onComplete();
        }
    }

    protected void error(Exception response) {
        try {
            logger.error(String.format("%s: error(%s)", this.getTag(), response.toString()), response);
        } catch (Exception ex) {
            //Silence is golden
        }
        try {
            onError(response);
        } catch (Exception ex) {
            //Silence is golden
        }
        onComplete();
    }

    public void onSuccess(T response) {

    }

    public void onError(Exception response) {

    }

    public void onComplete() {

    }

}
