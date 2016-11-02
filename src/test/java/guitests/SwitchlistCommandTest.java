package guitests;

import static org.junit.Assert.*;
import static teamfour.tasc.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import teamfour.tasc.logic.commands.SwitchlistCommand;

public class SwitchlistCommandTest extends AddressBookGuiTest{

    @Test
    public void switchlist_validInput_success() {

        String testFilename = "validName";
        commandBox.runCommand("switchlist " + testFilename);
        assertResultMessage(String.format(SwitchlistCommand.MESSAGE_SUCCESS, testFilename));

        testFilename = "validNameWithNumbers123";
        commandBox.runCommand("switchlist " + testFilename);
        assertResultMessage(String.format(SwitchlistCommand.MESSAGE_SUCCESS, testFilename));

        testFilename = "valid name with spaces and numbers123";
        commandBox.runCommand("switchlist " + testFilename);
        assertResultMessage(String.format(SwitchlistCommand.MESSAGE_SUCCESS, testFilename));

    }

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
}
