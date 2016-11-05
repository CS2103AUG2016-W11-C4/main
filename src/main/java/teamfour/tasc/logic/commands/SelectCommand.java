package teamfour.tasc.logic.commands;

import teamfour.tasc.commons.core.EventsCenter;
import teamfour.tasc.commons.core.Messages;
import teamfour.tasc.commons.core.UnmodifiableObservableList;
import teamfour.tasc.commons.events.ui.JumpToListRequestEvent;
import teamfour.tasc.logic.keyword.SelectCommandKeyword;
import teamfour.tasc.model.task.ReadOnlyTask;

/**
 * Selects a task identified using it's last displayed index
 */
public class SelectCommand extends Command {

    private static final int SELECT_LAST_TARGET_INDEX = -1;

    public static final String COMMAND_WORD = SelectCommandKeyword.keyword;

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Selects the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SELECT_TASK_SUCCESS = "Selected Task: %1$s";
    public static final String MESSAGE_SELECT_EMPTY_LIST = "Can't select from an empty list";

    private final int targetIndex;

    public SelectCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() {

        int lastShownListSize = model.getFilteredTaskList().size();
        if (lastShownListSize < 1) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(MESSAGE_SELECT_EMPTY_LIST);
        }
        if (targetIndex == SELECT_LAST_TARGET_INDEX) {
            EventsCenter.getInstance().post(new JumpToListRequestEvent(lastShownListSize - 1));
            return new CommandResult(String.format(MESSAGE_SELECT_TASK_SUCCESS, lastShownListSize));
        }
        if (lastShownListSize < targetIndex) {
            String validIndexRange = "Valid index range: 1 to " + lastShownListSize;
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX + "\n" + validIndexRange);
        }

        EventsCenter.getInstance().post(new JumpToListRequestEvent(targetIndex - 1));
        return new CommandResult(String.format(MESSAGE_SELECT_TASK_SUCCESS, targetIndex));

    }

    @Override
    public boolean canUndo() {
        return false;
    }

}
