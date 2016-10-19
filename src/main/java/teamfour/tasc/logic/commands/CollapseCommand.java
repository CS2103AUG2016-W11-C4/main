package teamfour.tasc.logic.commands;

import teamfour.tasc.commons.core.EventsCenter;
import teamfour.tasc.commons.core.UnmodifiableObservableList;
import teamfour.tasc.commons.events.ui.CollapseChangeEvent;
import teamfour.tasc.commons.events.ui.JumpToListRequestEvent;
import teamfour.tasc.commons.events.ui.TaskPanelListChangedEvent;
import teamfour.tasc.model.task.ReadOnlyTask;

public class CollapseCommand extends Command {

    public static final String COMMAND_WORD = "collapse";
    public static final String MESSAGE_SUCCESS = "Task view collapsed";
    public static final String MESSAGE_FAILURE = "Already in collapsed view, type \"expand\" to go into expanded view";

    public CollapseCommand(){

    }

    public CommandResult execute(){

        if(CollapseChangeEvent.getCollapsed()){
            return new CommandResult(MESSAGE_FAILURE);
        }
        EventsCenter.getInstance().post(new CollapseChangeEvent(true));
        model.updateFilteredTaskListByFilter(); //refresh the list view
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean canUndo() {
        return false;
    }

    @Override
    public CommandResult executeUndo() {
        return null;
    }
}
