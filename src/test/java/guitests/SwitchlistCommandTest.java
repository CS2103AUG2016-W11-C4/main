//@@author A0127014W
package guitests;

import static teamfour.tasc.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import teamfour.tasc.logic.commands.SwitchlistCommand;

public class SwitchlistCommandTest extends TaskListGuiTest{

    @Test
    public void switchlist_invalidInput_failure() {

        String testFilename = "invalidNameWithSymbols@#$";
        commandBox.runCommand("switchlist " + testFilename);
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SwitchlistCommand.MESSAGE_USAGE));

        //empty name
        testFilename = "";
        commandBox.runCommand("switchlist " + testFilename);
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SwitchlistCommand.MESSAGE_USAGE));

        //name with only spaces
        testFilename = "       ";
        commandBox.runCommand("switchlist " + testFilename);
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SwitchlistCommand.MESSAGE_USAGE));
    }
    
    @Test
    public void switchlist_validInput_successMessage() {
        String newListFilename = "taskList2";
        commandBox.runCommand("switchlist " + newListFilename);
        assertResultMessage(String.format(SwitchlistCommand.MESSAGE_SUCCESS, newListFilename));
    }
}
