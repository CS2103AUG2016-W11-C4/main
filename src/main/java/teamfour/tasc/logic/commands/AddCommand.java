package teamfour.tasc.logic.commands;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import teamfour.tasc.commons.core.EventsCenter;
import teamfour.tasc.commons.core.UnmodifiableObservableList;
import teamfour.tasc.commons.events.ui.JumpToListRequestEvent;
import teamfour.tasc.commons.exceptions.IllegalValueException;
import teamfour.tasc.logic.keyword.AddCommandKeyword;
import teamfour.tasc.model.tag.Tag;
import teamfour.tasc.model.tag.UniqueTagList;
import teamfour.tasc.model.task.Complete;
import teamfour.tasc.model.task.Deadline;
import teamfour.tasc.model.task.Name;
import teamfour.tasc.model.task.Period;
import teamfour.tasc.model.task.ReadOnlyTask;
import teamfour.tasc.model.task.Recurrence;
import teamfour.tasc.model.task.Task;
import teamfour.tasc.model.task.UniqueTaskList;

/**
 * Adds a task to the task list.
 */
// @@author A0127014W
public class AddCommand extends Command {

    public static final String COMMAND_WORD = AddCommandKeyword.keyword;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the task list. "
            + "Parameters: NAME [by DEADLINE] [from STARTTIME] [to ENDTIME] [repeat RECURRENCE COUNT] [tag TAG]...\n"
            + "Example: " + COMMAND_WORD + " \"Watch Movie\" tag recreation";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task list";
    public static final String MESSAGE_INVALID_DATES = "Invalid date(s)";

    private final Task toAdd;

    /**
     * Add Command Convenience constructor using raw values.
     * Constructs a Task object to be added
     *
     * @param name          Name of task to add
     * @param deadlineTime  String containing deadline
     * @param startTime     String containing start time
     * @param endTime       String containing end time
     * @param repeat        String containing repeat parameters
     * @param tagStrings    Set of String containing tags
     * @throws IllegalValueException
     *             if any of the raw values are invalid
     */
    public AddCommand(String name, String deadlineTime, String startTime,
                String endTime, String repeat, Set<String> tagStrings) throws IllegalValueException {
        final Set<Tag> tagSet = getTagSetFromStringSet(tagStrings);
        Deadline deadline = getDeadlineFromString(deadlineTime);
        Period period = getPeriodFromStrings(deadlineTime, startTime, endTime);
        Recurrence taskRecurrence = getRecurrenceFromStrings(startTime, endTime, repeat, deadlineTime);

        this.toAdd = new Task(new Name(name), new Complete(false), deadline, period, taskRecurrence,
                new UniqueTagList(tagSet));
    }
    /**
     * Returns a Recurrence object from the input strings
     *
     * @param startTime         String containing start time
     * @param endTime           String containing end time
     * @param repeat            String containing repeat parameters
     * @param deadlineTime      String containing deadline
     * @return taskRecurrence   Recurrence object
     * @throws IllegalValueException
     */
    private Recurrence getRecurrenceFromStrings(String startTime, String endTime,
                String repeat, String deadlineTime) throws IllegalValueException {
        Recurrence taskRecurrence = new Recurrence();
        if (repeat != null && (startTime != null && endTime != null || deadlineTime != null)) {
            taskRecurrence = CommandHelper.getRecurrence(repeat);
        }
        return taskRecurrence;
    }
    /**
     * Returns a Period object from the input string
     *
     * @param deadlineTime  String containing deadline
     * @param startTime     String containing start time
     * @param endTime       String containing end time
     * @return period       Period object
     * @throws IllegalValueException
     */
    private Period getPeriodFromStrings(String deadlineTime, String startTime, String endTime)
            throws IllegalValueException {
        Period period = new Period();
        if ((startTime != null) && (endTime != null)) {
            List<Date> dates = CommandHelper.convertStringToMultipleDates(startTime + " and " + endTime);
            if (dates.size() < 2) {
                throw new IllegalValueException(MESSAGE_INVALID_DATES);
            }
            period = new Period(dates.get(0), dates.get(1));
        } else if ((startTime != null) && (deadlineTime != null)) {
            List<Date> dates = CommandHelper.convertStringToMultipleDates(startTime + " and " + deadlineTime);
            if (dates.size() < 2) {
                throw new IllegalValueException(MESSAGE_INVALID_DATES);
            }
            period = new Period(dates.get(0), dates.get(1));
        }
        return period;
    }
    /**
     * Returns a Deadline object from the input string
     *
     * @param deadlineTime  String containing deadline
     * @return deadline     Deadline object
     * @throws IllegalValueException
     */
    private Deadline getDeadlineFromString(String deadlineTime) throws IllegalValueException {
        Deadline deadline = new Deadline();
        if (deadlineTime != null) {
            deadline = new Deadline(CommandHelper.convertStringToDate(deadlineTime));
        }
        return deadline;
    }
    /**
     * Returns a Set of Tag objects from the input Set of Strings
     *
     * @param tagStrings    Set of Strings
     * @return tagSet       Set of Tag objects
     * @throws IllegalValueException
     */
    private Set<Tag> getTagSetFromStringSet(Set<String> tagStrings) throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tagStrings) {
            tagSet.add(new Tag(tagName));
        }
        return tagSet;
    }

    @Override
    public CommandResult execute() {
        assert model != null;
        try {
            model.addTask(toAdd);
            selectAddedTask();
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            return new CommandResult(MESSAGE_DUPLICATE_TASK);
        }
    }

    /**
     * Raises an event to select the last task that was added
     */
    private void selectAddedTask() {
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();
        int targetIndex = lastShownList.size();
        EventsCenter.getInstance().post(new JumpToListRequestEvent(targetIndex - 1));
    }

    @Override
    public boolean canUndo() {
        return true;
    }

}
