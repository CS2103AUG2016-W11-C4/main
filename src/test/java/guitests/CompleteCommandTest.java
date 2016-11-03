//@@author A0140011L
package guitests;

import static org.junit.Assert.*;
import static teamfour.tasc.logic.commands.CompleteCommand.MESSAGE_COMPLETE_TASK_ALREADY_COMPLETED;
import static teamfour.tasc.logic.commands.CompleteCommand.MESSAGE_COMPLETE_TASK_SUCCESS;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import teamfour.tasc.commons.util.DateUtil;
import teamfour.tasc.testutil.TestClock;
import teamfour.tasc.testutil.TestTask;
import teamfour.tasc.testutil.TestUtil;

public class CompleteCommandTest extends TaskListGuiTest {
  
    @Before
    public void setUp() {
        // set up fake time
        TestClock testClock = new TestClock(new Date(0));
        DateUtil.getInstance().setClock(testClock);
        commandBox.runCommand("list all");
    }
    
    @Test
    public void complete() throws Exception {

        // mark the first in the list as complete
        TestTask[] currentList = td.getTypicalTasks();
        int targetIndex = 1;
        assertCompleteSuccess(targetIndex, currentList);
        currentList = TestUtil.markTaskInListAsComplete(currentList, targetIndex);

        // mark the last in the list as complete
        targetIndex = currentList.length;
        assertCompleteSuccess(targetIndex, currentList);
        currentList = TestUtil.markTaskInListAsComplete(currentList, targetIndex);

        // mark the task in the middle of the list as complete
        targetIndex = currentList.length/2;
        assertCompleteSuccess(targetIndex, currentList);
        currentList = TestUtil.markTaskInListAsComplete(currentList, targetIndex);
     
        // try to remark the same task as complete
        targetIndex = 1;
        assertCompleteFailureBecauseAlreadyMarked(targetIndex, currentList);

        // invalid index
        commandBox.runCommand("complete " + currentList.length + 1);
        assertResultMessage("The task index provided is invalid");
    }
    
    /**
     * Runs the complete command to mark the task at specified index as complete
     * and confirms the result is correct.
     * 
     * @param targetIndexOneIndexed e.g. to complete the first task in the list,
     *            1 should be given as the target index.
     * @param currentList A copy of the current list of tasks (before marking as
     *            complete).
     */
    private void assertCompleteSuccess(int targetIndexOneIndexed, final TestTask[] currentList)
            throws Exception {    
        TestTask[] expectedNewList = TestUtil.markTaskInListAsComplete(currentList, targetIndexOneIndexed);

        commandBox.runCommand("complete " + targetIndexOneIndexed);

        // confirm the list now contains all tasks with the new task marked as
        // completed
        assertTrue(taskListPanel.isListMatching(expectedNewList));

        // confirm the result message is correct
        assertResultMessage(String.format(MESSAGE_COMPLETE_TASK_SUCCESS,
                expectedNewList[targetIndexOneIndexed - 1]));
    }

    /**
     * Runs the complete command to mark the task that is already completed at
     * specified index as complete and confirms the result is correct.
     * 
     * @param targetIndexOneIndexed e.g. to complete the first task in the list,
     *            1 should be given as the target index.
     * @param currentList A copy of the current list of tasks (before marking as
     *            complete).
     */
    private void assertCompleteFailureBecauseAlreadyMarked(int targetIndexOneIndexed,
            final TestTask[] currentList) {
        TestTask taskAlreadyCompleted = currentList[targetIndexOneIndexed - 1];
        assertTrue(taskAlreadyCompleted.getComplete().isCompleted());

        commandBox.runCommand("complete " + targetIndexOneIndexed);

        // confirm the list is still the same
        assertTrue(taskListPanel.isListMatching(currentList));

        // confirm the result message is correct
        assertResultMessage(String.format(MESSAGE_COMPLETE_TASK_ALREADY_COMPLETED, taskAlreadyCompleted));
    }
}
