//@@author A0147971U
package teamfour.tasc.logic.commands;

import java.io.File;

import teamfour.tasc.MainApp;
import teamfour.tasc.commons.core.EventsCenter;
import teamfour.tasc.commons.events.storage.FileRelocateEvent;
import teamfour.tasc.logic.keyword.RelocateCommandKeyword;

/**
 * Moves the data storage file to a new directory.
 */
public class RelocateCommand extends Command {

    public static final String COMMAND_WORD = RelocateCommandKeyword.keyword;

    public static final String MESSAGE_USAGE = 
            COMMAND_WORD + ": Designates a new data storage location (relative to current location). \n"
            + "Parameters: [RELATIVE_PATH] (Enter no parameter for relocating to original path)\n"
            + "Example: " + COMMAND_WORD
            + " .." + File.separator + ".." + File.separator + "relative" + File.separator + "path" 
            + File.separator + "to" + File.separator + "storage" + File.separator + "location";

    
    public static final String MESSAGE_SUCCESS = 
            "File Relocated: %1$s.";
    public static final String MESSAGE_UNDO_SUCCESS = 
            "File Relocation cancelled. Data will be stored in %1$s.";
    public static final String MESSAGE_FILE_OPERATION_FAILURE = 
            "Error occured while transfering data. ";
    
    private final String destination;
    
    private boolean isUndoable = false;

    /**
     * Relocate Command for changing storage path to new directory.
     */
    public RelocateCommand(String destination) {
        this.destination = "data"  + File.separator + destination;
    }
    
    /**
     * Relocate Command for changing back storage to original directory.
     * */
    public RelocateCommand() {
        this.destination = "data";
    }

    @Override
    public CommandResult execute() {
        assert model != null;
        EventsCenter.getInstance().post(new FileRelocateEvent(destination));
        isUndoable = true;
        return new CommandResult(String.format(MESSAGE_SUCCESS, destination));
    }

    @Override
    public boolean canUndo() {
        return isUndoable;
    }
}
