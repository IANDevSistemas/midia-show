package br.com.iandev.midiaindoor.core.interfaces;

import java.util.Date;

/**
 * Created by Lucas on 18/12/2016.
 */

public interface Playable {
    Date getLastPlaybackDate();

    void setLastPlaybackDate(Date lastPlaybackDate);

    String getURLForPlayer();

    Long getDurationInterval();

    boolean canPlay(Date date);
}
