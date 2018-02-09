package br.com.iandev.midiaindoor.core.services;

import android.content.Intent;

import br.com.iandev.midiaindoor.core.interfaces.Playable;
import br.com.iandev.midiaindoor.util.IntervalUtil;
import br.com.iandev.midiaindoor.wrap.runners.Player;
import br.com.iandev.midiaindoor.wrap.runners.RunnableWrapper;

/**
 * Created by Lucas on 13/11/2016.
 * Changes:
 * Date        Responsible     Change
 * 13/11/2016  Lucas
 */

public class PlayerService extends TimerService {
    public static final String MESSAGE_LOAD_URL = PlayerService.class.getCanonicalName() + ".MESSAGE_LOAD_URL";
    private static final long DELAY_DEFAULT = IntervalUtil.parse("00:00:10");
    private long delay = DELAY_DEFAULT;

    public PlayerService() {
        super("PlayerService");
    }

    @Override
    protected long getDelay() {
        return delay;
    }

    @Override
    protected RunnableWrapper getRunnable(final Propagation propagation) {
        return new Player(PlayerService.super.getApp()) {
            @Override
            public void onSuccess(Playable response) {
                delay = response.getDurationInterval();
                Intent playerIntent = new Intent(MESSAGE_LOAD_URL);
                playerIntent.putExtra(MESSAGE_LOAD_URL, response.getURLForPlayer());
                sendBroadcast(playerIntent);
            }

            @Override
            public void onError(Exception response) {
                delay = DELAY_DEFAULT;
                Intent playerIntent = new Intent(MESSAGE_LOAD_URL);
                playerIntent.putExtra(MESSAGE_LOAD_URL, "about:blank");
                sendBroadcast(playerIntent);
            }

            @Override
            public void onComplete() {
                schedule(propagation, this);
            }
        };
    }
}