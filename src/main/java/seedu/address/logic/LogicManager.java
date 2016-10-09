package seedu.address.logic;

import javafx.collections.ObservableList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.LogicManager.UndoableTaskNotEnoughException;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.parser.Parser;
import seedu.address.model.Model;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.storage.Storage;

import java.util.Stack;
import java.util.logging.Logger;

/**
 * The main LogicManager of the app.
 */
public class LogicManager extends ComponentManager implements Logic {
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Parser parser;
    
    private static Stack<Command> undoableCommands = new Stack<Command>();

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.parser = new Parser();
    }
    
    public static void executeUndo(int num) throws UndoableTaskNotEnoughException {
        if (undoableCommands.size() < num) {
            throw new UndoableTaskNotEnoughException();
        } else {
            for(int i=0; i<num; i++)
                undoableCommands.pop().executeUndo();
        }
    }
    
    public static int numUndoableCommands() {
        return undoableCommands.size();
    }
    
    public static class UndoableTaskNotEnoughException extends Exception {
        private static final long serialVersionUID = 1L;
    }

    @Override
    public CommandResult execute(String commandText) {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        Command command = parser.parseCommand(commandText);
        command.setData(model);
        CommandResult result = command.execute();
        if (command.canUndo()) {
            undoableCommands.push(command);
        }
        return result;
    }

    @Override
    public ObservableList<ReadOnlyTask> getFilteredTaskList() {
        return model.getFilteredTaskList();
    }
}
