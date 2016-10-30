//@@author A0140011L
package teamfour.tasc.commons.util.clock;

import java.util.Date;

/**
 * Represents the current time on the computer.
 */
public class SystemClock implements Clock {

    /**
     * Gets the current time as determined by the system.
     */
    @Override
    public Date getCurrentTime() {

        /*
         * For those that are wondering why I didn't test
         * this method:
         * 
         * There is just no way to test such a 
         * time-sensitive method.
         * 
         * Do remember that DateUtil.setClock() allows 
         * replacement with fake clocks, to replace
         * SystemClock and fake the current timing 
         * for testing purposes.
         */    
        
        return new Date();
    }
}
