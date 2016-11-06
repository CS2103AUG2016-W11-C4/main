//@@author A0148096W

package teamfour.tasc.logic.commands;

import teamfour.tasc.commons.exceptions.IllegalValueException;
import teamfour.tasc.logic.keyword.UndoCommandKeyword;

/**
 * Undo the last (n) commands.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = UndoCommandKeyword.keyword;

    public static final String MESSAGE_USAGE = COMMAND_WORD 
            + ": Undo the last command(s). "
            + "Parameters: [Number of steps]\n"
            + "Example: " + COMMAND_WORD + " 5";

    public static final String MESSAGE_SUCCESS = "Last %1$s undone.";
    public static final String MESSAGE_NO_PAST_COMMAND_TO_UNDO = 
            "There is no past command to undo.";

    private final int numCommandsToBeUndone;
    
    /**
     * Default behavior of UndoCommand, undoes 1 command
     */
    public UndoCommand() throws IllegalValueException {
        this(1);
    }

    /**
     * Add Command for floating tasks
     * Convenience constructor using raw values.
     * @throws IllegalValueException if numCommandsToBeUndone is < 1
     */
    public UndoCommand(int numCommandsToBeUndone) throws IllegalValueException {
        if (numCommandsToBeUndone < 1) {
            throw new IllegalValueException("Number of undo must be positive.");
        }
        this.numCommandsToBeUndone = numCommandsToBeUndone;
    }
    
    private String createUndoResultMessage(int numUndone) {
        String resultMessage = MESSAGE_NO_PAST_COMMAND_TO_UNDO;
        if (numUndone == 1) {
            resultMessage = String.format(MESSAGE_SUCCESS, "command");
        } else if (numUndone > 1) {
            resultMessage = String.format(MESSAGE_SUCCESS, numUndone + " commands");
        }
        return resultMessage;
    }

    @Override
    public CommandResult execute() {
        assert model != null;
        
        int numUndone = model.undoTaskListHistory(numCommandsToBeUndone);
        String resultMessage = createUndoResultMessage(numUndone);
        return new CommandResult(resultMessage);
    }

    @Override
    public boolean canUndo() {
        return false;
    }

}
