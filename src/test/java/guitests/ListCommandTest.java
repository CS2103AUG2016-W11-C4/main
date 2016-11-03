//@@author A0148096W

package guitests;

import org.junit.Test;

import teamfour.tasc.testutil.TestTask;
import teamfour.tasc.testutil.TestUtil;
import teamfour.tasc.testutil.TypicalTestTasks;

import static org.junit.Assert.assertTrue;

import org.junit.Before;

public class ListCommandTest extends TaskListGuiTest {

    private TestTask[] currentList;
    
    @Before
    public void setUp() {
        currentList = td.getTypicalTasks();
    }
    
    /*
     * - The test methods test one argument type at a time.
     * - Then tests combined arguments using as few tests as possible.
     */
    
    //---------------- Tests individual arguments ----------------------
    
    @Test
    public void list_noParameter_showsUncompletedTasks() {
        currentList = TestUtil.removeTasksFromList(currentList,
                TypicalTestTasks.researchWhales);
        assertListResult("list", currentList);
    }

    @Test
    public void list_noParameterOnEmptyList_showsEmptyList(){
        commandBox.runCommand("clear");
        currentList = TestUtil.removeTasksFromList(currentList, 
                TypicalTestTasks.submitPrototype, 
                TypicalTestTasks.submitProgressReport,  
                TypicalTestTasks.signUpForYoga, 
                TypicalTestTasks.buyBirthdayGift, 
                TypicalTestTasks.researchWhales, 
                TypicalTestTasks.learnVim, 
                TypicalTestTasks.developerMeeting);
        assertListResult("list", currentList);
    }
    
    @Test
    public void list_typeAll_showsAllTasks() {
        assertListResult("list all", currentList);
    }
    
    @Test
    public void list_typeOverdue_showsOverdueTasks() {
        currentList = TestUtil.removeTasksFromList(currentList, 
                TypicalTestTasks.submitPrototype, 
                TypicalTestTasks.signUpForYoga, 
                TypicalTestTasks.buyBirthdayGift, 
                TypicalTestTasks.researchWhales, 
                TypicalTestTasks.learnVim, 
                TypicalTestTasks.developerMeeting);
        assertListResult("list overdue", currentList);
    }
    
    @Test
    public void list_typeRecurring_showsRecurringTasks() {
        currentList = TestUtil.removeTasksFromList(currentList, 
                TypicalTestTasks.submitPrototype, 
                TypicalTestTasks.submitProgressReport,  
                TypicalTestTasks.signUpForYoga, 
                TypicalTestTasks.buyBirthdayGift, 
                TypicalTestTasks.researchWhales);
        assertListResult("list recurring", currentList);
    }

    @Test
    public void list_typeUncompleted_showsUncompletedTasks() {
        currentList = TestUtil.removeTasksFromList(currentList, 
                TypicalTestTasks.researchWhales);
        assertListResult("list uncompleted", currentList);
    }
    
    @Test
    public void list_typeCompleted_showsCompletedTasks() {
        currentList = TestUtil.removeTasksFromList(currentList, 
                TypicalTestTasks.submitPrototype, 
                TypicalTestTasks.submitProgressReport,  
                TypicalTestTasks.signUpForYoga, 
                TypicalTestTasks.buyBirthdayGift, 
                TypicalTestTasks.learnVim, 
                TypicalTestTasks.developerMeeting);
        assertListResult("list completed", currentList);
    }
    
    @Test
    public void list_typeTasks_showsTasksWithoutPeriod() {
        currentList = TestUtil.removeTasksFromList(currentList, 
                TypicalTestTasks.signUpForYoga, 
                TypicalTestTasks.researchWhales, 
                TypicalTestTasks.learnVim);
        assertListResult("list tasks", currentList);
    }
    
    @Test
    public void list_typeEvents_showsTasksWithPeriod() {
        currentList = TestUtil.removeTasksFromList(currentList, 
                TypicalTestTasks.submitPrototype, 
                TypicalTestTasks.submitProgressReport, 
                TypicalTestTasks.buyBirthdayGift, 
                TypicalTestTasks.developerMeeting);
        assertListResult("list events", currentList);
    }
    
    @Test
    public void list_typeCompletedEvents_showsCompletedTasksWithPeriod() {
        currentList = TestUtil.removeTasksFromList(currentList, 
                TypicalTestTasks.submitPrototype, 
                TypicalTestTasks.submitProgressReport, 
                TypicalTestTasks.signUpForYoga, 
                TypicalTestTasks.buyBirthdayGift,
                TypicalTestTasks.learnVim, 
                TypicalTestTasks.developerMeeting);
        assertListResult("list completed events", currentList);
    }
    
    @Test
    public void list_typeUncompletedEvents_showsUncompletedTasksWithPeriod() {
        currentList = TestUtil.removeTasksFromList(currentList, 
                TypicalTestTasks.submitPrototype, 
                TypicalTestTasks.submitProgressReport, 
                TypicalTestTasks.buyBirthdayGift, 
                TypicalTestTasks.researchWhales, 
                TypicalTestTasks.developerMeeting);
        assertListResult("list events uncompleted", currentList);
    }
    
    @Test
    public void list_deadlineBy12Dec2020_showsTasksWithDeadlineBefore12Dec2020() {
        currentList = TestUtil.removeTasksFromList(currentList, 
                TypicalTestTasks.submitPrototype, 
                TypicalTestTasks.signUpForYoga, 
                TypicalTestTasks.researchWhales, 
                TypicalTestTasks.learnVim, 
                TypicalTestTasks.developerMeeting);
        assertListResult("list by 12 dec 2020", currentList);
    }
    
    @Test
    public void list_startTimeFrom1Jan2022_showsTasksWithPeriodAfter1Jan2022() {
        currentList = TestUtil.removeTasksFromList(currentList, 
                TypicalTestTasks.submitProgressReport, 
                TypicalTestTasks.signUpForYoga, 
                TypicalTestTasks.buyBirthdayGift, 
                TypicalTestTasks.learnVim, 
                TypicalTestTasks.developerMeeting);
        assertListResult("list from 1 jan 2022", currentList);
    }
    
    @Test
    public void list_endTimeTo30Dec2021_showsTasksWithPeriodBefore30Dec2021() {
        assertListResult("list to 30 dec 2021", currentList);
    }
    
    @Test
    public void list_tagsUrgent_showsTasksTaggedUrgent() {
        currentList = TestUtil.removeTasksFromList(currentList, 
                TypicalTestTasks.signUpForYoga, 
                TypicalTestTasks.buyBirthdayGift, 
                TypicalTestTasks.researchWhales, 
                TypicalTestTasks.learnVim, 
                TypicalTestTasks.developerMeeting);
        assertListResult("list tag urgent", currentList);
    }
    
    @Test
    public void list_tagsNoMatches_showsEmptyList() {
        currentList = TestUtil.removeTasksFromList(currentList, 
                TypicalTestTasks.submitPrototype, 
                TypicalTestTasks.submitProgressReport, 
                TypicalTestTasks.signUpForYoga, 
                TypicalTestTasks.buyBirthdayGift, 
                TypicalTestTasks.researchWhales, 
                TypicalTestTasks.learnVim, 
                TypicalTestTasks.developerMeeting);
        assertListResult("list tag thistagdoesnotexist");
    }
    
    @Test
    public void list_sortAToZ_showsSortedAToZ() {
        currentList = TestUtil.replaceTaskFromList(currentList, TypicalTestTasks.developerMeeting, 0);
        currentList = TestUtil.replaceTaskFromList(currentList, TypicalTestTasks.buyBirthdayGift, 1);
        currentList = TestUtil.replaceTaskFromList(currentList, TypicalTestTasks.learnVim, 2);
        currentList = TestUtil.replaceTaskFromList(currentList, TypicalTestTasks.researchWhales, 3);
        currentList = TestUtil.replaceTaskFromList(currentList, TypicalTestTasks.signUpForYoga, 4);
        currentList = TestUtil.replaceTaskFromList(currentList, TypicalTestTasks.submitProgressReport, 5);
        currentList = TestUtil.replaceTaskFromList(currentList, TypicalTestTasks.submitPrototype, 6);
        assertListResult("list all sort a-z", currentList);
    }
    
    @Test
    public void list_sortZToA_showsSortedZToA() {
        currentList = TestUtil.replaceTaskFromList(currentList, TypicalTestTasks.developerMeeting, 6);
        currentList = TestUtil.replaceTaskFromList(currentList, TypicalTestTasks.buyBirthdayGift, 5);
        currentList = TestUtil.replaceTaskFromList(currentList, TypicalTestTasks.learnVim, 4);
        currentList = TestUtil.replaceTaskFromList(currentList, TypicalTestTasks.researchWhales, 3);
        currentList = TestUtil.replaceTaskFromList(currentList, TypicalTestTasks.signUpForYoga, 2);
        currentList = TestUtil.replaceTaskFromList(currentList, TypicalTestTasks.submitProgressReport, 1);
        currentList = TestUtil.replaceTaskFromList(currentList, TypicalTestTasks.submitPrototype, 0);
        assertListResult("list all sort z-a", currentList);
    }
    
    @Test
    public void list_sortEarliestFirst_showsSortedEarliestTasksFirst() {
        currentList = TestUtil.replaceTaskFromList(currentList, TypicalTestTasks.submitPrototype, 0);
        currentList = TestUtil.replaceTaskFromList(currentList, TypicalTestTasks.submitProgressReport, 1);
        currentList = TestUtil.replaceTaskFromList(currentList, TypicalTestTasks.signUpForYoga, 2);
        currentList = TestUtil.replaceTaskFromList(currentList, TypicalTestTasks.buyBirthdayGift, 3);
        currentList = TestUtil.replaceTaskFromList(currentList, TypicalTestTasks.researchWhales, 4);
        currentList = TestUtil.replaceTaskFromList(currentList, TypicalTestTasks.learnVim, 5);
        currentList = TestUtil.replaceTaskFromList(currentList, TypicalTestTasks.developerMeeting, 6);
        assertListResult("list all sort earliest first", currentList);
    }
    
    @Test
    public void list_sortLatestFirst_showsSortedLatestTasksFirst() {
        currentList = TestUtil.replaceTaskFromList(currentList, TypicalTestTasks.submitPrototype, 6);
        currentList = TestUtil.replaceTaskFromList(currentList, TypicalTestTasks.submitProgressReport, 5);
        currentList = TestUtil.replaceTaskFromList(currentList, TypicalTestTasks.signUpForYoga, 4);
        currentList = TestUtil.replaceTaskFromList(currentList, TypicalTestTasks.buyBirthdayGift, 3);
        currentList = TestUtil.replaceTaskFromList(currentList, TypicalTestTasks.researchWhales, 1);
        currentList = TestUtil.replaceTaskFromList(currentList, TypicalTestTasks.learnVim, 2);
        currentList = TestUtil.replaceTaskFromList(currentList, TypicalTestTasks.developerMeeting, 0);
        assertListResult("list all sort latest first", currentList);
    }
    
    @Test
    public void list_sortDefault_showsSortedEarliestTasksFirst() {
        currentList = TestUtil.replaceTaskFromList(currentList, TypicalTestTasks.submitPrototype, 0);
        currentList = TestUtil.replaceTaskFromList(currentList, TypicalTestTasks.submitProgressReport, 1);
        currentList = TestUtil.replaceTaskFromList(currentList, TypicalTestTasks.signUpForYoga, 2);
        currentList = TestUtil.replaceTaskFromList(currentList, TypicalTestTasks.buyBirthdayGift, 3);
        currentList = TestUtil.replaceTaskFromList(currentList, TypicalTestTasks.researchWhales, 4);
        currentList = TestUtil.replaceTaskFromList(currentList, TypicalTestTasks.learnVim, 5);
        currentList = TestUtil.replaceTaskFromList(currentList, TypicalTestTasks.developerMeeting, 6);
        assertListResult("list all sort typeanythinghere", currentList);
    }
    
    //---------------- Tests combined arguments ----------------------
    
    @Test
    public void list_combinedArgs_showsUncompletedTasksWithPeriodWithTagSortedZToA() {
        currentList = TestUtil.removeTasksFromList(currentList, 
                TypicalTestTasks.signUpForYoga, 
                TypicalTestTasks.buyBirthdayGift, 
                TypicalTestTasks.researchWhales, 
                TypicalTestTasks.learnVim, 
                TypicalTestTasks.developerMeeting);
        assertListResult("list uncomplete tasks from 1 jan 1998"
                + " to 1 jan 2020, tag urgent, sort z-a", currentList);
    }
    
    @Test
    public void list_combinedArgs_showsCompletedEventsWithEndTimeSortedEarliestFirst() {
        currentList = TestUtil.removeTasksFromList(currentList, 
                TypicalTestTasks.submitPrototype, 
                TypicalTestTasks.submitProgressReport, 
                TypicalTestTasks.signUpForYoga, 
                TypicalTestTasks.buyBirthdayGift, 
                TypicalTestTasks.learnVim, 
                TypicalTestTasks.developerMeeting);
        assertListResult("list completed events to 18 sep 2024, sort earliest first", 
                currentList);
    }
    
    @Test
    public void list_combinedArgs_showsRecurringTasksWithDeadlineSortedLatestFirst() {
        currentList = TestUtil.removeTasksFromList(currentList, 
                TypicalTestTasks.submitPrototype, 
                TypicalTestTasks.submitProgressReport, 
                TypicalTestTasks.signUpForYoga, 
                TypicalTestTasks.buyBirthdayGift, 
                TypicalTestTasks.researchWhales, 
                TypicalTestTasks.learnVim);
        assertListResult("list recurring by 19 sep 2024, sort latest first", 
                currentList);
    }

    //---------------- Utility methods ----------------------
    
    private void assertListResult(String command, TestTask... expectedHits ) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(expectedHits.length + " tasks listed!");
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }
}
