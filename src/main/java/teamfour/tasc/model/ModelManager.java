package teamfour.tasc.model;

import javafx.collections.transformation.FilteredList;
import teamfour.tasc.commons.core.ComponentManager;
import teamfour.tasc.commons.core.Config;
import teamfour.tasc.commons.core.LogsCenter;
import teamfour.tasc.commons.core.UnmodifiableObservableList;
import teamfour.tasc.commons.events.model.TaskListChangedEvent;
import teamfour.tasc.model.history.HistoryStack;
import teamfour.tasc.model.history.HistoryStack.OutOfHistoryException;
import teamfour.tasc.model.task.ReadOnlyTask;
import teamfour.tasc.model.task.Task;
import teamfour.tasc.model.task.UniqueTaskList;
import teamfour.tasc.model.task.UniqueTaskList.TaskNotFoundException;

import teamfour.tasc.model.task.comparators.AToZComparator;
import teamfour.tasc.model.task.comparators.EarliestFirstComparator;
import teamfour.tasc.model.task.comparators.LatestFirstComparator;
import teamfour.tasc.model.task.comparators.ZToAComparator;
import teamfour.tasc.model.task.qualifiers.AllQualifier;
import teamfour.tasc.model.task.qualifiers.DeadlineQualifier;
import teamfour.tasc.model.task.qualifiers.EndTimeQualifier;
import teamfour.tasc.model.task.qualifiers.NameQualifier;
import teamfour.tasc.model.task.qualifiers.Qualifier;
import teamfour.tasc.model.task.qualifiers.StartTimeQualifier;
import teamfour.tasc.model.task.qualifiers.StartToEndTimeQualifier;
import teamfour.tasc.model.task.qualifiers.TagQualifier;
import teamfour.tasc.model.task.qualifiers.TypeQualifier;

import java.util.Date;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Represents the in-memory model of the task list data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final TaskList taskList;
    private final FilteredList<Task> filteredTasks;
    private PredicateExpression taskListFilter;
    private HistoryStack<TaskList> undoTaskListHistory;
    private HistoryStack<TaskList> redoTaskListHistory;
    private String[] tasklistNames;

    /**
     * Initializes a ModelManager with the given ReadOnlyTaskList
     * ReadOnlyTaskList and its variables should not be null
     */
    public ModelManager(ReadOnlyTaskList initialData, UserPrefs userPrefs, Config config) {
        super();
        assert initialData != null;
        assert userPrefs != null;

        logger.fine("Initializing with task list: " + initialData + " and user prefs " + userPrefs);

        taskList = new TaskList(initialData);
        filteredTasks = new FilteredList<>(taskList.getTasks());
        taskListFilter = new PredicateExpression(new AllQualifier());
        undoTaskListHistory = new HistoryStack<TaskList>();
        redoTaskListHistory = new HistoryStack<TaskList>();
        tasklistNames = config.getTaskListNames();
        setupDefaultFiltersAndSortOrder();
    }

    public ModelManager() {
        this(new TaskList(), new UserPrefs(), new Config());
    }

    private void setupDefaultFiltersAndSortOrder() {
        resetTaskListFilter();
        addTaskListFilterByType(Model.FILTER_TYPE_DEFAULT, false);
        updateFilteredTaskListByFilter();
        sortFilteredTaskListByOrder(Model.SORT_ORDER_DEFAULT);
    }
    
    @Override
    public void resetTasklistNames(String[] newTasklistNames) {
        this.tasklistNames = newTasklistNames;
    }
    
    @Override
    public boolean tasklistExists(String tasklist) {
        for (String name : this.tasklistNames) {
            if (name.equals(tasklist)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void resetData(ReadOnlyTaskList newData) {
        taskList.resetData(newData);
        indicateTaskListChanged();
    }

    @Override
    public ReadOnlyTaskList getTaskList() {
        return taskList;
    }

    /**
     * Raises an event to indicate the model has changed.
     * Also saves a history state of the task list.
     */
    private void indicateTaskListChanged() {
        raise(new TaskListChangedEvent(taskList));
    }

    //@@author A0148096W
    @Override
    public void saveTaskListHistory() {
        undoTaskListHistory.push(taskList);
    }

    @Override
    public int undoTaskListHistory(int numToUndo) {
        assert numToUndo > 0;

        int numUndone = 0;
        TaskList historyTaskList = null;
        try {
            for (int i = 0; i < numToUndo; i++) {
                TaskList redoTaskList = historyTaskList;
                if (redoTaskList == null) {
                    redoTaskList = taskList;
                }
                
                historyTaskList = undoTaskListHistory.pop();
                redoTaskListHistory.push(redoTaskList);
                numUndone++;
            }
        } catch (OutOfHistoryException e) {
            logger.fine(e.getMessage());
        }

        if (historyTaskList != null) {
            resetData(historyTaskList);
        }
        return numUndone;
    }

    //@@author A0147971U
    @Override
    public int redoTaskListHistory(int numToRedo) {
        assert numToRedo > 0;

        int numRedone = 0;
        TaskList historyTaskList = null;
        try {
            for (int i = 0; i < numToRedo; i++) {
                TaskList undoTaskList = historyTaskList;
                if (undoTaskList == null) {
                    undoTaskList = taskList;
                }
                
                historyTaskList = redoTaskListHistory.pop();
                undoTaskListHistory.push(undoTaskList);
                numRedone++;
            }
        } catch (OutOfHistoryException e) {
            logger.fine(e.getMessage());
        }

        if (historyTaskList != null) {
            resetData(historyTaskList);
        }
        return numRedone;
    }

    @Override
    public void clearRedoTaskListHistory() {
        redoTaskListHistory = new HistoryStack<TaskList>();
    }
    //@@author


    @Override
    public synchronized void deleteTask(ReadOnlyTask target) throws TaskNotFoundException {
        taskList.removeTask(target);
        indicateTaskListChanged();
    }

    @Override
    public synchronized void addTask(Task task) throws UniqueTaskList.DuplicateTaskException {
        taskList.addTask(task);
        indicateTaskListChanged();
    }

    //@@author A0140011L
    @Override
    public synchronized void updateTask(ReadOnlyTask oldTask, Task newTask) throws TaskNotFoundException {
        taskList.updateTask(oldTask, newTask);
        indicateTaskListChanged();
    }

    //@@author

    //=========== Filtered Task List Accessors ===============================================================

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList() {
        return new UnmodifiableObservableList<>(filteredTasks);
    }

    @Override
    public void updateFilteredTaskToShowAll() {
        filteredTasks.setPredicate(null);
    }

    @Override
    public void updateFilteredTaskList(Set<String> keywords){
        updateFilteredTaskList(new PredicateExpression(new NameQualifier(keywords)));
    }

    //@@author A0148096W
    @Override
    public void resetTaskListFilter() {
        taskListFilter = new PredicateExpression(new AllQualifier());
    }

    @Override
    public void addTaskListFilterByType(String type, boolean negated) {
        assert type != null;
        taskListFilter.addNext(new PredicateExpression(new TypeQualifier(type), negated));
    }

    @Override
    public void addTaskListFilterByDeadline(Date deadline, boolean negated) {
        assert deadline != null;
        taskListFilter.addNext(new PredicateExpression(new DeadlineQualifier(deadline), negated));
    }

    @Override
    public void addTaskListFilterByStartTime(Date startTime, boolean negated) {
        assert startTime != null;
        taskListFilter.addNext(new PredicateExpression(new StartTimeQualifier(startTime), negated));
    }

    @Override
    public void addTaskListFilterByEndTime(Date endTime, boolean negated) {
        assert endTime != null;
        taskListFilter.addNext(new PredicateExpression(new EndTimeQualifier(endTime), negated));
    }

    @Override
    public void addTaskListFilterByStartToEndTime(Date startTime, Date endTime, boolean negated) {
        assert startTime != null;
        assert endTime != null;
        taskListFilter.addNext(new PredicateExpression(new StartToEndTimeQualifier(startTime, endTime), negated));
    }

    @Override
    public void addTaskListFilterByTags(Set<String> tags, boolean negated) {
        assert tags != null;
        taskListFilter.addNext(new PredicateExpression(new TagQualifier(tags), negated));
    }

    @Override
    public void updateFilteredTaskListByFilter() {
        updateFilteredTaskList(taskListFilter);
    }
    
    @Override
    public void sortFilteredTaskListByOrder(String sortOrder) {
        assert sortOrder != null;
        switch(sortOrder) {
        case Model.SORT_ORDER_BY_EARLIEST_FIRST:
            taskList.sortUsingComparator(new EarliestFirstComparator());
            break;
        case Model.SORT_ORDER_BY_LATEST_FIRST:
            taskList.sortUsingComparator(new LatestFirstComparator());
            break;
        case Model.SORT_ORDER_BY_A_TO_Z:
            taskList.sortUsingComparator(new AToZComparator());
            break;
        case Model.SORT_ORDER_BY_Z_TO_A:
            taskList.sortUsingComparator(new ZToAComparator());
            break;
        default:
            logger.warning("Unable to sort task list due to "
                    + "unrecognized sort order string: " + sortOrder);
            break;
        }
    }

    //@@author
    private void updateFilteredTaskList(Expression expression) {
        filteredTasks.setPredicate(expression::satisfies);
    }

    //@@author A0148096W
    //========== Inner classes/interfaces used for filtering ==================================================

    interface Expression {
        boolean satisfies(ReadOnlyTask task);
        String toString();
    }

    private class PredicateExpression implements Expression {

        private final Qualifier qualifier;
        private PredicateExpression next;
        private boolean isNegated;

        PredicateExpression(Qualifier qualifier) {
            this(qualifier, false);
        }

        PredicateExpression(Qualifier qualifier, boolean negated) {
            this.qualifier = qualifier;
            this.next = null;
            this.isNegated = negated;
        }

        /**
         * Chains the predicate using logical AND of this predicate and another.
         * @param other The other predicate
         */
        public void addNext(PredicateExpression other) {
            PredicateExpression tail = this;
            while (tail.next != null) {
                tail = tail.next;
            }
            tail.next = other;
        }

        @Override
        /**
         * Runs all the chained predicates using logical AND.
         * @param task Task to check
         * @return true if all predicates are satisfied
         */
        public boolean satisfies(ReadOnlyTask task) {
            PredicateExpression it = this;
            while (it != null) {
                if (it.qualifier.run(task) == it.isNegated) {
                    return false;
                }
                it = it.next;
            }
            return true;
        }

        @Override
        public String toString() {
            return qualifier.toString();
        }
    }
}
