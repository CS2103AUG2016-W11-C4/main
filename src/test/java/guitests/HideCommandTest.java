//@@author A0148096W

package guitests;

import org.junit.Test;

import teamfour.tasc.logic.commands.HideCommand;
import teamfour.tasc.testutil.TestTask;

import static org.junit.Assert.assertTrue;
import static teamfour.tasc.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

public class HideCommandTest extends AddressBookGuiTest {

    /*
     * - The test methods test one argument type at a time.
     * - Then tests combined arguments and continuous executions of hide command.
     */
    
    //---------------- Tests individual arguments ----------------------
    
    @Test
    public void hide_invalidCommand_showsUsageMessage() {
        commandBox.runCommand("hide");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HideCommand.MESSAGE_USAGE));
    }

    @Test
    public void hide_emptyList_showsNonEmptyList(){
        commandBox.runCommand("clear");
        assertListResult("hide tasks"); //no results
    }
    
    @Test
    public void hide_type_overdue_showsNonEmptyList() {
        assertListResult("hide overdue", td.submitPrototype, 
                td.developerMeeting,
                td.researchWhales, td.learnVim, 
                td.buyBirthdayGift, td.signUpForYoga);
    }
    
    @Test
    public void hide_type_recurring_showsNonEmptyList() {
        assertListResult("hide recurring", td.submitPrototype,
                td.submitProgressReport, td.researchWhales,
                td.buyBirthdayGift, td.signUpForYoga);
    }

    @Test
    public void hide_type_completed_showsNonEmptyList() {
        assertListResult("hide completed", td.submitPrototype, 
                td.submitProgressReport, td.developerMeeting,
                td.learnVim, td.buyBirthdayGift, td.signUpForYoga);
    }
    
    @Test
    public void hide_type_uncompleted_showsNonEmptyList() {
        assertListResult("hide uncompleted", td.researchWhales);
    }
    
    @Test
    public void hide_type_events_showsNonEmptyList() {
        assertListResult("hide events", td.submitPrototype, 
                td.submitProgressReport, td.developerMeeting,
                td.buyBirthdayGift);
    }
    
    @Test
    public void hide_type_tasks_showsNonEmptyList() {
        assertListResult("hide tasks", td.researchWhales,
                td.learnVim, td.signUpForYoga);
    }
    
    @Test
    public void hide_type_uncompletedEvents_showsNonEmptyList() {
        assertListResult("hide uncompleted events", td.submitPrototype,
                td.submitProgressReport, td.developerMeeting, 
                td.researchWhales, td.buyBirthdayGift);
    }
    
    @Test
    public void hide_type_completedEvents_showsNonEmptyList() {
        assertListResult("hide events completed", td.submitPrototype, 
                td.submitProgressReport, td.developerMeeting,
                td.learnVim, td.buyBirthdayGift, td.signUpForYoga);
    }
    
    @Test
    public void hide_date_on1Jan2022_showsNonEmptyList() {
        assertListResult("hide on 1 jan 2022", td.submitPrototype, 
                td.submitProgressReport, td.developerMeeting,
                td.learnVim, td.buyBirthdayGift, td.signUpForYoga);
    }
    
    @Test
    public void hide_deadline_by12Dec2020_showsNonEmptyList() {
        assertListResult("hide by 12 dec 2020", td.submitPrototype, 
                td.developerMeeting, td.researchWhales, 
                td.learnVim, td.signUpForYoga);
    }
    
    @Test
    public void hide_startTime_from1Jan2022_showsNonEmptyList() {
        assertListResult("hide from 1 jan 2022", 
                td.submitProgressReport, td.developerMeeting,
                td.learnVim, td.buyBirthdayGift, td.signUpForYoga);
    }
    
    @Test
    public void hide_endTime_to30Dec2021_showsNonEmptyList() {
        assertListResult("hide to 30 dec 2021");
    }
    
    @Test
    public void hide_tags_withMatches_showsNonEmptyList() {
        assertListResult("hide tag urgent", td.developerMeeting,
                td.researchWhales, td.learnVim, 
                td.buyBirthdayGift, td.signUpForYoga);
    }
    
    @Test
    public void hide_tags_noMatches_showsNonEmptyList() {
        assertListResult("hide tag thistagdoesnotexist", 
                td.submitPrototype, td.submitProgressReport, 
                td.developerMeeting, td.researchWhales,
                td.learnVim, td.buyBirthdayGift, td.signUpForYoga);
    }
    

    //---------------- Tests combined arguments ----------------------
    
    @Test
    public void hide_combinedArgs_showsEmptyList() {
        assertListResult("hide uncomplete tasks, from 1 jan 1998"
                + " to 1 jan 2024, tag urgent");
    }
    
    @Test
    public void hide_continuously_narrowsList_showsNonEmptyListAtStart_showsEmptyListAtEnd() {
        assertListResult("hide tag thistagdoesnotexist", 
                td.submitPrototype, td.submitProgressReport, 
                td.developerMeeting, td.researchWhales,
                td.learnVim, td.buyBirthdayGift, td.signUpForYoga);
        
        assertListResult("hide tag urgent", td.developerMeeting,
                td.researchWhales, td.learnVim, 
                td.buyBirthdayGift, td.signUpForYoga);
        
        assertListResult("hide tasks", td.researchWhales,
                td.learnVim, td.signUpForYoga);
        
        assertListResult("hide uncompleted", td.researchWhales);
        
        assertListResult("hide completed");
    }
    
    //---------------- Utility methods ----------------------

    private void assertListResult(String command, TestTask... expectedHits ) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(expectedHits.length + " tasks listed!");
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }
}
