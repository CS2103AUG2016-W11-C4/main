//@@author A0147971U
package guitests;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

import teamfour.tasc.logic.commands.RedoCommand;
import teamfour.tasc.testutil.TestTask;
import teamfour.tasc.testutil.TypicalTestTasks;

public class RedoCommandTest extends TaskListGuiTest {
    
    public void prepare() {
        commandBox.runCommand("add \"redo test case 1\"");
        commandBox.runCommand("add \"redo test case 2\"");
        commandBox.runCommand("add \"redo test case 3\"");
    }
    
    /* Equivalence Partitions:
     * - redo (no argument)
     *     - redo 1 if has available history
     *     - unchanged if no available history
     *     - redoable command history is reset if 
     *       undoable commands are entered after undo
     *     
     * - redo (positive integer)
     *     - redo all history if more than available history
     *     - redo count if less than or equal to available history
     *     - unchanged if no available history
     *     
     * - redo (illegal arguments)
     *     - nonpositive integer: command fails, error message shown
     *     - not an integer: command fails, error message shown
     *     
     * Boundary value analysis:
     *     - For 0 history: test with 0, 1
     *     - For 3 history: test with 3, 4
     */
    
    //---------------- redo (no argument) ----------------------

    private void setupRedoPreparation() {
        commandBox.runCommand("list all");
        commandBox.runCommand("delete 1");
        commandBox.runCommand("delete 1");
        commandBox.runCommand("delete 1");
        commandBox.runCommand("undo 3");
    }
    
    @Test
    public void redo_noArg_hasThreeHistory_redoOne() {
        commandBox.runCommand("delete 1");
        setupRedoPreparation();
        assertRedoResult("redo", createRedoSuccessResultMessage(1), 
                TypicalTestTasks.signUpForYoga,
                TypicalTestTasks.buyBirthdayGift, TypicalTestTasks.researchWhales, 
                TypicalTestTasks.learnVim, TypicalTestTasks.developerMeeting);
    }
    
    @Test
    public void redo_noArg_hasZeroHistory_remainsUnchanged() {
        commandBox.runCommand("list all");
        commandBox.runCommand("delete 1");
        assertRedoResult("redo", RedoCommand.MESSAGE_NO_PAST_COMMAND_TO_REDO,
                TypicalTestTasks.submitProgressReport, TypicalTestTasks.signUpForYoga,
                TypicalTestTasks.buyBirthdayGift, TypicalTestTasks.researchWhales, 
                TypicalTestTasks.learnVim, TypicalTestTasks.developerMeeting);
    }
    
    @Test
    public void redo_noArg_resetHistory_remainsUnchanged() {
        commandBox.runCommand("list all");
        commandBox.runCommand("delete 1");
        commandBox.runCommand("undo");
        commandBox.runCommand("delete 1");
        assertRedoResult("redo", RedoCommand.MESSAGE_NO_PAST_COMMAND_TO_REDO,
                TypicalTestTasks.submitProgressReport, TypicalTestTasks.signUpForYoga,
                TypicalTestTasks.buyBirthdayGift, TypicalTestTasks.researchWhales, 
                TypicalTestTasks.learnVim, TypicalTestTasks.developerMeeting);
    }
    
    //---------------- redo (positive integer) ----------------------
    
    @Test
    public void redo_four_hasThreeHistory_redoAll() {
        setupRedoPreparation();
        assertRedoResult("redo 4", createRedoSuccessResultMessage(3), 
                TypicalTestTasks.buyBirthdayGift, TypicalTestTasks.researchWhales, 
                TypicalTestTasks.learnVim, TypicalTestTasks.developerMeeting);
    }
   
    @Test
    public void redo_three_hasThreeHistory_redoThree() {
        setupRedoPreparation();
        assertRedoResult("redo 3", createRedoSuccessResultMessage(3), 
                TypicalTestTasks.buyBirthdayGift, TypicalTestTasks.researchWhales, 
                TypicalTestTasks.learnVim, TypicalTestTasks.developerMeeting);
    }
    
    @Test
    public void redo_three_hasThreeHistory_redoOneByOneUntilOriginal() {
        setupRedoPreparation();
        assertRedoResult("redo 1", createRedoSuccessResultMessage(1), 
                TypicalTestTasks.submitProgressReport, TypicalTestTasks.signUpForYoga,
                TypicalTestTasks.buyBirthdayGift, TypicalTestTasks.researchWhales, 
                TypicalTestTasks.learnVim, TypicalTestTasks.developerMeeting);
        assertRedoResult("redo 1", createRedoSuccessResultMessage(1), 
                TypicalTestTasks.signUpForYoga,
                TypicalTestTasks.buyBirthdayGift, TypicalTestTasks.researchWhales, 
                TypicalTestTasks.learnVim, TypicalTestTasks.developerMeeting);
        assertRedoResult("redo 1", createRedoSuccessResultMessage(1),
                TypicalTestTasks.buyBirthdayGift, TypicalTestTasks.researchWhales, 
                TypicalTestTasks.learnVim, TypicalTestTasks.developerMeeting);
        assertRedoResult("redo 1", RedoCommand.MESSAGE_NO_PAST_COMMAND_TO_REDO,
                TypicalTestTasks.buyBirthdayGift, TypicalTestTasks.researchWhales, 
                TypicalTestTasks.learnVim, TypicalTestTasks.developerMeeting);
    }
    
    @Test
    public void redo_three_hasZeroHistory_remainsUnchanged() {
        commandBox.runCommand("list all");
        assertRedoResult("redo 3", RedoCommand.MESSAGE_NO_PAST_COMMAND_TO_REDO,
                TypicalTestTasks.submitPrototype,
                TypicalTestTasks.submitProgressReport, TypicalTestTasks.signUpForYoga,
                TypicalTestTasks.buyBirthdayGift, TypicalTestTasks.researchWhales, 
                TypicalTestTasks.learnVim, TypicalTestTasks.developerMeeting);
    }
    
    //---------------- redo (illegal arguments) ----------------------
    
    @Test
    public void redo_nonpositiveInteger_hasThreeHistory_remainsUnchanged_errorMessageShown() {
        setupRedoPreparation();
        assertRedoResult("redo -3", "Invalid command format! \n" 
                                    + RedoCommand.MESSAGE_USAGE,
                                    TypicalTestTasks.submitPrototype, 
                                    TypicalTestTasks.submitProgressReport, TypicalTestTasks.signUpForYoga,
                                    TypicalTestTasks.buyBirthdayGift, TypicalTestTasks.researchWhales, 
                                    TypicalTestTasks.learnVim, TypicalTestTasks.developerMeeting);
    }
    
    @Test
    public void redo_notAnInteger_hasThreeHistory_remainsUnchanged_errorMessageShown() {
        setupRedoPreparation();
        assertRedoResult("redo string", "Invalid command format! \n" 
                                        + RedoCommand.MESSAGE_USAGE,
                                        TypicalTestTasks.submitPrototype, 
                                        TypicalTestTasks.submitProgressReport, TypicalTestTasks.signUpForYoga,
                                        TypicalTestTasks.buyBirthdayGift, TypicalTestTasks.researchWhales, 
                                        TypicalTestTasks.learnVim, TypicalTestTasks.developerMeeting);
    }
    
    //---------------- Utility methods ----------------------
    
    private void assertRedoResult(String command, String expectedMessage, TestTask... expectedHits) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(expectedMessage);
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }
    
    private String createRedoSuccessResultMessage(int numCommandsRedone) {
        return String.format(RedoCommand.MESSAGE_SUCCESS, 
                numCommandsRedone == 1 ? 
                "command" : numCommandsRedone + " commands");
    }
}
