package br.com.iandev.midiaindoor.core.interfaces;

import java.util.Date;

/**
 * Created by Lucas on 07/01/2017.
 */

public interface Updateble {
    Long getId();

    Date getLastUpdateDate();

    void setLastUpdateDate(Date lastUpdateDate);

    Date getLastUpdateAttemptDate();

    void setLastUpdateAttemptDate(Date lastUpdateAttemptDate);

    Date getNextUpdateDate() throws UpdatebleException;

    Long getUpdateInterval();

    void setUpdateInterval(Long updateInterval);

    Long getUpdateToleranceInterval();

    void setUpdateToleranceInterval(Long updateToleranceinterval);

    Character getStatus();

    /**
     * @param date Date to compare with its lastUpdateDate and updateInterval.
     *             If null just test if it was updated once
     * @return if it must update
     */
    boolean mustUpdate(Date date);

    /**
     * @param date Date to compare with its lastUpdateDate and updateInterval.
     *             If null just test if it was updated once
     * @return if it must update
     */
    boolean outOfDate(Date date);
}
