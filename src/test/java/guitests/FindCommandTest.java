package guitests;

import org.junit.Test;

import teamfour.tasc.commons.core.Messages;
import teamfour.tasc.testutil.TestTask;
import teamfour.tasc.testutil.TypicalTestTasks;

import static org.junit.Assert.assertTrue;

public class FindCommandTest extends TaskListGuiTest {

    @Test
    public void find_nonEmptyList() {
        assertFindResult("find DSFKSDKFSDJKFDSKJFKDS"); //no results
        assertFindResult("find Submit", 
                TypicalTestTasks.submitPrototype, 
                TypicalTestTasks.submitProgressReport); //multiple results

        //find after deleting one result
        commandBox.runCommand("delete 1");
        assertFindResult("find Submit",TypicalTestTasks.submitProgressReport);
    }

    @Test
    public void find_emptyList(){
        commandBox.runCommand("clear");
        assertFindResult("find DSFKSDKFSDJKFDSKJFKDS"); //no results
    }

    @Test
    public void find_invalidCommand_fail() {
        commandBox.runCommand("findsubmitreport");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertFindResult(String command, TestTask... expectedHits ) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(expectedHits.length + " tasks listed!");
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }
}
