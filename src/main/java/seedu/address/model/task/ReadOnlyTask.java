package seedu.address.model.task;

import seedu.address.model.tag.UniqueTagList;

/**
 * A read-only immutable interface for a Task in the task list.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyTask {

    Name getName();
    Complete getComplete();
    
    Deadline getDeadline();
    Period getPeriod();
    Recurrence getDeadlineRecurrence();
    Recurrence getPeriodRecurrence();

    /**
     * The returned TagList is a deep copy of the internal TagList,
     * changes on the returned list will not affect the task's internal tags.
     */
    UniqueTagList getTags();

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyTask other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && (other.getName().equals(this.getName()) // state checks here onwards
                    && other.getComplete().equals(this.getComplete())
                    && other.getDeadline().equals(this.getDeadline())
                    && other.getPeriod().equals(this.getPeriod()) 
                    && other.getDeadlineRecurrence().equals(this.getDeadlineRecurrence())
                    && other.getPeriodRecurrence().equals(this.getPeriodRecurrence())));
    }

    /**
     * Formats the task as text, showing all task details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
            .append("Completion Status: ")
            .append(getComplete());
        
        if (getDeadline().hasDeadline) {
            builder.append("Deadline: ")
                .append(getDeadline());
        }
        
        if (getPeriod().hasPeriod) {
            builder.append("Period: ")
                .append(getPeriod());
        }

        if (getDeadlineRecurrence().hasRecurrence) {
            builder.append("Recurrence (deadline): ")
                .append(getDeadlineRecurrence());
        }

        if (getPeriodRecurrence().hasRecurrence) {
            builder.append("Recurrence (period): ")
                .append(getPeriodRecurrence());
        }
        
        builder.append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

    /**
     * Returns a string representation of this Task's tags
     */
    default String tagsString() {
        final StringBuffer buffer = new StringBuffer();
        final String separator = ", ";
        getTags().forEach(tag -> buffer.append(tag).append(separator));
        if (buffer.length() == 0) {
            return "";
        } else {
            return buffer.substring(0, buffer.length() - separator.length());
        }
    }

}
