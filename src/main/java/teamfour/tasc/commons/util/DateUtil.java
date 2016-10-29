//@@author A0140011L
package teamfour.tasc.commons.util;

import java.util.Date;

import teamfour.tasc.commons.util.clock.Clock;
import teamfour.tasc.commons.util.clock.SystemClock;

/**
 * Operations related to dates.
 */
public class DateUtil {    
    private static DateUtil singleton;
    
    private Clock clock;
    
    private DateUtil() {
        this.clock = new SystemClock();
    }
    
    /**
     * Get the singleton instance of DateUtil.
     */
    public static DateUtil getInstance() {
        if (singleton == null) {
            singleton = new DateUtil();
        }
        
        return singleton;
    }
    
    public void setClock(Clock newClock) {
        this.clock = newClock;
    }
    
    /**
     * Get the current time.
     */
    public Date getCurrentTime() {
        return clock.getCurrentTime();
    }
}
