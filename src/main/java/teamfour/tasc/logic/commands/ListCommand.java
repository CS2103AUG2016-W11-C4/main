//@@author A0148096W

package teamfour.tasc.logic.commands;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import teamfour.tasc.commons.exceptions.IllegalValueException;
import teamfour.tasc.logic.keyword.ByKeyword;
import teamfour.tasc.logic.keyword.FromKeyword;
import teamfour.tasc.logic.keyword.ListCommandKeyword;
import teamfour.tasc.logic.keyword.SortKeyword;
import teamfour.tasc.logic.keyword.TagKeyword;
import teamfour.tasc.logic.keyword.ToKeyword;
import teamfour.tasc.model.Model;
import teamfour.tasc.model.task.qualifiers.DeadlineQualifier;
import teamfour.tasc.model.task.qualifiers.EndTimeQualifier;
import teamfour.tasc.model.task.qualifiers.StartTimeQualifier;
import teamfour.tasc.model.task.qualifiers.TagsQualifier;
import teamfour.tasc.model.task.qualifiers.TypeQualifier;

/**
 * Lists all tasks in the task list to the user with filters and sort.
 */
public class ListCommand extends Command {
    
    public static final String COMMAND_WORD = ListCommandKeyword.keyword;

    public static final String MESSAGE_USAGE = COMMAND_WORD 
            + ": List all tasks/events with filters. "
            + "Parameters: [TYPE...] [by DEADLINE] [from START_TIME] "
            + "[to END_TIME] [tag \"TAG\"...] [sort SORTING_ORDER]\n"
            + "Example: " + COMMAND_WORD
            + " completed tasks, tag \"Important\", sort earliest first";

    public static final String KEYWORD_DEADLINE = ByKeyword.keyword;
    public static final String KEYWORD_PERIOD_START_TIME = FromKeyword.keyword;
    public static final String KEYWORD_PERIOD_END_TIME = ToKeyword.keyword;
    public static final String KEYWORD_TAG = TagKeyword.keyword;
    public static final String KEYWORD_SORT = SortKeyword.keyword;

    public static final String[] VALID_KEYWORDS = { COMMAND_WORD, KEYWORD_DEADLINE,
            KEYWORD_PERIOD_START_TIME, KEYWORD_PERIOD_END_TIME, KEYWORD_TAG, KEYWORD_SORT };

    private final String type;
    private final Date deadline;
    private final Date startTime;
    private final Date endTime;
    private final Set<String> tags;
    private final String sortOrder;

    /**
     * List Command with default values
     * Lists all uncompleted tasks and events from now.
     */
    public ListCommand() throws IllegalValueException {
        this(Model.FILTER_TYPE_DEFAULT, null, null, null, 
                new HashSet<String>(), Model.SORT_ORDER_DEFAULT);
    }
    
    /**
     * List Command
     * Convenience constructor using raw values.
     * Set any parameter as null if it is not required.
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public ListCommand(String type, String deadline, String startTime, String endTime,
                        Set<String> tags, String sortingOrder) throws IllegalValueException {
        
        this.deadline = CommandHelper.tryConvertStringToDateOrReturnNull(deadline);
        this.startTime = CommandHelper.tryConvertStringToDateOrReturnNull(startTime);
        this.endTime = CommandHelper.tryConvertStringToDateOrReturnNull(endTime);
        this.tags = new HashSet<String>(tags);
        this.type = type;
        this.sortOrder = sortingOrder;
    }

    /**
     * Precondition: model is not null.
     * Resets the filters in this command to filters in the model.
     * Does not update the list yet.
     */
    private void addCommandFiltersToModel() {
        assert model != null;
        
        if (type != null) {
            model.addTaskListFilter(new TypeQualifier(type), false);
        }
        if (deadline != null) {
            model.addTaskListFilter(new DeadlineQualifier(deadline), false);
        }
        if (startTime != null) {
            model.addTaskListFilter(new StartTimeQualifier(startTime), false);
        }
        if (endTime != null) {
            model.addTaskListFilter(new EndTimeQualifier(endTime), false);
        }
        if (!tags.isEmpty()) {
            model.addTaskListFilter(new TagsQualifier(tags), false);
        }
    }

    @Override
    public CommandResult execute() {
        assert model != null;

        model.resetTaskListFilter();
        addCommandFiltersToModel();
        model.updateFilteredTaskListByFilters();

        if (sortOrder != null) {
            model.sortFilteredTaskList(sortOrder);
        }

        return new CommandResult(
                getMessageForTaskListShownSummary(model.getFilteredTaskList().size()));
    }

    @Override
    public boolean canUndo() {
        return false;
    }

}
