//@@author A0148096W

package teamfour.tasc.model.task.qualifiers;

import java.util.Date;

import teamfour.tasc.model.task.ReadOnlyTask;

/**
 * This qualifier allows tasks with deadlines
 * before the specified date to pass.
 */
public class DeadlineQualifier implements Qualifier {
    private Date deadline;

    public DeadlineQualifier(Date deadline) {
        this.deadline = deadline;
    }

    @Override
    public boolean run(ReadOnlyTask task) {
        if (task.getDeadline().hasDeadline() == false) {
            return false;
        }
        return deadline.after(task.getDeadline().getDeadline());
    }

    @Override
    public String toString() {
        return "deadline=" + deadline;
    }
}