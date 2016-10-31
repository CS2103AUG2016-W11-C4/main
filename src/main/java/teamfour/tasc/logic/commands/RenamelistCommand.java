//@@author A0147971U
package teamfour.tasc.logic.commands;

import teamfour.tasc.commons.core.EventsCenter;
import teamfour.tasc.commons.events.storage.TaskListRenamedEvent;
import teamfour.tasc.logic.keyword.RenameListCommandKeyword;

/**
 * Renames the current tasklist.
 */
public class RenamelistCommand extends Command {

    public static final String COMMAND_WORD = RenameListCommandKeyword.keyword;

    public static final String MESSAGE_USAGE = 
            COMMAND_WORD + ": Renames the current tasklist. \n"
            + "Parameters: FILENAME\n"
            + "Example: " + COMMAND_WORD
            + " life";

    
    public static final String MESSAGE_SUCCESS = 
            "Successfully renamed to: %1$s ";
    public static final String MESSAGE_FILE_OPERATION_FAILURE = 
            "Error occured while operating data. ";
    
    private final String newFilename;

    /**
     * Renamelist command for renaming the current list.
     */
    public RenamelistCommand(String newFilename) {
        this.newFilename = newFilename;
    }

    @Override
    public CommandResult execute() {
        assert model != null;
        EventsCenter.getInstance().post(new TaskListRenamedEvent(this.newFilename));
        return new CommandResult(String.format(MESSAGE_SUCCESS, newFilename));
    }

    @Override
    public boolean canUndo() {
        return false;
    }

}
