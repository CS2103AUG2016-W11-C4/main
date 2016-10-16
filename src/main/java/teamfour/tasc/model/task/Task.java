package teamfour.tasc.model.task;

import teamfour.tasc.commons.util.CollectionUtil;
import teamfour.tasc.model.tag.UniqueTagList;
import teamfour.tasc.model.task.exceptions.TaskAlreadyCompletedException;

import java.util.Objects;

/**
 * Represents a Task in the task list.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Task implements ReadOnlyTask {

    private Name name;
    private Complete complete;
    
    private Deadline deadline;
    private Period period;
    private Recurrence recurrence;

    private UniqueTagList tags;

    /**
     * Every field must be present and not null.
     */
    public Task(Name name, Complete complete, Deadline deadline, Period period, Recurrence recurrence,
            UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(name, complete, deadline, period, recurrence, tags);
        this.name = name;
        this.complete = complete;
        this.deadline = deadline;
        this.period = period;
        this.recurrence = recurrence;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
        
        if (!Task.isRecurrenceValid(deadline, period, recurrence)) {
            this.recurrence = new Recurrence();
        }
    }

    /**
     * Copy constructor.
     */
    public Task(ReadOnlyTask source) {
        this(source.getName(), source.getComplete(), source.getDeadline(), source.getPeriod(),
                source.getRecurrence(), source.getTags());
    }
    
    /**
     * Floating task should never have recurrence value.
     * @return
     */
    public static boolean isRecurrenceValid(Deadline deadline, Period period, Recurrence recurrence) {
        assert !CollectionUtil.isAnyNull(deadline, period, recurrence);
        
        boolean floatingTask = !deadline.hasDeadline() && !period.hasPeriod();

        if (floatingTask && recurrence.hasRecurrence()) {
            return false;
        }
        
        return true;
    }
        
    /**
     * Convert an uncompleted task to completed.
     * 
     * @param taskToConvert to completed
     * @return the same task but with completed = true
     * @throws TaskAlreadyCompletedException if task is already completed
     */
    public static Task convertToComplete(ReadOnlyTask taskToConvert) throws TaskAlreadyCompletedException {
        if (taskToConvert.getComplete().isCompleted() == true) {
            throw new TaskAlreadyCompletedException();
        }
        
        return new Task(taskToConvert.getName(),
                new Complete(true),
                taskToConvert.getDeadline(),
                taskToConvert.getPeriod(),
                taskToConvert.getRecurrence(),
                taskToConvert.getTags());
    }

    @Override
    public Name getName() {
        return name;
    }

    @Override
    public UniqueTagList getTags() {
        return new UniqueTagList(tags);
    }

    /**
     * Replaces this task's tags with the tags in the argument tag list.
     */
    public void setTags(UniqueTagList replacement) {
        tags.setTags(replacement);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyTask // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyTask) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, complete, deadline, period, recurrence, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

    @Override
    public Complete getComplete() {
        return complete;
    }

    @Override
    public Deadline getDeadline() {
        return deadline;
    }

    @Override
    public Period getPeriod() {
        return period;
    }

    @Override
    public Recurrence getRecurrence() {
        return recurrence;
    }
}
