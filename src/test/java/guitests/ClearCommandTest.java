package guitests;

import org.junit.Test;

import teamfour.tasc.testutil.TypicalTestTasks;

import static org.junit.Assert.assertTrue;

public class ClearCommandTest extends TaskListGuiTest {

    @Test
    public void clear() {

        commandBox.runCommand("list all");

        //verify a non-empty list can be cleared
        assertTrue(taskListPanel.isListMatching(td.getTypicalTasks()));
        assertClearCommandSuccess();

        //verify other commands can work after a clear command
        commandBox.runCommand(TypicalTestTasks.attendWorkshop.getAddCommand());
        assertTrue(taskListPanel.isListMatching(TypicalTestTasks.attendWorkshop));
        commandBox.runCommand("delete 1");
        assertListSize(0);

        //verify clear command works when the list is empty
        assertClearCommandSuccess();
    }

    private void assertClearCommandSuccess() {
        commandBox.runCommand("clear");
        assertListSize(0);
        assertResultMessage("Task list has been cleared!");
    }
}
