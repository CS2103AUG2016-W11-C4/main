//@@author A0140011L
package teamfour.tasc.commons.util.clock;

import java.util.Date;

/**
 * Represents a clock that can tell us the current time.
 */
public interface Clock {
    /**
     * Get the current time provided by the clock.
     */
    Date getCurrentTime();
}
