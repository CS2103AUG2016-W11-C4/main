//@@author A0140011L
package teamfour.tasc.model.task;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import teamfour.tasc.model.task.Deadline;

public class DeadlineTest {
    
    private Deadline noDeadline;
    private Date epochDate;
    private Deadline epochDeadline;
    
    @Before
    public void setUp() {
        noDeadline = new Deadline();
        epochDate = new Date(0);
        epochDeadline = new Deadline(epochDate);
    }

    @Test
    public void defaultConstructor_noInput_returnsEmptyDeadline() {        
        assertFalse(noDeadline.hasDeadline());
        assertNull(noDeadline.getDeadline());
    }
    
    @Test
    public void constructor_validDateGiven_returnsDeadlineWithDate() {        
        assertTrue(epochDeadline.hasDeadline());
        assertEquals(epochDate, epochDeadline.getDeadline());
    }
    
    @Test
    public void toString_noDeadline_returnsNoDeadline() {        
        assertEquals(Deadline.TO_STRING_NO_DEADLINE, noDeadline.toString());
    }
    
    @Test
    public void toString_validDeadline_returnsSameDate() {        
        assertEquals(epochDate.toString(), epochDeadline.toString());   
    }
    
    @Test
    public void equals_nonDeadlineObject_returnsFalse() {
        assertFalse(noDeadline.equals("String"));
    }
}
