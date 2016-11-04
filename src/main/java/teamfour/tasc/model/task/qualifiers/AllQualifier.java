//@@author A0148096W

package teamfour.tasc.model.task.qualifiers;

import teamfour.tasc.model.task.ReadOnlyTask;

/**
 * This qualifier allows all tasks to pass.
 */
public class AllQualifier implements Qualifier {
    public AllQualifier() {}

    @Override
    public boolean run(ReadOnlyTask task) {
        return true;
    }

    @Override
    public String toString() {
        return "all qualifier";
    }
}