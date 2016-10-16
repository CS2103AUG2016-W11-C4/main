package teamfour.tasc.logic.commands;

/**
 * Switches to a new tasklist.
 */
public class SwitchlistCommand extends Command {

    public static final String COMMAND_WORD = "switchlist";

    public static final String MESSAGE_USAGE = 
            COMMAND_WORD + ": Switches to another tasklist. If it does not exist, creates a new file. \n"
            + "Parameters: FILENAME\n"
            + "Example: " + COMMAND_WORD
            + " work";

    
    public static final String MESSAGE_SUCCESS = 
            "Switched to tasklist: %1$s ";
    public static final String MESSAGE_FILE_OPERATION_FAILURE = 
            "Error occured while operating data. ";
    
    private final String filename;

    /**
     * Relocate Command for changing storage path to new directory.
     */
    public SwitchlistCommand(String filename) {
        this.filename = filename;
    }

    @Override
    public CommandResult execute() {
        assert model != null;
//        try {
            return new CommandResult(String.format(MESSAGE_SUCCESS, 
                    filename));
//        } catch (IOException | JAXBException e) {
//            return new CommandResult(MESSAGE_FILE_OPERATION_FAILURE);
//        }
    }

    @Override
    public boolean canUndo() {
        return false;
    }

    @Override
    public CommandResult executeUndo() {
        return null;
    }

}
