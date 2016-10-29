//@@author A0140011L
package teamfour.tasc.ui.calendar;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.Temporal;
import java.util.Calendar;
import java.util.Date;

import jfxtras.scene.control.agenda.Agenda.Appointment;
import jfxtras.scene.control.agenda.Agenda.AppointmentGroup;
import teamfour.tasc.commons.util.DateUtil;
import teamfour.tasc.model.task.ReadOnlyTask;
import teamfour.tasc.model.task.status.EventStatus;

/**
 * An implementation of the jfxtra's appointment class to display a task in our
 * agendar calendar control.
 */
public class CalendarReadOnlyAppointment implements Appointment {

    protected ReadOnlyTask associatedTask;
    private int associatedIndex;

    public CalendarReadOnlyAppointment(ReadOnlyTask associatedTask, int associatedIndex) {
        this.associatedTask = associatedTask;
        this.associatedIndex = associatedIndex;
    }

    /**
     * Do both objects have the same associated task?
     *
     * Obviously they must both be CalendarReadOnlyAppointment. The
     * associatedTask variables must also match
     */
    public boolean hasSameAssociatedTask(Appointment appointment) {
        if (appointment instanceof CalendarReadOnlyAppointment) {
            CalendarReadOnlyAppointment readOnlyAppointment = (CalendarReadOnlyAppointment) appointment;
            return associatedTask.equals(readOnlyAppointment.associatedTask);
        } else {
            return false;
        }
    }

    @Override
    public Boolean isWholeDay() {
        // not supported
        return false;
    }

    @Override
    public void setWholeDay(Boolean b) {
        // do nothing (read-only)
    }

    @Override
    public String getSummary() {
        return getDescription();
    }

    @Override
    public void setSummary(String s) {
        // do nothing (read-only)
    }

    @Override
    public String getDescription() {
        return "(" + associatedIndex + "): " + associatedTask.getName().getName();
    }

    @Override
    public void setDescription(String s) {
        // do nothing (read-only)
    }

    @Override
    public String getLocation() {
        // not supported
        return "";
    }

    @Override
    public void setLocation(String s) {
        // do nothing (read-only)
    }

    @Override
    public AppointmentGroup getAppointmentGroup() {
        if (associatedTask.getComplete().isCompleted()) {
            return CalendarAppointmentGroups.COMPLETED;
        }

        if (associatedTask.getPeriod().hasPeriod()) {
            if (associatedTask.getEventStatus(DateUtil.getInstance().getCurrentTime()) 
                    == EventStatus.ENDED) {
                return CalendarAppointmentGroups.COMPLETED;
            }

            return CalendarAppointmentGroups.PERIOD;
        }

        if (associatedTask.getDeadline().hasDeadline()) {
            if (associatedTask.isOverdue(DateUtil.getInstance().getCurrentTime())) {
                return CalendarAppointmentGroups.OVERDUE;
            }

            return CalendarAppointmentGroups.DEADLINE;
        }

        return null;
    }

    @Override
    public void setAppointmentGroup(AppointmentGroup s) {
        // do nothing (read-only)
    }

    @Override
    public Calendar getStartTime() {
        // not supported (use getLocalStartTime())
        return null;
    }

    @Override
    public void setStartTime(Calendar c) {
        // do nothing (read-only)
    }

    @Override
    public Calendar getEndTime() {
        // not supported (use getLocalEndTime())
        return null;
    }

    @Override
    public void setEndTime(Calendar c) {
        // do nothing (read-only)
    }

    @Override
    public Temporal getStartTemporal() {
        // not supported (use getLocalStartTime())
        return null;
    }

    @Override
    public void setStartTemporal(Temporal t) {
        // do nothing (read-only)
    }

    @Override
    public Temporal getEndTemporal() {
        // not supported (use getLocalEndTime())
        return null;
    }

    @Override
    public void setEndTemporal(Temporal t) {
        // do nothing (read-only)
    }

    @Override
    public LocalDateTime getStartLocalDateTime() {
        if (associatedTask.getDeadline().hasDeadline()) {
            return DateUtil.convertToLocalDateTime(associatedTask.getDeadline().getDeadline());
        }

        if (associatedTask.getPeriod().hasPeriod()) {
            return DateUtil.convertToLocalDateTime(associatedTask.getPeriod().getStartTime());
        }

        return null;
    }

    @Override
    public void setStartLocalDateTime(LocalDateTime v) {
        // do nothing (read-only)
    }

    @Override
    public LocalDateTime getEndLocalDateTime() {
        if (associatedTask.getDeadline().hasDeadline()) {
            return DateUtil.convertToLocalDateTime(associatedTask.getDeadline().getDeadline())
                    .plusHours(1);
        }

        if (associatedTask.getPeriod().hasPeriod()) {
            return DateUtil.convertToLocalDateTime(associatedTask.getPeriod().getEndTime());
        }

        return null;
    }

    @Override
    public void setEndLocalDateTime(LocalDateTime v) {
        // do nothing (read-only)
    }
}