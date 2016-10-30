//@@author A0140011L
package teamfour.tasc.model.task;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Categories.ExcludeCategory;

import teamfour.tasc.commons.exceptions.IllegalValueException;
import teamfour.tasc.model.task.Period;

public class PeriodTest {

    private Date validStartTime;
    private Date validEndTime;
    
    @Before
    public void setUp() {
        validStartTime = new Date(5);
        validEndTime = new Date(10);
    }

    @Test
    public void defaultConstructor_noInput_returnsEmptyPeriod() {
        Period noPeriod = new Period();
        
        assertFalse(noPeriod.hasPeriod());
        assertNull(noPeriod.getStartTime());
        assertNull(noPeriod.getEndTime());
    }

    @Test
    public void constructor_validStartEndTime_returnsCorrectPeriod() throws IllegalValueException {
        Period validPeriod = new Period(validStartTime, validEndTime);
        
        assertTrue(validPeriod.hasPeriod());
        assertEquals(validStartTime, validPeriod.getStartTime());
        assertEquals(validEndTime, validPeriod.getEndTime());
    }
    
    @Test (expected = IllegalValueException.class)
    public void constructor_startTimeLaterThanEndTime_throwsIllegalValueException() throws IllegalValueException {
        Date earlierTime = new Date(5);
        Date laterTime = new Date(10);
        
        Period invalidPeriod = new Period(laterTime, earlierTime);
    }
    
    @Test
    public void toString_noPeriod_returnsNoPeriod() {
        Period noPeriod = new Period();
        assertEquals(Period.TO_STRING_NO_PERIOD, noPeriod.toString());
    }
    
    @Test
    public void toString_validPeriod_returnsValidTime() throws IllegalValueException {
        Period validPeriod = new Period(validStartTime, validEndTime);
        
        String expectedOutput = validStartTime.toString() + " - " + validEndTime.toString();
        
        assertEquals(expectedOutput, validPeriod.toString());
    }
    
    @Test
    public void equals_nonPeriodObject_returnFalse() {
        Period noPeriod = new Period();
        
        assertFalse(noPeriod.equals("String"));
    }
}
