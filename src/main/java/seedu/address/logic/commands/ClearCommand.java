package seedu.address.logic.commands;

import seedu.address.model.TaskList;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Tasc has been cleared!";

    public ClearCommand() {}


    @Override
    public CommandResult execute() {
        assert model != null;
        model.resetData(TaskList.getEmptyTaskList());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
