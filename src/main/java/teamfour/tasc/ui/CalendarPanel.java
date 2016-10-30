//@@author A0140011L
package teamfour.tasc.ui;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import jfxtras.internal.scene.control.skin.agenda.AgendaDaySkin;
import jfxtras.internal.scene.control.skin.agenda.AgendaWeekSkin;
import jfxtras.scene.control.agenda.Agenda;
import jfxtras.scene.control.agenda.Agenda.Appointment;
import teamfour.tasc.commons.core.LogsCenter;
import teamfour.tasc.commons.exceptions.IllegalValueException;
import teamfour.tasc.commons.util.FxViewUtil;
import teamfour.tasc.logic.commands.CalendarCommand;
import teamfour.tasc.model.task.Deadline;
import teamfour.tasc.model.task.Period;
import teamfour.tasc.model.task.ReadOnlyTask;
import teamfour.tasc.model.task.Recurrence;
import teamfour.tasc.ui.calendar.CalendarReadOnlyAppointment;
import teamfour.tasc.ui.calendar.CalendarReadOnlyRecurredAppointment;

/**
 * Panel containing a visual overview of the calendar.
 */
public class CalendarPanel extends UiPart {

    private static Logger logger = LogsCenter.getLogger(CalendarPanel.class);
    private static String currentCalendarView = "";
   
    private Agenda agendaView;
    private ReadOnlyTask lastSelectedTask;

    /**
     * Constructor is kept private as {@link #load(AnchorPane)} is the only way to create a CalendarPanel.
     */
    private CalendarPanel() {
        lastSelectedTask = null;
    }
    
    /**
     * Get current calendar view type.
     */
    public static String getCalendarView() {
        return currentCalendarView;
    }
    
    public static CalendarPanel load(AnchorPane placeholder, List<ReadOnlyTask> initialTaskList) {
        logger.info("Initializing calendar view");
        CalendarPanel calendarPanel = new CalendarPanel();
        calendarPanel.setupAgendaView();
        calendarPanel.refreshTasks(initialTaskList);
        
        FxViewUtil.applyAnchorBoundaryParameters(calendarPanel.agendaView, 0.0, 0.0, 0.0, 0.0);
        placeholder.getChildren().add(calendarPanel.agendaView);
        
        return calendarPanel;
    }

    private void setupAgendaView() {
        agendaView = new Agenda();
        
        // forbid any form of editing
        agendaView.setAllowDragging(false);
        agendaView.setAllowResize(false);        
        agendaView.setEditAppointmentCallback(new Callback<Appointment, Void>() {
            @Override
            public Void call(Appointment appointment) {
                // don't show any editing popups at all
                return null;
            }            
        });
        changeView(CalendarCommand.KEYWORD_CALENDAR_VIEW_WEEK);
    }

    @Override
    public void setNode(Node node) {
        // not applicable
    }

    @Override
    public String getFxmlPath() {
        // not applicable (not using fxml for this panel)
        return null;
    }

    /**
     * Free resources used by the calendar.
     */
    public void freeResources() {
        agendaView = null;
    }
    
    //@@author A0148096W
    /**
     * Precondition: argument is not null.
     * Change the view of the calendar.
     */
    public void changeView(String view) {
        assert view != null;
        
        switch(view) {
        case CalendarCommand.KEYWORD_CALENDAR_VIEW_DAY:
            agendaView.setSkin(new AgendaDaySkin(agendaView));
            currentCalendarView = CalendarCommand.KEYWORD_CALENDAR_VIEW_DAY;
            break;
        case CalendarCommand.KEYWORD_CALENDAR_VIEW_WEEK:
            agendaView.setSkin(new AgendaWeekSkin(agendaView));
            currentCalendarView = CalendarCommand.KEYWORD_CALENDAR_VIEW_WEEK;
            break;
        default:
            logger.warning("Calendar view type is not recognized: " + view);
            break;
        }
        selectLastSelectedTask();
    }
    
    /**
     * Selects and shows the given date on calendar.
     * @param date date to select
     */
    public void selectDate(Date date) {
        LocalDateTime time = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        agendaView.setDisplayedLocalDateTime(time);
    }
    
    //@@author A0140011L
    /** 
     * Refreshes the calendar using the new task list given.
     */
    @Subscribe
    public void refreshTasks(List<ReadOnlyTask> taskList) {
        agendaView.appointments().clear();
        
        int index = 0;
        
        for (ReadOnlyTask task : taskList) {
            index++; 
            
            if (isDisplayableInCalendar(task)) {
                try {
                    agendaView.appointments().addAll(generateAllAppointmentsForTask(task, index));
                } catch (IllegalValueException ive) {
                    logger.warning("Fail to generate calendar UI for task " + index);
                }
            }
        }
    }

    /**
     * Generates all appointments given a task, taking into
     * consideration any possible recurring.
     * 
     * Pre-condition: The task must be displayable in calendar.
     * 
     * @param task to generate the calendar UI appointment object
     * @throws IllegalValueException if task has an invalid task detail
     */
    private List<Appointment> generateAllAppointmentsForTask(ReadOnlyTask task, int index)
            throws IllegalValueException {
        assert isDisplayableInCalendar(task);
                
        if (!task.getRecurrence().hasRecurrence()) {
            
            List<Appointment> results = new ArrayList<Appointment>();            
            results.add(getFirstAppointment(task, index));
            return results;
            
        } else {
            return getRecurringAppointments(task, index);
        }
    }

    /**
     * Gets the first appointment for this task.
     * 
     * If the task is recurring, only the first instance of the
     * recurrence will be returned as an appointment
     * 
     * If the task is non-recurring, the same task will be 
     * returned as an appointment.
     * 
     * Pre-condition: The task must be displayable in calendar.
     * 
     * @param task to generate the calendar UI appointment object
     * @throws IllegalValueException if task has an invalid task detail
     */
    private CalendarReadOnlyAppointment getFirstAppointment(ReadOnlyTask task, int index) {
        return new CalendarReadOnlyAppointment(task, index);
    }
    
    /**
     * Gets all recurring appointments for this task.
     * 
     * Pre-condition: The task must be displayable in calendar, and 
     * the task must have recurrence.
     * 
     * @param task to generate the calendar UI appointment object
     * @throws IllegalValueException if task has an invalid task detail
     */
    private List<Appointment> getRecurringAppointments(ReadOnlyTask task, int index)
            throws IllegalValueException {    
        assert isDisplayableInCalendar(task);   
        assert task.getRecurrence().hasRecurrence();

        List<Appointment> results = new ArrayList<Appointment>();
        
        results.add(getFirstAppointment(task, index));
        
        Recurrence remainingRecurrence = task.getRecurrence();
        Deadline currentDeadline = task.getDeadline();
        Period currentPeriod = task.getPeriod();
        
        while (remainingRecurrence.hasRecurrence()) {
            currentDeadline = getNextRecurringDeadline(remainingRecurrence, currentDeadline);
            currentPeriod = getNextRecurringPeriod(remainingRecurrence, currentPeriod);            
            remainingRecurrence = remainingRecurrence.getRecurrenceWithOneFrequencyLess();
            
            results.add(new CalendarReadOnlyRecurredAppointment(task, index, currentDeadline, currentPeriod));
        }
        
        return results;
    }

    /**
     * Gets the next period following right after the given period,
     * with reference to the recurring pattern given.

     * @throws IllegalValueException if the new period time is not valid.
     */
    private Period getNextRecurringPeriod(Recurrence recurrence, Period period)
            throws IllegalValueException {

        if (period.hasPeriod()) {
            Date newStartTime = recurrence.getNextDateAfterRecurrence(period.getStartTime());
            Date newEndTime = recurrence.getNextDateAfterRecurrence(period.getEndTime());
            return new Period(newStartTime, newEndTime);
        }

        return period;
    }

    /**
     * Gets the next deadline following right after the given deadline,
     * with reference to the recurring pattern given.
     */
    private Deadline getNextRecurringDeadline(Recurrence recurrence, Deadline deadline) {

        if (deadline.hasDeadline()) {
            return new Deadline(recurrence.getNextDateAfterRecurrence(deadline.getDeadline()));
        }

        return deadline;
    }

    /**
     * Select the particular task in the calendar.
     */
    public void selectTask(ReadOnlyTask taskToSelect) {
        logger.fine("Calendar will handle selectTask()");
        agendaView.selectedAppointments().clear();
        
        CalendarReadOnlyAppointment taskAppointment = new CalendarReadOnlyAppointment(taskToSelect, -1);
        for (Appointment appointment : agendaView.appointments()) {
            if (taskAppointment.hasSameAssociatedTask(appointment)) {
                logger.fine("Calendar found the right task to select!");
                agendaView.setDisplayedLocalDateTime(taskAppointment.getStartLocalDateTime());
                agendaView.selectedAppointments().add(appointment);
                lastSelectedTask = taskToSelect;
                break;
            }
        }
    }
    
    /**
     * Re-selects the last selected task.
     */
    public void selectLastSelectedTask() {
        selectDate(new Date());
        if (lastSelectedTask != null) {
            selectTask(lastSelectedTask);
        }
    }
    
    /**
     * Determine whether the task can be displayed in the calendar. It cannot
     * be displayed if the task has no timings at all.
     */
    private boolean isDisplayableInCalendar(ReadOnlyTask task) {
        return task.getPeriod().hasPeriod() || task.getDeadline().hasDeadline();
    }
}
