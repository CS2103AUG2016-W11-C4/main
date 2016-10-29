//@@author A0140011L
package teamfour.tasc.commons.util.clock;

import java.util.Date;

/**
 * Represents the time on the computer
 */
public class SystemClock implements Clock {

    @Override
    public Date getCurrentTime() {
        return new Date();
    }
}
