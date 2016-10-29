//@@author A0140011L
package teamfour.tasc.commons.util;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
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

    @Test
    public void clampDateTimeWithMaxAllowedHour_timeNotExceed_returnsSameTime() {
        LocalDateTime notExceed = LocalDateTime.of(2016, 1, 1, 22, 59);
        
        assertEquals(notExceed, DateUtil.clampDateTimeWithMaxAllowedHour(notExceed, 23));
    }
    
    @Test
    public void clampDateTimeWithMaxAllowedHour_timeExceed_returnsClampedTime() {
        LocalDateTime exceed = LocalDateTime.of(2016, 1, 1, 23, 01);
        LocalDateTime expectedClamp = LocalDateTime.of(2016, 1, 1, 23, 00);
        
        assertEquals(expectedClamp, DateUtil.clampDateTimeWithMaxAllowedHour(exceed, 23));
    }
}
