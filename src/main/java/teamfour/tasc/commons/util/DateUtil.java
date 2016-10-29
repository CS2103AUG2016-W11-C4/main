//@@author A0140011L
package teamfour.tasc.commons.util;

import java.time.LocalDateTime;
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
    
    /**
     * Clamp the dateTime so that the latest hour can only be maxAllowedHour.
     */
    public static LocalDateTime clampDateTimeWithMaxAllowedHour(LocalDateTime dateTime,
            int maxAllowedHour) {
        if (dateTime.getHour() >= maxAllowedHour) {
            return dateTime.withHour(maxAllowedHour).withMinute(0);
        }

        return dateTime;
    }
}
