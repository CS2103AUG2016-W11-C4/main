//@@author A0148096W

package teamfour.tasc.logic.commands;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import teamfour.tasc.commons.exceptions.IllegalValueException;
import teamfour.tasc.logic.keyword.ByKeyword;
import teamfour.tasc.logic.keyword.FromKeyword;
import teamfour.tasc.logic.keyword.HideCommandKeyword;
import teamfour.tasc.logic.keyword.OnKeyword;
import teamfour.tasc.logic.keyword.TagKeyword;
import teamfour.tasc.logic.keyword.ToKeyword;
import teamfour.tasc.model.task.qualifiers.DeadlineQualifier;
import teamfour.tasc.model.task.qualifiers.EndTimeQualifier;
import teamfour.tasc.model.task.qualifiers.StartTimeQualifier;
import teamfour.tasc.model.task.qualifiers.StartToEndTimeQualifier;
import teamfour.tasc.model.task.qualifiers.TagsQualifier;
import teamfour.tasc.model.task.qualifiers.TypeQualifier;

/**
 * Hides results from current listing results to the user that match the filters.
 */
public class HideCommand extends Command {
    public static final String COMMAND_WORD = HideCommandKeyword.keyword;
    
    public static final String MESSAGE_USAGE = COMMAND_WORD 
            + ": Hides listing results with specified type, date or tags. "
            + "Parameters: [TYPE...] [on DATE] [by DEADLINE] [from START_TIME] "
            + "[to END_TIME] [tag \"TAG\"...]\n"
            + "Example: " + COMMAND_WORD + " events on 24 Sep, tag \"Important\"";
    
    public static final String KEYWORD_DATE = OnKeyword.keyword;
    public static final String KEYWORD_DEADLINE = ByKeyword.keyword;
    public static final String KEYWORD_PERIOD_START_TIME = FromKeyword.keyword;
    public static final String KEYWORD_PERIOD_END_TIME = ToKeyword.keyword;
    public static final String KEYWORD_TAG = TagKeyword.keyword;
    
    public static final String[] VALID_KEYWORDS = { COMMAND_WORD, KEYWORD_DATE,
            KEYWORD_DEADLINE, KEYWORD_PERIOD_START_TIME, KEYWORD_PERIOD_END_TIME, KEYWORD_TAG };

    private final String type;
    private final Date deadline;
    private final Date startTime;
    private final Date endTime;
    private final Set<String> tags;
    private final boolean hasDate;
    
    /**
     * Show Command
     * Convenience constructor using raw values.
     * Set any parameter as null if it is not required.
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public HideCommand(String type, String date, String deadline, String startTime, 
                        String endTime, Set<String> tags) throws IllegalValueException {
        
        this.deadline = CommandHelper.tryConvertStringToDateOrReturnNull(deadline);
        
        Date convertedDate = CommandHelper.tryConvertStringToDateOrReturnNull(date);
        if (convertedDate != null) {
            hasDate = true;
            this.startTime = CommandHelper.getStartOfTheDate(convertedDate);
            this.endTime = CommandHelper.getEndOfTheDate(convertedDate);
        } else {
            hasDate = false;
            this.startTime = CommandHelper.tryConvertStringToDateOrReturnNull(startTime);
            this.endTime = CommandHelper.tryConvertStringToDateOrReturnNull(endTime);
        }
        
        this.tags = new HashSet<String>(tags);
        this.type = type;
    }
    
    /**
     * Precondition: model is not null.
     * Adds the filters in this command to the model. 
     * Does not update the list yet.
     */
    private void addCommandFiltersToModel() {
        assert model != null;
        
        if (type != null) {
            model.addTaskListFilter(new TypeQualifier(type), true);
        }
        if (deadline != null) {
            model.addTaskListFilter(new DeadlineQualifier(deadline), true);
        }
        if (hasDate) {
            model.addTaskListFilter(new StartToEndTimeQualifier(startTime, endTime), true);
        }
        if (!hasDate && startTime != null) {
            model.addTaskListFilter(new StartTimeQualifier(startTime), true);
        }
        if (!hasDate && endTime != null) {
            model.addTaskListFilter(new EndTimeQualifier(endTime), true);
        }
        if (!tags.isEmpty()) {
            model.addTaskListFilter(new TagsQualifier(tags), true);
        }
    }

    @Override
    public CommandResult execute() {
        assert model != null;
        
        addCommandFiltersToModel();
        model.updateFilteredTaskListByFilters();
        
        return new CommandResult(getMessageForTaskListShownSummary(model.getFilteredTaskList().size()));
    }

    @Override
    public boolean canUndo() {
        return false;
    }

}
