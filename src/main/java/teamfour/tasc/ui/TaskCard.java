package teamfour.tasc.ui;

import java.text.SimpleDateFormat;
import java.time.Instant;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import teamfour.tasc.model.task.Complete;
import teamfour.tasc.model.task.ReadOnlyTask;

public class TaskCard extends UiPart{

    private static final String FXML = "TaskListCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label deadline;
    @FXML
    private Label recurrence;
    @FXML
    private Label periodFrom;
    @FXML
    private Label periodTo;
    @FXML
    private Label tags;
    @FXML
    private Label completeStatus;

    private ReadOnlyTask task;
    private int displayedIndex;

    public TaskCard(){

    }

    public static TaskCard load(ReadOnlyTask task, int displayedIndex){
        TaskCard card = new TaskCard();
        card.task = task;
        card.displayedIndex = displayedIndex;
        return UiPartLoader.loadUiPart(card);
    }

    @FXML
    public void initialize() {
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy, HH:mm");
        name.setText(displayedIndex + ". " + task.getName().getName());
        if (task.getDeadline().hasDeadline()) {
            deadline.setVisible(true);
            deadline.setText("Deadline: " + sdf.format(task.getDeadline().getDeadline()));
        } else {
            deadline.setVisible(false);
        }
        if (task.getRecurrence().hasRecurrence()) {
            recurrence.setVisible(true);
            recurrence.setText("Repeat: " + task.getRecurrence().toString());
        } else {
            recurrence.setVisible(false);
        }
        if (task.getPeriod().hasPeriod()) {
            periodFrom.setVisible(true);
            periodTo.setVisible(true);
            periodFrom.setText("From : " + sdf.format(task.getPeriod().getStartTime()));
            periodTo.setText("      To : " + sdf.format(task.getPeriod().getEndTime()));
        } else {
            periodFrom.setVisible(false);
            periodTo.setVisible(false);
        }
        if (task.tagsString().equals("")) {
            tags.setVisible(false);
        } else {
            tags.setVisible(true);
            tags.setText("Tags: " + task.tagsString());
        }
        String completeString = task.getCompleteString();
        completeStatus.setText(completeString);
        completeStatus.setStyle(
                completeString == Complete.TO_STRING_COMPLETED ? "-fx-text-fill: #008600;" : 
                completeString == Complete.TO_STRING_OVERDUE ? "-fx-text-fill: #ff0000;" :
                "-fx-text-fill: #000000;");
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
