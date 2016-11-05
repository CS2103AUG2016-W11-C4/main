package teamfour.tasc.logic.commands;

import teamfour.tasc.commons.core.EventsCenter;
import teamfour.tasc.commons.core.Messages;
import teamfour.tasc.commons.events.ui.JumpToListRequestEvent;
import teamfour.tasc.logic.keyword.SelectCommandKeyword;

/**
 * Selects a task identified using it's last displayed index
 */
//@@author A0127014
public class SelectCommand extends Command {

    private static final int SELECT_LAST_TARGET_INDEX = -1;

    public static final String COMMAND_WORD = SelectCommandKeyword.keyword;

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Selects the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SELECT_TASK_SUCCESS = "Selected Task: %1$s";
    public static final String MESSAGE_SELECT_EMPTY_LIST = "Can't select from an empty list";
    private static final String VALID_INDEX_RANGE_START = "Valid index range: 1 to ";

    private final int targetIndex;

    public SelectCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }
    /**
     * Executes the SelectCommand, and returns a CommandResult
     */
    @Override
    public CommandResult execute() {
        int lastShownListSize = model.getFilteredTaskList().size();
        if (lastShownListSize < 1) {
            return selectTaskFromEmptyList();
        }
        if (targetIndex == SELECT_LAST_TARGET_INDEX) {
            return selectTaskSuccess(lastShownListSize);
        }
        if (lastShownListSize < targetIndex) {
            return selectTaskWithInvalidIndex(lastShownListSize);
        }
        return selectTaskSuccess(targetIndex);
    }

    private CommandResult selectTaskFromEmptyList() {
        indicateAttemptToExecuteIncorrectCommand();
        return new CommandResult(MESSAGE_SELECT_EMPTY_LIST);
    }

    private CommandResult selectTaskWithInvalidIndex(int lastShownListSize) {
        indicateAttemptToExecuteIncorrectCommand();
        return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX + "\n"
                    + (VALID_INDEX_RANGE_START + lastShownListSize));
    }

    /**
     * Posts an event to select the task specified by target
     *
     * @param target    Index of task to select
     * @return          CommandResult for successful execution of SelectCommand
     */
    private CommandResult selectTaskSuccess(int target) {
        EventsCenter.getInstance().post(new JumpToListRequestEvent(target - 1));
        return new CommandResult(String.format(MESSAGE_SELECT_TASK_SUCCESS, target));
    }

    @Override
    public boolean canUndo() {
        return false;
    }

}
