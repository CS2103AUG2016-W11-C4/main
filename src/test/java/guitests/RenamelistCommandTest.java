package guitests;

import static org.junit.Assert.*;
import static teamfour.tasc.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import teamfour.tasc.logic.commands.RenamelistCommand;

public class RenamelistCommandTest extends AddressBookGuiTest{

    @Test
    public void renamelist_validInput_success() {

        String testFilename = "validName";
        commandBox.runCommand("renamelist " + testFilename);
        assertResultMessage(String.format(RenamelistCommand.MESSAGE_SUCCESS, testFilename));

        testFilename = "validNameWithNumbers123";
        commandBox.runCommand("renamelist " + testFilename);
        assertResultMessage(String.format(RenamelistCommand.MESSAGE_SUCCESS, testFilename));

        testFilename = "valid name with spaces and numbers";
        commandBox.runCommand("renamelist " + testFilename);
        assertResultMessage(String.format(RenamelistCommand.MESSAGE_SUCCESS, testFilename));

    }

}
