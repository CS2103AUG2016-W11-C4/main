//@@author A0140011L
package teamfour.tasc.ui.calendar;

import java.time.LocalDateTime;
import java.util.Date;

import jfxtras.scene.control.agenda.Agenda.AppointmentGroup;
import teamfour.tasc.commons.util.DateUtil;
import teamfour.tasc.model.task.Deadline;
import teamfour.tasc.model.task.Period;
import teamfour.tasc.model.task.ReadOnlyTask;
import teamfour.tasc.model.task.status.EventStatus;

public class CalendarReadOnlyRecurredAppointment extends CalendarReadOnlyAppointment {

    private final Deadline deadlineForOccurrence;
    private final Period periodForOccurence;
    
    public CalendarReadOnlyRecurredAppointment(ReadOnlyTask associatedTask,
            int associatedIndex,
            Deadline deadlineForOccurrence, Period periodForOccurence) {
        super(associatedTask, associatedIndex);

        this.deadlineForOccurrence = deadlineForOccurrence;
        this.periodForOccurence = periodForOccurence;
    }
    
    @Override
    public AppointmentGroup getAppointmentGroup() {
        Date currentTime = DateUtil.getInstance().getCurrentTime();
        
        if (deadlineForOccurrence.hasDeadline() && deadlineForOccurrence.isOverdue(currentTime)) {
            return CalendarAppointmentGroups.OVERDUE;
        }

        if (periodForOccurence.hasPeriod()
                && (periodForOccurence.getEventStatus(currentTime) == EventStatus.ENDED)) {
            return CalendarAppointmentGroups.COMPLETED;
        }
        return CalendarAppointmentGroups.RECURRING;
    }

    @Override
    public LocalDateTime getStartLocalDateTime() {
        if (deadlineForOccurrence.hasDeadline()) {
            return getProperDeadlineStartTime(deadlineForOccurrence.getDeadline());
        }

        if (periodForOccurence.hasPeriod()) {
            return DateUtil.convertToLocalDateTime(periodForOccurence.getStartTime());
        }

        return null;
    }

    @Override
    public LocalDateTime getEndLocalDateTime() {
        if (deadlineForOccurrence.hasDeadline()) {
            return getProperDeadlineEndTime(deadlineForOccurrence.getDeadline());
        }

        if (periodForOccurence.hasPeriod()) {
            return DateUtil.convertToLocalDateTime(periodForOccurence.getEndTime());
        }

        return null;
    }
}
