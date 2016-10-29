//@@author A0140011L
package teamfour.tasc.commons.util;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

import teamfour.tasc.testutil.TestClock;

public class DateUtilTest {

    @Test
    public void setClock_setFakeClock_returnsFakeTime() {
        Date fakeTime = new Date(0);
        TestClock fakeClock = new TestClock(fakeTime);
        
        DateUtil.getInstance().setClock(fakeClock);        
        assertEquals(fakeTime, DateUtil.getInstance().getCurrentTime());
    }

}
