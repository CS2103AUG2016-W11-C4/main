package teamfour.tasc.model.task;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

import teamfour.tasc.commons.exceptions.IllegalValueException;
import teamfour.tasc.commons.util.DateUtil;
import teamfour.tasc.model.task.Complete;
import teamfour.tasc.model.task.ReadOnlyTask;
import teamfour.tasc.model.task.Task;
import teamfour.tasc.model.task.exceptions.TaskAlreadyCompletedException;
import teamfour.tasc.model.task.status.EventStatus;
import teamfour.tasc.testutil.TaskBuilder;
import teamfour.tasc.testutil.TestTask;
import teamfour.tasc.testutil.TypicalTestTasks;

public class TaskTest {

    @Test
    public void convertToComplete_uncompletedTask_becomesCompleted()
            throws IllegalValueException, TaskAlreadyCompletedException {
        TestTask uncompletedTask = new TypicalTestTasks().submitPrototype;
        uncompletedTask.setComplete(new Complete(false));

        ReadOnlyTask completedTask = Task.convertToComplete(uncompletedTask);
        assertTrue(completedTask.getComplete().isCompleted());
    }

    @Test(expected = TaskAlreadyCompletedException.class)
    public void convertToComplete_completedTask_throwsException()
            throws TaskAlreadyCompletedException {
        TestTask completedTask = new TypicalTestTasks().submitPrototype;
        completedTask.setComplete(new Complete(true));

        Task.convertToComplete(completedTask);
    }

    @Test
    public void isOverdue_noDeadline_returnsFalse() throws IllegalValueException {
        TestTask noDeadlineTask = new TaskBuilder().withName("No deadline").build();

        assertFalse(noDeadlineTask.isOverdue(DateUtil.getCurrentTime()));
    }

    @Test
    public void isOverdue_deadlineLaterThanCurrentTime_returnsTrue() throws IllegalValueException {
        Date currentTime = new Date(1);
        TestTask lateTask = new TaskBuilder().withDeadline(new Date(2)).build();

        assertTrue(lateTask.isOverdue(currentTime));
    }

    @Test
    public void isOverdue_deadlineEarlierThanCurrentTime_returnsTrue() throws IllegalValueException {
        Date currentTime = new Date(1);
        TestTask notLateTask = new TaskBuilder().withDeadline(new Date(0)).build();

        assertFalse(notLateTask.isOverdue(currentTime));
    }

    @Test
    public void getEventStatus_noPeriod_returnsNotAnEvent() throws IllegalValueException {
        TestTask noPeriod = new TaskBuilder().withName("No period").build();

        assertEquals(EventStatus.NOT_AN_EVENT, noPeriod.getEventStatus(DateUtil.getCurrentTime()));
    }

    @Test
    public void getEventStatus_eventNotStartedYet_returnsNotStarted() throws IllegalValueException {
        Date currentTime = new Date(10);
        TestTask futureEvent = new TaskBuilder().withPeriod(new Date(20), new Date(30)).build();
        
        assertEquals(EventStatus.NOT_STARTED, futureEvent.getEventStatus(currentTime));
    }
    
    @Test
    public void getEventStatus_eventHappeningNow_returnsInProgress() throws IllegalValueException {
        Date currentTime = new Date(10);
        TestTask happeningNow = new TaskBuilder().withPeriod(new Date(5), new Date(15)).build();
        
        assertEquals(EventStatus.IN_PROGRESS, happeningNow.getEventStatus(currentTime));
    }
    
    @Test
    public void getEventStatus_eventIsOver_returnsEnded() throws IllegalValueException {
        Date currentTime = new Date(10);
        TestTask eventEnded = new TaskBuilder().withPeriod(new Date(2), new Date(7)).build();
        
        assertEquals(EventStatus.ENDED, eventEnded.getEventStatus(currentTime));
    }
}