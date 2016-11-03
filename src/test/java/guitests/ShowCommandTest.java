//@@author A0148096W

package guitests;

import org.junit.Before;
import org.junit.Test;

import teamfour.tasc.logic.commands.ShowCommand;
import teamfour.tasc.testutil.TestTask;
import teamfour.tasc.testutil.TestUtil;
import teamfour.tasc.testutil.TypicalTestTasks;

import static org.junit.Assert.assertTrue;
import static teamfour.tasc.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

public class ShowCommandTest extends AddressBookGuiTest {

    TestTask[] currentList;
    
    @Before
    public void setUp() {
        currentList = td.getTypicalTasks();
        commandBox.runCommand("list all");
    }
    
    /*
     * - The test methods test one argument type at a time.
     * - Then tests combined arguments and continuous executions of show command.
     */
    
    //---------------- Tests individual arguments ----------------------
    
    @Test
    public void show_invalidCommand_showsUsageMessage() {
        commandBox.runCommand("show");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ShowCommand.MESSAGE_USAGE));
    }

    @Test
    public void show_emptyList_showsEmptyList(){
        commandBox.runCommand("clear");
        currentList = TestUtil.removeTasksFromList(currentList, 
                TypicalTestTasks.submitPrototype, 
                TypicalTestTasks.submitProgressReport,  
                TypicalTestTasks.signUpForYoga, 
                TypicalTestTasks.buyBirthdayGift, 
                TypicalTestTasks.researchWhales, 
                TypicalTestTasks.learnVim, 
                TypicalTestTasks.developerMeeting);
        assertListResult("show tasks");
    }
    
    @Test
    public void show_type_overdue_showsOverdueTasks() {
        currentList = TestUtil.removeTasksFromList(currentList, 
                TypicalTestTasks.submitPrototype, 
                TypicalTestTasks.signUpForYoga, 
                TypicalTestTasks.buyBirthdayGift, 
                TypicalTestTasks.researchWhales, 
                TypicalTestTasks.learnVim, 
                TypicalTestTasks.developerMeeting);
        assertListResult("show overdue", currentList);
    }
    
    @Test
    public void show_type_recurring_showsRecurringTasks() {
        currentList = TestUtil.removeTasksFromList(currentList, 
                TypicalTestTasks.submitPrototype, 
                TypicalTestTasks.submitProgressReport,  
                TypicalTestTasks.signUpForYoga, 
                TypicalTestTasks.buyBirthdayGift, 
                TypicalTestTasks.researchWhales);
        assertListResult("show recurring", currentList);
    }

    @Test
    public void show_type_uncompleted_showsUncompletedTasks() {
        currentList = TestUtil.removeTasksFromList(currentList, 
                TypicalTestTasks.researchWhales);
        assertListResult("show uncompleted", currentList);
    }
    
    @Test
    public void show_type_completed_showsCompletedTasks() {
        currentList = TestUtil.removeTasksFromList(currentList, 
                TypicalTestTasks.submitPrototype, 
                TypicalTestTasks.submitProgressReport,  
                TypicalTestTasks.signUpForYoga, 
                TypicalTestTasks.buyBirthdayGift, 
                TypicalTestTasks.learnVim, 
                TypicalTestTasks.developerMeeting);
        assertListResult("show completed", currentList);
    }
    
    @Test
    public void show_type_tasks_showsTasksWithoutPeriod() {
        currentList = TestUtil.removeTasksFromList(currentList, 
                TypicalTestTasks.signUpForYoga, 
                TypicalTestTasks.researchWhales, 
                TypicalTestTasks.learnVim);
        assertListResult("show tasks", currentList);
    }
    
    @Test
    public void show_type_events_showsTasksWithPeriod() {
        currentList = TestUtil.removeTasksFromList(currentList, 
                TypicalTestTasks.submitPrototype, 
                TypicalTestTasks.submitProgressReport, 
                TypicalTestTasks.buyBirthdayGift, 
                TypicalTestTasks.developerMeeting);
        assertListResult("show events", currentList);
    }
    
    @Test
    public void show_type_completedEvents_showsCompletedTasksWithPeriod() {
        currentList = TestUtil.removeTasksFromList(currentList, 
                TypicalTestTasks.submitPrototype, 
                TypicalTestTasks.submitProgressReport, 
                TypicalTestTasks.signUpForYoga, 
                TypicalTestTasks.buyBirthdayGift,
                TypicalTestTasks.learnVim, 
                TypicalTestTasks.developerMeeting);
        assertListResult("show completed events", currentList);
    }
    
    @Test
    public void show_type_uncompletedEvents_showsUncompletedTasksWithPeriod() {
        currentList = TestUtil.removeTasksFromList(currentList, 
                TypicalTestTasks.submitPrototype, 
                TypicalTestTasks.submitProgressReport, 
                TypicalTestTasks.buyBirthdayGift, 
                TypicalTestTasks.researchWhales, 
                TypicalTestTasks.developerMeeting);
        assertListResult("show events uncompleted", currentList);
    }
    
    @Test
    public void show_date_on1Jan2022_showsTasksOn1Jan2022() {
        currentList = TestUtil.removeTasksFromList(currentList, 
                TypicalTestTasks.submitPrototype, 
                TypicalTestTasks.submitProgressReport,  
                TypicalTestTasks.signUpForYoga, 
                TypicalTestTasks.buyBirthdayGift, 
                TypicalTestTasks.learnVim, 
                TypicalTestTasks.developerMeeting);
        assertListResult("show on 1 jan 2022", currentList);
    }
    
    @Test
    public void show_deadline_by12Dec2020_showsTasksWithDeadlineBefore12Dec2020() {
        currentList = TestUtil.removeTasksFromList(currentList, 
                TypicalTestTasks.submitPrototype, 
                TypicalTestTasks.signUpForYoga, 
                TypicalTestTasks.researchWhales, 
                TypicalTestTasks.learnVim, 
                TypicalTestTasks.developerMeeting);
        assertListResult("show by 12 dec 2020", currentList);
    }
    
    @Test
    public void show_startTime_from1Jan2022_showsTasksWithPeriodAfter1Jan2022() {
        currentList = TestUtil.removeTasksFromList(currentList, 
                TypicalTestTasks.submitProgressReport, 
                TypicalTestTasks.signUpForYoga, 
                TypicalTestTasks.buyBirthdayGift, 
                TypicalTestTasks.learnVim, 
                TypicalTestTasks.developerMeeting);
        assertListResult("show from 1 jan 2022", currentList);
    }
    
    @Test
    public void show_endTime_to30Dec2021_showsTasksWithPeriodBefore30Dec2021() {
        assertListResult("show to 30 dec 2021", currentList);
    }
    
    @Test
    public void show_tags_taggedUrgent_showsTasksTaggedUrgent() {
        currentList = TestUtil.removeTasksFromList(currentList, 
                TypicalTestTasks.signUpForYoga, 
                TypicalTestTasks.buyBirthdayGift, 
                TypicalTestTasks.researchWhales, 
                TypicalTestTasks.learnVim, 
                TypicalTestTasks.developerMeeting);
        assertListResult("show tag urgent", currentList);
    }
    
    @Test
    public void show_tags_noMatches_showsEmptyList() {
        currentList = TestUtil.removeTasksFromList(currentList, 
                TypicalTestTasks.submitPrototype, 
                TypicalTestTasks.submitProgressReport, 
                TypicalTestTasks.signUpForYoga, 
                TypicalTestTasks.buyBirthdayGift, 
                TypicalTestTasks.researchWhales, 
                TypicalTestTasks.learnVim, 
                TypicalTestTasks.developerMeeting);
        assertListResult("show tag thistagdoesnotexist");
    }
    

    //---------------- Tests combined arguments ----------------------
    
    @Test
    public void show_combinedArgs_showsUncompletedTasksFrom1Jan1998To1Jan2024() {
        currentList = TestUtil.removeTasksFromList(currentList, 
                TypicalTestTasks.signUpForYoga, 
                TypicalTestTasks.buyBirthdayGift, 
                TypicalTestTasks.researchWhales, 
                TypicalTestTasks.learnVim, 
                TypicalTestTasks.developerMeeting);
        assertListResult("show uncomplete tasks from 1 jan 1998"
                + " to 1 jan 2024, tag urgent", currentList);
    }
    
    @Test
    public void show_continuously_narrowsList_showsNonEmptyListAtStart_showsEmptyListAtEnd() {
        assertListResult("show to 30 dec 2021", currentList);
        
        currentList = TestUtil.removeTasksFromList(currentList, 
                TypicalTestTasks.researchWhales);
        assertListResult("show uncompleted", currentList);
        
        currentList = TestUtil.removeTasksFromList(currentList, 
                TypicalTestTasks.submitPrototype, 
                TypicalTestTasks.submitProgressReport, 
                TypicalTestTasks.buyBirthdayGift, 
                TypicalTestTasks.developerMeeting);
        assertListResult("show events", currentList);

        currentList = TestUtil.removeTasksFromList(currentList, 
                TypicalTestTasks.signUpForYoga);
        assertListResult("show recurring", currentList);
        
        currentList = TestUtil.removeTasksFromList(currentList, 
                TypicalTestTasks.learnVim);
        assertListResult("show completed", currentList);
    }

    //---------------- Utility methods ----------------------
    
    private void assertListResult(String command, TestTask... expectedHits ) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(expectedHits.length + " tasks listed!");
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }
}
