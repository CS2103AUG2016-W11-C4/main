//@@author A0140011L
package teamfour.tasc.testutil;

import java.util.Date;

import teamfour.tasc.commons.util.clock.Clock;

/**
 * Allow us to set fake current time.
 */
public class TestClock implements Clock {
    
    private Date fakeTime;
    
    public TestClock(Date fakeTime) {
        setFakeTime(fakeTime);
    }
    
    public void setFakeTime(Date fakeTime) {
        this.fakeTime = fakeTime;
    }

    @Override
    public Date getCurrentTime() {
        return fakeTime;
    }

}
