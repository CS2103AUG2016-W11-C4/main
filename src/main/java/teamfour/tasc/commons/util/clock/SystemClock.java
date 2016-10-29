//@@author A0140011L
package teamfour.tasc.commons.util.clock;

import java.util.Date;

/**
 * Represents the time on the computer
 */
public class SystemClock implements Clock {

    /*
     * Do remember that DateUtil.setClock() allows 
     * replacement with fake clocks, to replace
     * SystemClock and fake the current timing 
     * for testing purposes.
     * 
     * There is just no way to test such a 
     * time-sensitive method.
     */    
    @Override
    public Date getCurrentTime() {
        return new Date();
    }
}
