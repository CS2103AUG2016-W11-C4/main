package seedu.address.testutil;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.TaskList;
import seedu.address.model.task.*;

/**
 *
 */
public class TypicalTestTasks {

    public static TestTask submitPrototype, submitProgressReport, developerMeeting, researchWhales, learnVim,
            buyBirthdayGift, signUpForYoga, attendWorkshop, updateGithubRepo;
    public static Date date1, date2, date3, date4, date5, date6;
    
    public TypicalTestTasks() {
        try {
        	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        	date1 = sdf.parse("23/10/2016 23:00:05");
        	date2 = sdf.parse("25/11/2016 17:32:05");
        	date3 = sdf.parse("01/12/2016 08:56:05");
        	date4 = sdf.parse("14/01/2018 20:50:05");
        	date5 = sdf.parse("02/03/2017 14:20:05");
        	date6 = sdf.parse("03/11/2016 12:04:05");
            submitPrototype =  new TaskBuilder().withName("Submit prototype")
                    .withTags("urgent").withCompleteStatus(false).build();
            submitProgressReport = new TaskBuilder().withCompleteStatus(true).withDeadline(date1)
            		.withName("Submit progress report").withTags("finance", "urgent").build();
            developerMeeting = new TaskBuilder().withName("Attend developer meeting").withDeadline(date5)
            		.withDeadlineRecurrence(Recurrence.Pattern.DAILY, 3).build();
            researchWhales = new TaskBuilder().withName("Research on whales")
            		.withPeriod(date3, date4).withCompleteStatus(true).build();
            learnVim = new TaskBuilder().withName("Learn Vim").withPeriod(date3, date5)
            		.withPeriodRecurrence(Recurrence.Pattern.WEEKLY, 2).build();
            buyBirthdayGift = new TaskBuilder().withName("Buy birthday gift").withDeadline(date2).build();
            signUpForYoga = new TaskBuilder().withName("Sign up for yoga").withCompleteStatus(false)
            		.withPeriod(date6, date5).build();
            attendWorkshop = new TaskBuilder().withName("Attend workshop").build();
            updateGithubRepo = new TaskBuilder().withName("Update GitHub repository").build();
        } catch (IllegalValueException | ParseException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadTaskListWithSampleData(TaskList tl) {

        try {
            tl.addTask(new Task(submitPrototype));
            tl.addTask(new Task(submitProgressReport));
            tl.addTask(new Task(developerMeeting));
            tl.addTask(new Task(researchWhales));
            tl.addTask(new Task(learnVim));
            tl.addTask(new Task(buyBirthdayGift));
            tl.addTask(new Task(signUpForYoga));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            assert false : "not possible";
        }
    }

    public TestTask[] getTypicalTasks() {
        return new TestTask[]{submitPrototype, submitProgressReport, developerMeeting, researchWhales, learnVim, buyBirthdayGift, signUpForYoga};
    }

    public TaskList getTypicalTaskList(){
        TaskList tl = new TaskList();
        loadTaskListWithSampleData(tl);
        return tl;
    }
}
