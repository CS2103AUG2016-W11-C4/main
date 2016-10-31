//@@author A0148096W

package teamfour.tasc.commons.events.ui;

import teamfour.tasc.commons.events.BaseEvent;

/**
 * Represents that the task list has been renamed
 */
public class TaskListRenamedEvent extends BaseEvent {

    private final String newPath;

    public TaskListRenamedEvent(String newPath){
        this.newPath = newPath;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public String getNewPath() {
        return newPath;
    }
}
