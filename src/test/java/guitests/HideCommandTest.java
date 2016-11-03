//@@author A0148096W

package guitests;

import org.junit.Before;
import org.junit.Test;

import teamfour.tasc.logic.commands.HideCommand;
import teamfour.tasc.testutil.TestTask;
import teamfour.tasc.testutil.TestUtil;
import teamfour.tasc.testutil.TypicalTestTasks;

import static org.junit.Assert.assertTrue;
import static teamfour.tasc.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

public class HideCommandTest extends TaskListGuiTest {

    private TestTask[] currentList;
    
    @Before
    public void setUp() {
        currentList = td.getTypicalTasks();
        commandBox.runCommand("list all");
    }
    
    /*
     * - The test methods test one argument type at a time.
     * - Then tests combined arguments and continuous executions of hide command.
     */
    
    //---------------- Tests individual arguments ----------------------
    
    @Test
    public void hide_noParameter_showsUsageMessage() {
        commandBox.runCommand("hide");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HideCommand.MESSAGE_USAGE));
    }

    @Test
    public void hide_emptyList_showsEmptyList(){
        commandBox.runCommand("clear");
        currentList = TestUtil.removeTasksFromList(currentList, 
                TypicalTestTasks.submitPrototype, 
                TypicalTestTasks.submitProgressReport,  
                TypicalTestTasks.signUpForYoga, 
                TypicalTestTasks.buyBirthdayGift, 
                TypicalTestTasks.researchWhales, 
                TypicalTestTasks.learnVim, 
                TypicalTestTasks.developerMeeting);
        assertListResult("hide tasks", currentList);
    }
    
    @Test
    public void hide_typeOverdue_showsTasksNotOverdue() {
        currentList = TestUtil.removeTasksFromList(currentList, 
                TypicalTestTasks.submitProgressReport);
        assertListResult("hide overdue", currentList);
    }
    
    @Test
    public void hide_typeRecurring_showsTasksWithNoRecurrence() {
        currentList = TestUtil.removeTasksFromList(currentList, 
                TypicalTestTasks.learnVim, 
                TypicalTestTasks.developerMeeting);
        assertListResult("hide recurring", currentList);
    }

    @Test
    public void hide_typeCompleted_showsUncompletedTasks() {
        currentList = TestUtil.removeTasksFromList(currentList, 
                TypicalTestTasks.researchWhales);
        assertListResult("hide completed", currentList);
    }
    
    @Test
    public void hide_typeUncompleted_showsCompletedTasks() {
        currentList = TestUtil.removeTasksFromList(currentList, 
                TypicalTestTasks.submitPrototype, 
                TypicalTestTasks.submitProgressReport,  
                TypicalTestTasks.signUpForYoga, 
                TypicalTestTasks.buyBirthdayGift, 
                TypicalTestTasks.learnVim, 
                TypicalTestTasks.developerMeeting);
        assertListResult("hide uncompleted", currentList);
    }
    
    @Test
    public void hide_typeEvents_showsTasksWithoutPeriod() {
        currentList = TestUtil.removeTasksFromList(currentList,  
                TypicalTestTasks.signUpForYoga,  
                TypicalTestTasks.researchWhales, 
                TypicalTestTasks.learnVim);
        assertListResult("hide events", currentList);
    }
    
    @Test
    public void hide_typeTasks_showsTasksWithPeriod() {
        currentList = TestUtil.removeTasksFromList(currentList, 
                TypicalTestTasks.submitPrototype, 
                TypicalTestTasks.submitProgressReport, 
                TypicalTestTasks.buyBirthdayGift, 
                TypicalTestTasks.developerMeeting);
        assertListResult("hide tasks", currentList);
    }
    
    @Test
    public void hide_typeUncompletedEvents_showsCompletedTasksOrWithoutPeriod() {
        currentList = TestUtil.removeTasksFromList(currentList, 
                TypicalTestTasks.signUpForYoga, 
                TypicalTestTasks.learnVim);
        assertListResult("hide uncompleted events", currentList);
    }
    
    @Test
    public void hide_typeCompletedEvents_showsUncompletedTasksOrWithoutPeriod() {
        currentList = TestUtil.removeTasksFromList(currentList, 
                TypicalTestTasks.researchWhales);
        assertListResult("hide events completed", currentList);
    }
    
    @Test
    public void hide_dateOn1Jan2022_showsTasksNotOn1Jan2022() {
        currentList = TestUtil.removeTasksFromList(currentList, 
                TypicalTestTasks.researchWhales);
        assertListResult("hide on 1 jan 2022", currentList);
    }
    
    @Test
    public void hide_deadlineBy12Dec2020_showsTasksWithDeadlineAfter12Dec2020() {
        currentList = TestUtil.removeTasksFromList(currentList, 
                TypicalTestTasks.submitProgressReport, 
                TypicalTestTasks.buyBirthdayGift);
        assertListResult("hide by 12 dec 2020", currentList);
    }
    
    @Test
    public void hide_startTimeFrom1Jan2022_showsTasksWithPeriodBefore1Jan2022() {
        currentList = TestUtil.removeTasksFromList(currentList, 
                TypicalTestTasks.submitPrototype, 
                TypicalTestTasks.researchWhales);
        assertListResult("hide from 1 jan 2022", currentList);
    }
    
    @Test
    public void hide_endTimeTo30Dec2025_showsEmptyList() {
        currentList = TestUtil.removeTasksFromList(currentList, 
                TypicalTestTasks.submitPrototype, 
                TypicalTestTasks.submitProgressReport,  
                TypicalTestTasks.signUpForYoga, 
                TypicalTestTasks.buyBirthdayGift, 
                TypicalTestTasks.researchWhales, 
                TypicalTestTasks.learnVim, 
                TypicalTestTasks.developerMeeting);
        assertListResult("hide to 30 dec 2025", currentList);
    }
    
    @Test
    public void hide_tagsUrgent_showsTasksNotTaggedUrgent() {
        currentList = TestUtil.removeTasksFromList(currentList, 
                TypicalTestTasks.submitPrototype, 
                TypicalTestTasks.submitProgressReport);
        assertListResult("hide tag urgent", currentList);
    }
    
    @Test
    public void hide_tagsNoMatches_showsAllTasks() {
        assertListResult("hide tag thistagdoesnotexist", currentList);
    }
    

    //---------------- Tests combined arguments ----------------------
    
    @Test
    public void hide_combinedArgs_showsEmptyList() {
        currentList = TestUtil.removeTasksFromList(currentList, 
                TypicalTestTasks.submitPrototype, 
                TypicalTestTasks.submitProgressReport,  
                TypicalTestTasks.signUpForYoga, 
                TypicalTestTasks.buyBirthdayGift, 
                TypicalTestTasks.researchWhales, 
                TypicalTestTasks.learnVim, 
                TypicalTestTasks.developerMeeting);
        assertListResult("hide uncomplete tasks, from 1 jan 1998"
                + " to 1 jan 2024, tag urgent", currentList);
    }
    
    @Test
    public void hide_continuouslyNarrowsList_showsNonEmptyListAtStartAndEmptyListAtEnd() {
        assertListResult("hide tag thistagdoesnotexist", currentList);
        
        currentList = TestUtil.removeTasksFromList(currentList, 
                TypicalTestTasks.submitPrototype, 
                TypicalTestTasks.submitProgressReport);
        assertListResult("hide tag urgent", currentList);
        
        currentList = TestUtil.removeTasksFromList(currentList, 
                TypicalTestTasks.buyBirthdayGift, 
                TypicalTestTasks.developerMeeting);
        assertListResult("hide tasks", currentList);
        
        currentList = TestUtil.removeTasksFromList(currentList, 
                TypicalTestTasks.learnVim, 
                TypicalTestTasks.signUpForYoga);
        assertListResult("hide uncompleted", currentList);
        
        currentList = TestUtil.removeTasksFromList(currentList, 
                TypicalTestTasks.researchWhales);
        assertListResult("hide completed", currentList);
    }
    
    //---------------- Utility methods ----------------------

    private void assertListResult(String command, TestTask... expectedHits ) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(expectedHits.length + " tasks listed!");
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }
}
