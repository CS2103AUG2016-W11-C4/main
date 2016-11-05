//@@author A0147971U
package guitests;

import static teamfour.tasc.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.io.File;
import java.util.regex.Matcher;

import org.junit.Test;

import teamfour.tasc.logic.commands.RelocateCommand;

public class RelocateCommandTest extends TaskListGuiTest {
    @Test
    public void invalid_relocate_input() {
        
        // Only in format "relocate/to/new/directory".
        commandBox.runCommand("relocate /new");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RelocateCommand.MESSAGE_USAGE));
        
        commandBox.runCommand("relocate /new/");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RelocateCommand.MESSAGE_USAGE));
        
        commandBox.runCommand("relocate new/");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RelocateCommand.MESSAGE_USAGE));
        
        // Should not append file name at the end.
        commandBox.runCommand("relocate new/tasklist.xml");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RelocateCommand.MESSAGE_USAGE));
    }
    
    @Test
    public void relocate_relativePath_successMessage() {
        String newDestination = "dir1/dir2";
        commandBox.runCommand("relocate " + newDestination);
        assertResultMessage(String.format(RelocateCommand.MESSAGE_SUCCESS, 
                "data" + File.separator + newDestination));
    }
    
    @Test
    public void relocate_absolutePath_successMessage() {
        String newDestination = "/Users/my_username/tasc/test";
        
        String os = System.getProperty("os.name");
        if (os.startsWith("Windows")) {
            newDestination = "C:\\tasc\\test";
        }
        
        commandBox.runCommand("relocate " + newDestination);
        assertResultMessage(String.format(RelocateCommand.MESSAGE_SUCCESS, newDestination));
    }
    
    @Test
    public void relocate_emptyInputDefault_successMessage() {
        commandBox.runCommand("relocate");
        assertResultMessage(String.format(RelocateCommand.MESSAGE_SUCCESS, "data"));
    }
}
