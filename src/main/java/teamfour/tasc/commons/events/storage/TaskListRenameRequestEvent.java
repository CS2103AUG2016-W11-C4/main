//@@author A0147971U
package teamfour.tasc.commons.events.storage;

import teamfour.tasc.commons.events.BaseEvent;

/** Indicates the Task List in the model has been renamed */
public class TaskListRenameRequestEvent extends BaseEvent {
    private final String newFilename;
    
    public String getNewFilename() {
        return newFilename;
    }

    public TaskListRenameRequestEvent(String newFilename) {
        this.newFilename = newFilename;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
