//@@author A0148096W

package guitests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import teamfour.tasc.logic.commands.CalendarCommand;
import teamfour.tasc.ui.CalendarPanel;

public class CalendarCommandTest extends TaskListGuiTest {

    /* 
     * Equivalence Partitions:
     * calendar day
     *     - Week changed to day view
     *     - Already in day view
     * calendar week
     *     - Day changed to week view
     *     - Already in week view
     * calendar today
     *     - Calendar shows current time and success message
     * calendar (all other arguments)
     * calendar (empty string) = no arguments
     *     - Wrong command format message
     */
    
    @Test
    public void calendar_day_successWeekChangedToDayView(){
        String calendarView = "day";
        commandBox.runCommand("calendar week");
        assertCalendarResult("calendar " + calendarView, 
                String.format(CalendarCommand.MESSAGE_SUCCESS, calendarView), calendarView);
    }
    
    @Test
    public void calendar_day_failureAlreadyInView(){
        String calendarView = "day";
        commandBox.runCommand("calendar day");
        assertCalendarResult("calendar " + calendarView, 
                String.format(CalendarCommand.MESSAGE_FAILURE_ALREADY_IN_VIEW, calendarView), calendarView);
    }
    
    @Test
    public void calendar_week_successDayChangedToWeekView(){
        String calendarView = "week";
        commandBox.runCommand("calendar day");
        assertCalendarResult("calendar " + calendarView, 
                String.format(CalendarCommand.MESSAGE_SUCCESS, calendarView), calendarView);
    }
    
    @Test
    public void calendar_today_messageSuccess(){
        commandBox.runCommand("calendar week");
        assertCalendarResult("calendar today", 
                CalendarCommand.MESSAGE_SUCCESS_SELECTED_TODAY, "week");
        
        commandBox.runCommand("calendar day");
        assertCalendarResult("calendar today", 
                CalendarCommand.MESSAGE_SUCCESS_SELECTED_TODAY, "day");
    }
    
    @Test
    public void calendar_randomArguments_failureInvalidCommandFormat(){
        String calendarView = "random";
        commandBox.runCommand("calendar week");
        assertCalendarResult("calendar " + calendarView, 
                String.format("Invalid command format! \n" + CalendarCommand.MESSAGE_USAGE), "week");
    }
    
    @Test
    public void calendar_emptyStringArgument_failureInvalidCommandFormat(){
        String calendarView = "";
        commandBox.runCommand("calendar week");
        assertCalendarResult("calendar " + calendarView, 
                String.format("Invalid command format! \n" + CalendarCommand.MESSAGE_USAGE), "week");
    }
    
    private void assertCalendarResult(String command, String expectedResultMessage, 
                                        String calendarView) {
        commandBox.runCommand(command);
        assertResultMessage(expectedResultMessage);
        assertEquals(CalendarPanel.getCalendarView(), calendarView);
    }
    
}
