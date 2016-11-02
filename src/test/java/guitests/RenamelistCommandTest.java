package guitests;

import static org.junit.Assert.*;
import static teamfour.tasc.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import teamfour.tasc.logic.commands.RenamelistCommand;
import teamfour.tasc.logic.commands.UndoCommand;

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
    public void renamelist_attemptToUndo_failure() {

        String testFilename = "validName";
        commandBox.runCommand("renamelist " + testFilename);
        assertResultMessage(String.format(RenamelistCommand.MESSAGE_SUCCESS, testFilename));

        commandBox.runCommand("undo");
        assertResultMessage(UndoCommand.MESSAGE_NO_PAST_COMMAND_TO_UNDO);

    }
}
