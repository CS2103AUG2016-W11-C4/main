//@@author A0148096W

package guitests;

import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.assertTrue;

import teamfour.tasc.logic.commands.UndoCommand;
import teamfour.tasc.testutil.TestTask;
import teamfour.tasc.testutil.TestUtil;
import teamfour.tasc.testutil.TypicalTestTasks;

public class UndoCommandTest extends TaskListGuiTest {
    
    private TestTask[] currentList;
    
    @Before
    public void prepare() {
        currentList = td.getTypicalTasks();
        commandBox.runCommand("list all");
        commandBox.runCommand("delete 1");
        commandBox.runCommand("delete 1");
        commandBox.runCommand("delete 1");
        currentList = TestUtil.removeTasksFromList(currentList, 
                TypicalTestTasks.submitPrototype, 
                TypicalTestTasks.submitProgressReport,  
                TypicalTestTasks.signUpForYoga);
    }
    
    /* Equivalence Partitions:
     * - undo (no argument)
     *     - undo 1 if has available history
     *     - unchanged if no available history
     *     
     * - undo (positive integer)
     *     - undo all history if more than available history
     *     - undo count if less than or equal to available history
     *     - unchanged if no available history
     *     
     * - undo (illegal arguments)
     *     - nonpositive integer: command fails, error message shown
     *     - not an integer: command fails, error message shown
     *     
     * Boundary value analysis:
     *     - For 0 history: test with 0, 1
     *     - For 3 history: test with 3, 4
     */
    
    //---------------- undo (no argument) ----------------------
    
    @Test
    public void undo_noArg_hasThreeHistory_undoOne() {
        currentList = TestUtil.addTasksToList(currentList, 0,
                TypicalTestTasks.signUpForYoga);
        assertUndoResult("undo", createUndoSuccessResultMessage(1), 
                currentList);
    }
    
    @Test
    public void undo_noArg_hasZeroHistory_remainsUnchanged() {
        commandBox.runCommand("undo 3");
        assertUndoResult("undo", UndoCommand.MESSAGE_NO_PAST_COMMAND_TO_UNDO,
                td.getTypicalTasks());
    }
    
    //---------------- undo (positive integer) ----------------------
    
    @Test
    public void undo_four_hasThreeHistory_undoAll() {
        currentList = TestUtil.addTasksToList(currentList, 0, 
                TypicalTestTasks.submitPrototype,
                TypicalTestTasks.submitProgressReport,
                TypicalTestTasks.signUpForYoga);
        assertUndoResult("undo 4", createUndoSuccessResultMessage(3), 
                currentList);
    }
    
    @Test
    public void undo_three_hasThreeHistory_undoThree() {
        currentList = TestUtil.addTasksToList(currentList, 0, 
                TypicalTestTasks.submitPrototype,
                TypicalTestTasks.submitProgressReport,
                TypicalTestTasks.signUpForYoga);
        assertUndoResult("undo 3", createUndoSuccessResultMessage(3), 
                currentList);
    }
    
    @Test
    public void undo_three_hasThreeHistory_undoOneByOneUntilOriginal() {
        currentList = TestUtil.addTasksToList(currentList, 0, 
                TypicalTestTasks.signUpForYoga);
        assertUndoResult("undo 1", createUndoSuccessResultMessage(1), 
                currentList);
        
        currentList = TestUtil.addTasksToList(currentList, 0, 
                TypicalTestTasks.submitProgressReport);
        assertUndoResult("undo 1", createUndoSuccessResultMessage(1), 
                currentList);
        
        currentList = TestUtil.addTasksToList(currentList, 0, 
                TypicalTestTasks.submitPrototype);
        assertUndoResult("undo 1", createUndoSuccessResultMessage(1), 
                currentList);
        
        assertUndoResult("undo 1", UndoCommand.MESSAGE_NO_PAST_COMMAND_TO_UNDO,
                currentList);
    }
    
    @Test
    public void undo_three_hasZeroHistory_remainsUnchanged() {
        commandBox.runCommand("undo 3");
        assertUndoResult("undo 3", UndoCommand.MESSAGE_NO_PAST_COMMAND_TO_UNDO,
                td.getTypicalTasks());
    }
    
    //---------------- undo (illegal arguments) ----------------------
    
    @Test
    public void undo_nonpositiveInteger_hasThreeHistory_remainsUnchanged_errorMessageShown() {
        assertUndoResult("undo -3", "Invalid command format! \n"  + UndoCommand.MESSAGE_USAGE,
                currentList);
    }
    
    @Test
    public void undo_notAnInteger_hasThreeHistory_remainsUnchanged_errorMessageShown() {
        assertUndoResult("undo string", "Invalid command format! \n"  + UndoCommand.MESSAGE_USAGE,
                currentList);
    }
    
    //---------------- Utility methods ----------------------
    
    private void assertUndoResult(String command, String expectedMessage, TestTask... expectedHits) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(expectedMessage);
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }
    
    private String createUndoSuccessResultMessage(int numCommandsUndone) {
        return String.format(UndoCommand.MESSAGE_SUCCESS, 
                numCommandsUndone == 1 ? 
                "command" : numCommandsUndone + " commands");
    }
}
