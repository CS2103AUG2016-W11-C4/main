//@@author A0140011L
package teamfour.tasc.logic.commands;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import teamfour.tasc.commons.core.EventsCenter;
import teamfour.tasc.commons.core.LogsCenter;
import teamfour.tasc.commons.core.Messages;
import teamfour.tasc.commons.core.UnmodifiableObservableList;
import teamfour.tasc.commons.events.ui.JumpToListRequestEvent;
import teamfour.tasc.commons.exceptions.IllegalValueException;
import teamfour.tasc.logic.keyword.UpdateCommandKeyword;
import teamfour.tasc.model.tag.Tag;
import teamfour.tasc.model.tag.UniqueTagList;
import teamfour.tasc.model.tag.UniqueTagList.DuplicateTagException;
import teamfour.tasc.model.tag.exceptions.TagNotFoundException;
import teamfour.tasc.model.task.Complete;
import teamfour.tasc.model.task.Deadline;
import teamfour.tasc.model.task.Name;
import teamfour.tasc.model.task.Period;
import teamfour.tasc.model.task.ReadOnlyTask;
import teamfour.tasc.model.task.Recurrence;
import teamfour.tasc.model.task.Task;
import teamfour.tasc.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Updates a task identified using it's last displayed index from the task list.
 */
public class UpdateCommand extends Command {

    public static final String COMMAND_WORD = UpdateCommandKeyword.keyword;

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Updates the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer) [name NAME] [by DEADLINE] "
            + "[from START_TIME to END_TIME] [repeatdeadline FREQUENCY COUNT] "
            + "[repeattime FREQUENCY COUNT] [tag \"TAG\"...]\n" + "Example: " + COMMAND_WORD
            + " 1 by 15 Sep 3pm";

    public static final String MESSAGE_UPDATE_TASK_SUCCESS = "Updated Task: %1$s";
    private static final String MESSAGE_UPDATE_TASK_UNDO_SUCCESS = 
            "Changes to task was reverted. Now: %1$s";
    private static final String MESSAGE_PERIOD_NEED_BOTH_START_AND_END_TIME = 
            "Period needs to have both start and end time.";

    public static final String PREFIX_REMOVE = "remove";

    public static final String KEYWORD_NAME = "name";
    public static final String KEYWORD_DEADLINE = "by";
    public static final String KEYWORD_PERIOD_START_TIME = "from";
    public static final String KEYWORD_PERIOD_END_TIME = "to";
    public static final String KEYWORD_RECURRENCE = "repeat";
    public static final String KEYWORD_TAG = "tag";

    public static final String KEYWORD_REMOVE_DEADLINE = PREFIX_REMOVE + KEYWORD_DEADLINE;
    public static final String KEYWORD_REMOVE_START_TIME = PREFIX_REMOVE
            + KEYWORD_PERIOD_START_TIME;
    public static final String KEYWORD_REMOVE_END_TIME = PREFIX_REMOVE + KEYWORD_PERIOD_END_TIME;
    public static final String KEYWORD_REMOVE_RECURRENCE = PREFIX_REMOVE
            + KEYWORD_RECURRENCE;
    public static final String KEYWORD_REMOVE_TAG = PREFIX_REMOVE + KEYWORD_TAG;

    public static final String[] VALID_KEYWORDS = { COMMAND_WORD, KEYWORD_NAME, KEYWORD_DEADLINE,
            KEYWORD_PERIOD_START_TIME, KEYWORD_PERIOD_END_TIME, KEYWORD_RECURRENCE, KEYWORD_TAG,
            KEYWORD_REMOVE_DEADLINE, KEYWORD_REMOVE_START_TIME, KEYWORD_REMOVE_END_TIME,
            KEYWORD_REMOVE_RECURRENCE, KEYWORD_REMOVE_TAG };

    public final int targetIndex;

    public final String updatedName;
    public final String updatedBy;
    public final String updatedStartTime;
    public final String updatedEndTime;
    public final String updatedRecurrence;
    public final Set<String> tagsToAdd;

    public final boolean removeDeadline;
    public final boolean removePeriod;
    public final boolean removeRecurrence;
    public final Set<String> tagsToRemove;
    
    private final Logger logger = LogsCenter.getLogger(UpdateCommand.class);

    private ReadOnlyTask oldReadOnlyTask;
    private Task newTask;

    /**
     * Constructs a new command for updating the details of a task.
     * 
     * Note: Parameters can be null, to indicate that no changes were made to
     * that particular detail.
     * 
     * @param targetIndex of the task to update
     */
    public UpdateCommand(int targetIndex, String newName, String newBy, String newStartTime,
            String newEndTime, String newRecurrence, Set<String> newTags, 
            boolean removeDeadline, boolean removePeriod, boolean removeRecurrence,
            Set<String> deleteTags) {
        this.targetIndex = targetIndex;

        this.updatedName = newName;
        this.updatedBy = newBy;
        this.updatedStartTime = newStartTime;
        this.updatedEndTime = newEndTime;
        this.updatedRecurrence = newRecurrence;
        this.tagsToAdd = newTags;

        this.removeDeadline = removeDeadline;
        this.removePeriod = removePeriod;
        this.removeRecurrence = removeRecurrence;
        this.tagsToRemove = deleteTags;
    }

    @Override
    public CommandResult execute() {
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        oldReadOnlyTask = lastShownList.get(targetIndex - 1);
        try {
            newTask = createUpdatedTaskFromOldTask(oldReadOnlyTask);
        } catch (IllegalValueException ive) {
            return new CommandResult(ive.getMessage());
        }

        try {
            model.updateTask(oldReadOnlyTask, newTask);
        } catch (TaskNotFoundException pnfe) {
            assert false : "The target task cannot be missing";
        }

        taskListSelectTask(targetIndex - 1);
        return new CommandResult(String.format(MESSAGE_UPDATE_TASK_SUCCESS, newTask));
    }
    
    /**
     * Simulates select command on the task in the task list
     */
    private void taskListSelectTask(int index) {
        EventsCenter.getInstance().post(new JumpToListRequestEvent(index));
        model.updateFilteredTaskListByFilter(); // refresh to expand selected TaskCard
    }

    @Override
    public boolean canUndo() {
        return true;
    }

    /**
     * Updates the task details of the old task by creating a new task with the
     * new updated details.
     * 
     * @param oldTask to be updated
     * @return the updated task with the new details
     */
    private Task createUpdatedTaskFromOldTask(ReadOnlyTask oldTask) throws IllegalValueException {
        Name newName = getUpdatedTaskName(oldTask);
        Complete newComplete = getUpdatedTaskCompleteStatus(oldTask);
        Deadline newDeadline = getUpdatedTaskDeadline(oldTask);
        Period newPeriod = getUpdatedTaskPeriod(oldTask);
        Recurrence newRecurrence = getUpdatedTaskRecurrence(oldTask);
        UniqueTagList newTags = getUpdatedTaskTags(oldTask);
        
        return new Task(newName, newComplete, newDeadline, newPeriod, newRecurrence, newTags);
    }
    
    /**
     * Gets the name of the updated task.
     * 
     * @param oldTask details before we executed the update command
     * @return the new name if the user requested to update it, otherwise
     * just return the old task's name.
     * @throws IllegalValueException if the name is invalid
     */
    private Name getUpdatedTaskName(ReadOnlyTask oldTask) throws IllegalValueException {
        if (this.updatedName != null) {
            return new Name(this.updatedName);
        }
        
        return oldTask.getName();        
    }
    
    /**
     * Gets the deadline of the updated task.
     * 
     * @param oldTask details before we executed the update command
     * @return the new deadline if the user requested to update it, otherwise
     * just return the old task's deadline.
     * @throws IllegalValueException if the deadline is invalid
     */
    private Deadline getUpdatedTaskDeadline(ReadOnlyTask oldTask) throws IllegalValueException {
        if (this.removeDeadline) {
            return new Deadline();
        }
        
        if (this.updatedBy != null) {
            return new Deadline(CommandHelper.convertStringToDate(this.updatedBy));
        }
        
        return oldTask.getDeadline();
    }
    
    /**
     * Gets the recurrence of the updated task.
     * 
     * @param oldTask details before we executed the update command
     * @return the new recurrence if the user requested to update it, otherwise
     * just return the old task's recurrence.
     * @throws IllegalValueException if the recurrence is invalid
     */
    private Recurrence getUpdatedTaskRecurrence(ReadOnlyTask oldTask) throws IllegalValueException {
        if (this.removeRecurrence) {
            return new Recurrence();
        }
        
        if (this.updatedRecurrence != null) {
            return CommandHelper.getRecurrence(this.updatedRecurrence);
        }
        
        return oldTask.getRecurrence();
    }
    
    /**
     * Gets the complete status of the updated task.
     * 
     * @param oldTask details before we executed the update command
     * @return the same as the old task, complete status cannot be changed by this command.
     * (Instead, must execute the complete command)
     */
    private Complete getUpdatedTaskCompleteStatus(ReadOnlyTask oldTask) {
        return oldTask.getComplete();
    }
    
    /**
     * Gets the period of the updated task.
     * 
     * @param oldTask details before we executed the update command
     * @return the new period if the user requested to update it, otherwise
     * just return the old task's period.
     * @throws IllegalValueException if the period is invalid
     */
    private Period getUpdatedTaskPeriod(ReadOnlyTask oldTask) throws IllegalValueException {
        if (this.removePeriod) {
            return new Period();
        }
        
        if (this.updatedStartTime != null || this.updatedEndTime != null) {
            return createNewPeriodFromCommandArguments(oldTask.getPeriod());
        }
        
        return oldTask.getPeriod();
    }

    /**
     * Gets the updated tags collection of the updated task.
     * 
     * @param oldTask details before we executed the update command
     * @return the new tags collection after we finish adding new tags
     * and removing old tags
     * @throws IllegalValueException if the tag is invalid
     */
    private UniqueTagList getUpdatedTaskTags(ReadOnlyTask oldTask) throws IllegalValueException {
        UniqueTagList result = oldTask.getTags();
        
        if (this.tagsToAdd != null) {
            for (String newTag : this.tagsToAdd) {
                try {
                    result.add(new Tag(newTag));
                } catch (DuplicateTagException dte) {
                    // do nothing (it is fine for the user to accidentally add
                    // the same tag)
                }
            }
        }

        if (this.tagsToRemove != null) {
            for (String redundantTag : this.tagsToRemove) {
                try {
                    result.remove(new Tag(redundantTag));
                } catch (TagNotFoundException tnfe) {
                    // do nothing (it is fine for the user to make a typo)
                }
            }
        }
        
        return result;
    }
    
    /**
     * Creates a new period object given the supplied arguments.
     * 
     * Pre-condition: The arguments MUST be provided. If the user
     * did not provide the arguments (i.e. did not intend to update
     * the period), do NOT call this method (see warning).
     * 
     * Warning: Methods other than {@link #getUpdatedTaskPeriod(ReadOnlyTask)}
     * should* NOT call this method directly. Call 
     * {@link #getUpdatedTaskPeriod(ReadOnlyTask)} instead to handle
     * cases where arguments may not be supplied,  or user requested
     * for period removal.
     * 
     * @param oldTaskPeriod
     * @return the updated period
     * @throws IllegalValueException if the command arguments are invalid
     */
    private Period createNewPeriodFromCommandArguments(Period oldTaskPeriod) throws IllegalValueException {
        if (this.updatedStartTime == null && oldTaskPeriod.getStartTime() == null) {
            throw new IllegalValueException(MESSAGE_PERIOD_NEED_BOTH_START_AND_END_TIME);
        }
        if (this.updatedEndTime == null && oldTaskPeriod.getEndTime() == null) {
            throw new IllegalValueException(MESSAGE_PERIOD_NEED_BOTH_START_AND_END_TIME);
        }
        
        String prettyTimeDatesString = "";
        int startDateIndex = 0;
        int endDateIndex = 1;
        
        if (this.updatedStartTime != null && this.updatedEndTime != null) {
            prettyTimeDatesString = this.updatedStartTime + " and " + this.updatedEndTime;
            
        } else if (this.updatedStartTime != null) {
            // must do it the other way round, otherwise Pretty Time will get confused :(
            prettyTimeDatesString = CommandHelper.convertDateToPrettyTimeParserFriendlyString(oldTaskPeriod.getEndTime()) +
                    " and " + this.updatedStartTime;            
            startDateIndex = 1;
            endDateIndex = 0;
            
        } else if (this.updatedEndTime != null) {
            prettyTimeDatesString = CommandHelper.convertDateToPrettyTimeParserFriendlyString(oldTaskPeriod.getStartTime()) +
                    " and " + this.updatedEndTime;
        }

        List<Date> finalDateOutput = CommandHelper.convertStringToMultipleDates(prettyTimeDatesString);
        if (finalDateOutput.size() == 2) {
            return new Period(finalDateOutput.get(startDateIndex), finalDateOutput.get(endDateIndex));
        } else {
            throw new IllegalValueException(MESSAGE_PERIOD_NEED_BOTH_START_AND_END_TIME);
        }
    }
}
