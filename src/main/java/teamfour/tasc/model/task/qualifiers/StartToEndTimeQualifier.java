//@@author A0148096W

package teamfour.tasc.model.task.qualifiers;

import java.util.Date;

import teamfour.tasc.model.task.ReadOnlyTask;

/**
 * This qualifier allows tasks with period or deadline
 * exactly on the specified date to pass.
 */
public class StartToEndTimeQualifier implements Qualifier {
    private Date startTime;
    private Date endTime;

    public StartToEndTimeQualifier(Date startTime, Date endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public boolean run(ReadOnlyTask task) {
        if (task.getPeriod().hasPeriod()) {
            return startTime.before(task.getPeriod().getEndTime()) &&
                    endTime.after(task.getPeriod().getStartTime());
        } else if (task.getDeadline().hasDeadline()) {
            return startTime.before(task.getDeadline().getDeadline()) &&
                    endTime.after(task.getDeadline().getDeadline());
        }
        return false;
    }

    @Override
    public String toString() {
        return "startTime=" + startTime + ",endTime=" + endTime;
    }
}