//@@author A0127014W
package teamfour.tasc.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import teamfour.tasc.model.task.Complete;
import teamfour.tasc.model.task.ReadOnlyTask;

/**
 * Like TaskCard, but used for the collapsed view
 * Shorter than the default TaskCard
 * Only shows the name of the task and its index
 */
public class TaskCardCollapsed extends UiPart{

    private static final String FXML = "TaskListCardCollapsed.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;

    private ReadOnlyTask task;
    private int displayedIndex;

    public static TaskCardCollapsed load(ReadOnlyTask task, int displayedIndex){
        TaskCardCollapsed card = new TaskCardCollapsed();
        card.task = task;
        card.displayedIndex = displayedIndex;
        return UiPartLoader.loadUiPart(card);
    }

    @FXML
    public void initialize() {
        name.setText(displayedIndex + ". " + task.getName().getName());
        String completeString = task.getCompleteString();
        cardPane.setStyle(
                completeString == Complete.TO_STRING_COMPLETED ? "-fx-background-color: #448644;" :
                completeString == Complete.TO_STRING_OVERDUE ? "-fx-background-color: #ff8888;" :
                "-fx-background-color: #ffffff;");
    }

    public HBox getLayout() {
        return cardPane;
    }

    @Override
    public void setNode(Node node) {
        cardPane = (HBox)node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }
}

