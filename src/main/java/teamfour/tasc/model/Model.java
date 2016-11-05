package teamfour.tasc.model;

import teamfour.tasc.commons.core.UnmodifiableObservableList;
import teamfour.tasc.model.task.ReadOnlyTask;
import teamfour.tasc.model.task.Task;
import teamfour.tasc.model.task.UniqueTaskList;
import teamfour.tasc.model.task.UniqueTaskList.TaskNotFoundException;
import teamfour.tasc.model.task.qualifiers.Qualifier;

/**
 * The API of the Model component.
 */
public interface Model {
    //@@author A0148096W
    public static final String SORT_ORDER_BY_EARLIEST_FIRST = "earliest first";
    public static final String SORT_ORDER_BY_LATEST_FIRST = "latest first";
    public static final String SORT_ORDER_BY_A_TO_Z = "a-z";
    public static final String SORT_ORDER_BY_Z_TO_A = "z-a";
    public static final String SORT_ORDER_DEFAULT = SORT_ORDER_BY_EARLIEST_FIRST;
    public static final String FILTER_TYPE_DEFAULT = "uncompleted";

    //@@author
    /** Clears existing backing model and replaces with the provided new data */
    void resetData(ReadOnlyTaskList newData);

    /** Returns the TaskList */
    ReadOnlyTaskList getTaskList();

    //@@author A0148096W
    /** Saves the current state of the TaskList into history */
    void saveTaskListHistory();
    
    /**
     * Precondition: numToUndo must be > 0.
     * Reverts the task list to the most recent past state
     * @return number of history states undone
     */
    int undoTaskListHistory(int numToUndo);
    
    //@@author
    /** Deletes the given task */
    void deleteTask(ReadOnlyTask target) throws UniqueTaskList.TaskNotFoundException;

    /** Adds the given task */
    void addTask(Task task) throws UniqueTaskList.DuplicateTaskException;

    /** Returns the filtered task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList();

    /** Updates the filter of the filtered task list to show all tasks */
    void updateFilteredTaskToShowAll();

    //@@author A0148096W
    /** Removes all filters of the filtered task list */
    void resetTaskListFilter();
    
    /**
     * Precondition: qualifier is not null.
     * Adds the filter of the filtered task list by the qualifier
     */
    void addTaskListFilter(Qualifier qualifier, boolean negated);

    /** Updates the filtered task list by the added filters */
    void updateFilteredTaskListByFilters();

    /**
     * Precondition: arguments are not null.
     * Sorts the filtered task list by the given sorting order
     */
    void sortFilteredTaskList(String sortOrder);

    //@@author A0140011L
    /**
     * Updates the old task by replacing it with
     * new task details.
     * 
     * @param oldTask to be replaced
     * @param newTask details
     * @throws TaskNotFoundException if the old task
     * cannot be found.
     */
    void updateTask(ReadOnlyTask oldTask, Task newTask) throws TaskNotFoundException;

    //@@author
    /**
     * Precondition: numToRedo must be > 0.
     * Redo the past undos.
     * 
     * @return number of history states redone
     */
    int redoTaskListHistory(int numToRedo);

    /**
     * Clears the redo task list history.
     * Whenever commands excluding undo and redo are executed,
     * this will be run.
     * */
    void clearRedoTaskListHistory();

    boolean tasklistExists(String tasklist);

    void resetTasklistNames(String[] newTasklistNames);
}
