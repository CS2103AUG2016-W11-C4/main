//@@author A0148096W

package teamfour.tasc.model.task.qualifiers;

import java.util.Date;

import teamfour.tasc.model.task.ReadOnlyTask;

/**
 * This qualifier allows tasks with period or deadline
 * before the specified date to pass.
 */
public class EndTimeQualifier implements Qualifier {
    private Date endTime;

    public EndTimeQualifier(Date endTime) {
        this.endTime = endTime;
    }

    @Override
    public boolean run(ReadOnlyTask task) {
        if (task.getPeriod().hasPeriod()) {
            return endTime.after(task.getPeriod().getStartTime());
        } else if (task.getDeadline().hasDeadline()) {
            return endTime.after(task.getDeadline().getDeadline());
        }
        return true;
    }

    @Override
    public String toString() {
        return "endTime=" + endTime;
    }
}