//@@author A0148096W

package teamfour.tasc.model.task.qualifiers;

import teamfour.tasc.model.task.ReadOnlyTask;

/**
 * This qualifier allows tasks with types
 * which match the specified types to pass.
 */
public class TypeQualifier implements Qualifier {
    private String type;

    public TypeQualifier(String type) {
        assert type != null;
        this.type = type;
    }

    @Override
    public boolean run(ReadOnlyTask task) {
        assert task != null;
        String[] typeWords = type.toLowerCase().split(" ");
        String taskType = (" " + task.getAsTypeKeywords()).toLowerCase();

        for (String typeWord : typeWords) {
            if (!taskType.contains(" " + typeWord)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return "type=" + type;
    }
}