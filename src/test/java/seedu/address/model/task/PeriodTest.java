package seedu.address.model.task;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;
import org.junit.experimental.categories.Categories.ExcludeCategory;

import seedu.address.commons.exceptions.IllegalValueException;

public class PeriodTest {

    @Test
    public void defaultConstructor_noInput_returnsEmptyPeriod() {
        Period noPeriod = new Period();
        
        assertFalse(noPeriod.hasPeriod);
        assertNull(noPeriod.startTime);
        assertNull(noPeriod.endTime);
    }

    @Test
    public void constructor_validStartEndTime_returnsCorrectPeriod() throws IllegalValueException {
        Date startTime = new Date(5);
        Date endTime = new Date(10);
        
        Period period = new Period(startTime, endTime);
        
        assertTrue(period.hasPeriod);
        assertEquals(startTime, period.startTime);
        assertEquals(endTime, period.endTime);
    }
    
    @Test (expected = IllegalValueException.class)
    public void constructor_startTimeLaterThanEndTime_throwsIllegalValueException() throws IllegalValueException {
        Date earlierTime = new Date(5);
        Date laterTime = new Date(10);
        
        Period period = new Period(laterTime, earlierTime);
    }
    
    @Test
    public void toString_noPeriod_returnsNoPeriod() {
        Period noPeriod = new Period();
        assertEquals(Period.TO_STRING_NO_PERIOD, noPeriod.toString());
    }
    
    @Test
    public void toString_validPeriod_returnsValidTime() throws IllegalValueException {
        Date earlierTime = new Date(0);
        Date laterTime = new Date(10);
        Period period = new Period(earlierTime, laterTime);
        
        String expectedOutput = earlierTime.toString() + " - " + laterTime.toString();
        
        assertEquals(expectedOutput, period.toString());
    }
    
    @Test
    public void equals_nonPeriodObject_returnFalse() {
        Period noPeriod = new Period();
        
        assertFalse(noPeriod.equals("String"));
    }
}
