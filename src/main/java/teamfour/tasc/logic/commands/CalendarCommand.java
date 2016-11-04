//@@author A0148096W

package teamfour.tasc.logic.commands;

import java.util.Date;

import teamfour.tasc.commons.core.EventsCenter;
import teamfour.tasc.commons.events.ui.ChangeCalendarViewRequestEvent;
import teamfour.tasc.commons.events.ui.JumpToCalendarDateRequestEvent;
import teamfour.tasc.commons.exceptions.IllegalValueException;
import teamfour.tasc.logic.keyword.CalendarCommandKeyword;
import teamfour.tasc.logic.keyword.DayKeyword;
import teamfour.tasc.logic.keyword.TodayKeyword;
import teamfour.tasc.logic.keyword.WeekKeyword;
import teamfour.tasc.ui.CalendarPanel;

/**
 * Changes the calendar view.
 */
public class CalendarCommand extends Command {

    public static final String COMMAND_WORD = CalendarCommandKeyword.keyword;

    public static final String KEYWORD_CALENDAR_VIEW_DAY = DayKeyword.keyword;
    public static final String KEYWORD_CALENDAR_VIEW_WEEK = WeekKeyword.keyword;
    public static final String KEYWORD_CALENDAR_VIEW_TODAY = TodayKeyword.keyword;
    
    public static final String MESSAGE_USAGE = COMMAND_WORD 
            + ": Changes the calendar view. "
            + "Parameters: day|week|today\n"
            + "Example: " + COMMAND_WORD + " day";

    public static final String MESSAGE_SUCCESS = 
            "Calendar changed to %1$s view.";
    public static final String MESSAGE_SUCCESS_SELECTED_TODAY = 
            "Calendar is now showing the current time.";
    public static final String MESSAGE_FAILURE_ALREADY_IN_VIEW = 
            "Calendar is already in %1$s view.";

    private final String calendarView;
    private final boolean isViewingToday;
    
    /**
     * Calendar Command
     * Convenience constructor using raw values.
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public CalendarCommand(String calendarView) throws IllegalValueException {
        if (!isCalendarViewValid(calendarView)) {
            throw new IllegalValueException("Invalid calendar view type.");
        }
        this.isViewingToday = calendarView.equals(KEYWORD_CALENDAR_VIEW_TODAY);
        this.calendarView = calendarView;
    }
    
    /**
     * Validates the calendar view String in the argument.
     */
    private boolean isCalendarViewValid(String calendarView) {
        if (calendarView == null) {
            return false;
        }
        return calendarView.equals(KEYWORD_CALENDAR_VIEW_DAY) ||
                calendarView.equals(KEYWORD_CALENDAR_VIEW_WEEK) ||
                calendarView.equals(KEYWORD_CALENDAR_VIEW_TODAY);
    }

    @Override
    public CommandResult execute() {
        if (isViewingToday) {
            EventsCenter.getInstance().post(new JumpToCalendarDateRequestEvent(new Date()));
            return new CommandResult(MESSAGE_SUCCESS_SELECTED_TODAY);
        }
        
        if (calendarView.equals(CalendarPanel.getCalendarView())) {
            return new CommandResult(String.format(MESSAGE_FAILURE_ALREADY_IN_VIEW, calendarView));
        }
        
        EventsCenter.getInstance().post(new ChangeCalendarViewRequestEvent(calendarView));
        return new CommandResult(String.format(MESSAGE_SUCCESS, calendarView));
    }

    @Override
    public boolean canUndo() {
        return false;
    }
}
