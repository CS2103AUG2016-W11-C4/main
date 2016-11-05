package guitests;

import org.junit.Test;

import teamfour.tasc.model.task.ReadOnlyTask;

import static org.junit.Assert.assertEquals;

public class SelectCommandTest extends TaskListGuiTest {


    @Test
    public void selectTask_nonEmptyList() {

        commandBox.runCommand("list all");

        assertSelectionInvalid(10); //Out of bounds index
        assertNoTaskSelected();

        assertSelectionSuccess(1); //first task in the list
        int taskCount = td.getTypicalTasks().length;
        assertSelectionSuccess(taskCount); //last task in the list
        int middleIndex = taskCount / 2;
        assertSelectionSuccess(middleIndex); //a task in the middle of the list

        assertSelectionInvalid(taskCount + 1); //invalid index
        assertTaskSelected(middleIndex); //assert previous selection remains

        /* Testing other invalid indexes such as -1 should be done when testing the SelectCommand */
    }

    @Test
    public void selectTask_emptyList(){
        commandBox.runCommand("clear");
        assertListSize(0);
        assertSelectionInvalid(1); //invalid index
    }
    //@@author A0127014W

    @Test
    public void selectTask_selectLast_nonEmptyList_success(){
        commandBox.runCommand("select last");
        assertResultMessage("Selected Task: "+ 6);
    }

    @Test
    public void selectTask_selectLast_emptyList_failure(){
        commandBox.runCommand("clear");
        assertListSize(0);
        commandBox.runCommand("select last");
        assertResultMessage("Can't select from an empty list");
    }

    private void assertSelectionInvalid(int index) {
        commandBox.runCommand("select " + index);
        if(taskListPanel.getNumberOfTasks() < 1){
            assertResultMessage("Can't select from an empty list");
        }
        else{
            assertResultMessage("The task index provided is invalid" + "\n" + "Valid index range: 1 to " + taskListPanel.getNumberOfTasks());
        }
    }
    //@@author
    private void assertSelectionSuccess(int index) {
        commandBox.runCommand("select " + index);
        assertResultMessage("Selected Task: "+index);
        assertTaskSelected(index);
    }

    private void assertTaskSelected(int index) {
        assertEquals(taskListPanel.getSelectedTasks().size(), 1);
        ReadOnlyTask selectedTask = taskListPanel.getSelectedTasks().get(0);
        assertEquals(taskListPanel.getTask(index-1), selectedTask);
        //TODO: confirm the correct page is loaded in the Browser Panel
    }

    private void assertNoTaskSelected() {
        assertEquals(taskListPanel.getSelectedTasks().size(), 0);
    }

}
