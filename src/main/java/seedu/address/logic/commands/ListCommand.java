package seedu.address.logic.commands;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Lists all tasks in the task list to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";
    
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all tasks with filters. "
            + "Parameters: [TYPE] [by DEADLINE] [from START_TIME [to END_TIME]] [tag TAG...] [sort SORTING_ORDER]\n"
            + "Example: " + COMMAND_WORD
            + " tasks by 18 Sep, tag Important, sort earliest first";

    private final String type;
    private final Date deadline;
    private final Date startTime;
    private final Date endTime;
    private final Set<String> tags;
    private final String sortingOrder;

    /**
     * List Command with default values
     * Lists all uncompleted tasks and events from now.
     */
    public ListCommand() throws IllegalValueException {
        this.type = "uncompleted";
        this.deadline = null;
        this.startTime = new Date();
        this.endTime = null;
        this.tags = new HashSet<>();
        this.sortingOrder = "earliest first";
    }
    
    /**
     * List Command
     * Convenience constructor using raw values.
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public ListCommand(String type, String deadline, String startTime, String endTime, 
                        Set<String> tags, String sortingOrder) throws IllegalValueException {
        if (deadline != null) {
            this.deadline = CommandHelper.convertStringToDate(deadline);
        } else {
            this.deadline = null;
        }
        
        if (startTime != null) {
            this.startTime = CommandHelper.convertStringToDate(startTime);
        } else {
            this.startTime = null;
        }
        
        if (endTime != null) {
            this.endTime = CommandHelper.convertStringToDate(endTime);
        } else {
            this.endTime = null;
        }
        
        this.tags = new HashSet<>();
        for (String tagName : tags) {
            this.tags.add(tagName);
        }
        this.type = type;
        this.sortingOrder = sortingOrder;
    }

    @Override
    public CommandResult execute() {
        model.resetTaskListFilter();
        if (type != null)
            model.addTaskListFilterByType(type, false);
        if (deadline != null)
            model.addTaskListFilterByDeadline(deadline, false);
        if (startTime != null)
            model.addTaskListFilterByStartTime(startTime, false);
        if (endTime != null)
            model.addTaskListFilterByEndTime(endTime, false);
        if (!tags.isEmpty())
            model.addTaskListFilterByTags(tags, false);
        model.updateFilteredTaskListByFilter();
        
        // TODO: Sorting order
        
        return new CommandResult(getMessageForTaskListShownSummary(model.getFilteredTaskList().size()));
    }

    @Override
    public boolean canUndo() {
        return false;
    }

    @Override
    public CommandResult executeUndo() {
        return null;
    }
}
