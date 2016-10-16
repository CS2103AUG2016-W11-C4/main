package guitests;

import org.junit.Test;

import teamfour.tasc.logic.commands.ShowCommand;
import teamfour.tasc.testutil.TestTask;

import static org.junit.Assert.assertTrue;
import static teamfour.tasc.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

public class ShowCommandTest extends AddressBookGuiTest {

    @Test
    public void show_invalidCommand_fail() {
        commandBox.runCommand("show");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ShowCommand.MESSAGE_USAGE));
    }

    @Test
    public void show_emptyList_noResults(){
        commandBox.runCommand("clear");
        assertListResult("show tasks"); //no results
    }
    
    @Test
    public void list_type_overdue() {
        assertListResult("show overdue");
    }
    
    @Test
    public void list_type_recurring() {
        assertListResult("show recurring", td.developerMeeting,
                td.learnVim);
    }

    @Test
    public void show_type_uncompleted() {
        assertListResult("show uncompleted", td.submitPrototype, 
                td.submitProgressReport, td.developerMeeting,
                td.learnVim, td.buyBirthdayGift, td.signUpForYoga);
    }
    
    @Test
    public void show_type_completed() {
        assertListResult("show completed", td.researchWhales);
    }
    
    @Test
    public void show_type_tasks() {
        assertListResult("show tasks", td.submitPrototype, 
                td.submitProgressReport, td.developerMeeting,
                td.buyBirthdayGift);
    }
    
    @Test
    public void show_type_events() {
        assertListResult("show events", td.researchWhales,
                td.learnVim, td.signUpForYoga);
    }
    
    @Test
    public void show_type_completedEvents() {
        assertListResult("show completed events", td.researchWhales);
    }
    
    @Test
    public void show_type_uncompletedEvents() {
        assertListResult("show events uncompleted", td.learnVim, 
                td.signUpForYoga);
    }
    
    @Test
    public void show_date_on1Jan2018() {
        assertListResult("show on 1 jan 2018", td.researchWhales);
    }
    
    @Test
    public void show_deadline_byYear2016() {
        assertListResult("show by 12 dec 2016", td.submitProgressReport,
                td.buyBirthdayGift);
    }
    
    @Test
    public void show_startTime_fromYear2018() {
        assertListResult("show from 1 jan 2018", td.submitPrototype, 
                td.researchWhales);
    }
    
    @Test
    public void show_endTime_toYear2017() {
        assertListResult("show to 30 dec 2017", td.submitPrototype,
                td.submitProgressReport, td.developerMeeting,
                td.researchWhales, td.learnVim,
                td.buyBirthdayGift, td.signUpForYoga);
    }
    
    @Test
    public void show_tags_withMatches() {
        assertListResult("show tag urgent", td.submitPrototype, 
                td.submitProgressReport);
    }
    
    @Test
    public void show_tags_noMatches() {
        assertListResult("show tag thistagdoesnotexist");
    }
    
    @Test
    public void show_allParameters() {
        assertListResult("show uncomplete tasks from 1 jan 1998"
                + " to 1 jan 2020, tag urgent", td.submitPrototype,
                td.submitProgressReport);
    }

    private void assertListResult(String command, TestTask... expectedHits ) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(expectedHits.length + " tasks listed!");
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }
}
