//@@author A0148096W

package guitests;

import org.junit.Test;

import teamfour.tasc.testutil.TestTask;

import static org.junit.Assert.assertTrue;

public class ListCommandTest extends AddressBookGuiTest {

    /*
     * - The test methods test one argument type at a time.
     * - Then tests combined arguments using as few tests as possible.
     */
    
    //---------------- Tests individual arguments ----------------------
    
    @Test
    public void list_noParameter_showsNonEmptyList() {
        assertListResult("list", td.submitPrototype, 
                td.submitProgressReport, td.signUpForYoga,
                td.buyBirthdayGift, td.learnVim, td.developerMeeting);

        //find after deleting one result
        commandBox.runCommand("delete 1");
        assertListResult("list", 
                td.submitProgressReport, td.signUpForYoga,
                td.buyBirthdayGift, td.learnVim, td.developerMeeting);
    }

    @Test
    public void list_noParameter_showsEmptyList(){
        commandBox.runCommand("clear");
        assertListResult("list"); //no results
    }
    
    @Test
    public void list_type_all_showsAllTasks() {
        assertListResult("list all", td.submitPrototype, 
                td.submitProgressReport, td.developerMeeting,
                td.researchWhales, td.learnVim, 
                td.buyBirthdayGift, td.signUpForYoga);
    }
    
    @Test
    public void list_type_overdue_showsNonEmptyList() {
        assertListResult("list overdue", td.submitProgressReport);
    }
    
    @Test
    public void list_type_recurring_showsNonEmptyList() {
        assertListResult("list recurring", td.developerMeeting,
                td.learnVim);
    }

    @Test
    public void list_type_uncompleted_showsNonEmptyList() {
        assertListResult("list uncompleted", td.submitPrototype, 
                td.submitProgressReport, td.developerMeeting,
                td.learnVim, td.buyBirthdayGift, td.signUpForYoga);
    }
    
    @Test
    public void list_type_completed_showsNonEmptyList() {
        assertListResult("list completed", td.researchWhales);
    }
    
    @Test
    public void list_type_tasks_showsNonEmptyList() {
        assertListResult("list tasks", td.submitPrototype, 
                td.submitProgressReport, td.developerMeeting,
                td.buyBirthdayGift);
    }
    
    @Test
    public void list_type_events_showsNonEmptyList() {
        assertListResult("list events", td.researchWhales,
                td.learnVim, td.signUpForYoga);
    }
    
    @Test
    public void list_type_completedEvents_showsNonEmptyList() {
        assertListResult("list completed events", td.researchWhales);
    }
    
    @Test
    public void list_type_uncompletedEvents_showsNonEmptyList() {
        assertListResult("list events uncompleted", td.learnVim, 
                td.signUpForYoga);
    }
    
    @Test
    public void list_deadline_by12Dec2020_showsNonEmptyList() {
        assertListResult("list by 12 dec 2020", td.submitProgressReport,
                td.buyBirthdayGift);
    }
    
    @Test
    public void list_startTime_from1Jan2022_showsNonEmptyList() {
        assertListResult("list from 1 jan 2022", td.submitPrototype, 
                td.researchWhales);
    }
    
    @Test
    public void list_endTime_to30Dec2021_showsNonEmptyList() {
        assertListResult("list to 30 dec 2021", td.submitPrototype,
                td.submitProgressReport, td.developerMeeting,
                td.researchWhales, td.learnVim,
                td.buyBirthdayGift, td.signUpForYoga);
    }
    
    @Test
    public void list_tags_withMatches_showsNonEmptyList() {
        assertListResult("list tag urgent", td.submitPrototype, 
                td.submitProgressReport);
    }
    
    @Test
    public void list_tags_noMatches_showsEmptyList() {
        assertListResult("list tag thistagdoesnotexist");
    }
    
    @Test
    public void list_sort_a_to_z_showsSortedNonEmptyList() {
        assertListResult("list all sort a-z", td.developerMeeting, 
                td.buyBirthdayGift, td.learnVim,
                td.researchWhales, td.signUpForYoga, 
                td.submitProgressReport, td.submitPrototype);
    }
    
    @Test
    public void list_sort_z_to_a_showsSortedNonEmptyList() {
        assertListResult("list all sort z-a", td.submitPrototype, 
                td.submitProgressReport, td.signUpForYoga,
                td.researchWhales, td.learnVim, 
                td.buyBirthdayGift, td.developerMeeting);
    }
    
    @Test
    public void list_sort_earliestFirst_showsSortedNonEmptyList() {
        assertListResult("list all sort earliest first", 
                td.submitPrototype, td.submitProgressReport, 
                td.signUpForYoga, td.buyBirthdayGift,
                td.researchWhales, td.learnVim, 
                td.developerMeeting);
    }
    
    @Test
    public void list_sort_latestFirst_showsSortedNonEmptyList() {
        assertListResult("list all sort latest first",  
                td.developerMeeting, td.researchWhales,
                td.learnVim, td.buyBirthdayGift, 
                td.signUpForYoga, td.submitProgressReport,
                td.submitPrototype);
    }
    
    @Test
    public void list_sort_default_noSort_showsNonEmptyList() {
        assertListResult("list all sort typeanythinghere", 
                td.submitPrototype, 
                td.submitProgressReport, td.developerMeeting,
                td.researchWhales, td.learnVim, 
                td.buyBirthdayGift, td.signUpForYoga);
    }
    
    //---------------- Tests combined arguments ----------------------
    
    @Test
    public void list_combinedArgs_uncompletedTasks_withPeriod_withTag_sortZToA_showsNonEmptyList() {
        assertListResult("list uncomplete tasks from 1 jan 1998"
                + " to 1 jan 2020, tag urgent, sort z-a", 
                td.submitPrototype, td.submitProgressReport);
    }
    
    @Test
    public void list_combinedArgs_completedEvents_withEndTime_sortEarliestFirst_showsSortedNonEmptyList() {
        assertListResult("list completed events to 18 sep 2024, sort earliest first", 
                td.researchWhales);
    }
    
    @Test
    public void list_combinedArgs_recurringTasks_withDeadline_sortLatestFirst_showsSortedNonEmptyList() {
        assertListResult("list recurring by 19 sep 2024, sort latest first", 
                td.developerMeeting);
    }

    //---------------- Utility methods ----------------------
    
    private void assertListResult(String command, TestTask... expectedHits ) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(expectedHits.length + " tasks listed!");
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }
}
