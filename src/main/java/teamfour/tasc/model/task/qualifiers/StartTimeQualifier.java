//@@author A0148096W

package teamfour.tasc.model.task.qualifiers;

import java.util.Date;

import teamfour.tasc.model.task.ReadOnlyTask;

/**
 * This qualifier allows tasks with period or deadline
 * after the specified date to pass.
 */
public class StartTimeQualifier implements Qualifier {
    private Date startTime;

    public StartTimeQualifier(Date startTime) {
        assert startTime != null;
        this.startTime = startTime;
    }

    @Override
    public boolean run(ReadOnlyTask task) {
        assert task != null;
        if (task.getPeriod().hasPeriod()) {
            return startTime.before(task.getPeriod().getEndTime());
        } else if (task.getDeadline().hasDeadline()) {
            return startTime.before(task.getDeadline().getDeadline());
        }
        return true;
    }

    @Override
    public String toString() {
        return "startTime=" + startTime;
    }
}