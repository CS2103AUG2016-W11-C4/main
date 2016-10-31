//@@author A0147971U
package teamfour.tasc.commons.events.storage;

import teamfour.tasc.commons.events.BaseEvent;

/** Indicates the Task List in the model has been switched to another.*/
public class TaskListSwitchedEvent extends BaseEvent {

    private final String filename;
    
    public String getFilename() {
        return filename;
    }

    public TaskListSwitchedEvent(String filename){
        this.filename = filename;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
