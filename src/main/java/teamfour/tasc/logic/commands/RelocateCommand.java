//@@author A0147971U
package teamfour.tasc.logic.commands;

import java.io.File;

import teamfour.tasc.commons.core.EventsCenter;
import teamfour.tasc.commons.events.storage.FileRelocateEvent;
import teamfour.tasc.logic.keyword.RelocateCommandKeyword;

/**
 * Moves the data storage file to a new directory.
 */
public class RelocateCommand extends Command {

    public static final String COMMAND_WORD = RelocateCommandKeyword.keyword;

    public static final String MESSAGE_USAGE = 
            COMMAND_WORD + ": Designates a new data storage location. \n"
            + "Parameters: [PATH] (Enter no parameter for relocating to original path)\n"
            + "Example: \n"
            + "Relative: " + COMMAND_WORD + " .." + File.separator + "relative" + File.separator + "path" 
            + File.separator + "to" + File.separator + "storage" + File.separator + "location\n" 
            + "Windows full path: " + COMMAND_WORD + " C:\\full\\path\\to\\destination\n" 
            + "Mac full path: " + COMMAND_WORD + " /Users/your_username/path/to/destination";

    
    public static final String MESSAGE_SUCCESS = 
            "File Relocated: %1$s.";
    public static final String MESSAGE_UNDO_SUCCESS = 
            "File Relocation cancelled. Data will be stored in %1$s.";
    public static final String MESSAGE_FILE_OPERATION_FAILURE = 
            "Error occured while transfering data. ";
    
    private final String destination;

    /**
     * Relocate Command for changing storage path to new directory.
     */
    public RelocateCommand(String destination, boolean isFullPath) {
        if (isFullPath) {
            this.destination = destination;
        } else {
            this.destination = "data"  + File.separator + destination;            
        }
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
        return new CommandResult(String.format(MESSAGE_SUCCESS, destination));
    }

    @Override
    public boolean canUndo() {
        return false;
    }
}
