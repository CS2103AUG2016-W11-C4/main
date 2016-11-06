package teamfour.tasc.model.task.qualifiers;

import teamfour.tasc.model.task.ReadOnlyTask;

/**
 * A Qualifier decides on whether to allow a ReadOnlyTask
 * to pass through its filter to be shown in a filteredlist.
 */
public interface Qualifier {
    
    /**
     * Precondition: argument is not null.
     * Returns true if this task passes through the filter,
     * or false otherwise.
     */
    boolean run(ReadOnlyTask task);
    
    /**
     * Returns a string representation of this object.
     */
    String toString();
}