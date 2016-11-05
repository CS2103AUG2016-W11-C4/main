//@@author A0127014W
package guitests;

import static teamfour.tasc.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import teamfour.tasc.logic.commands.RenamelistCommand;

public class RenamelistCommandTest extends TaskListGuiTest{

    @Test
    public void renamelist_invalidInput_failure() {

        String testFilename = "invalidNameWithSymbols@#$";
        commandBox.runCommand("renamelist " + testFilename);
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RenamelistCommand.MESSAGE_USAGE));

        //empty name
        testFilename = "";
        commandBox.runCommand("renamelist " + testFilename);
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RenamelistCommand.MESSAGE_USAGE));

        //name with only spaces
        testFilename = "       ";
        commandBox.runCommand("renamelist " + testFilename);
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RenamelistCommand.MESSAGE_USAGE));

    }
    
    @Test
    public void renamelist_validInput_successMessage() {
        String testFilename = "NewTaskListName";
        commandBox.runCommand("renamelist " + testFilename);
        assertResultMessage(String.format(RenamelistCommand.MESSAGE_SUCCESS, testFilename));
    }

}
