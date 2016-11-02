package teamfour.tasc.ui;

import java.time.Instant;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import teamfour.tasc.model.task.ReadOnlyTask;

public class TaskCard extends UiPart{

    private static final String FXML = "TaskListCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
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
        name.setText(task.getName().getName());
        id.setText(displayedIndex + ". ");
        if (task.getDeadline().hasDeadline()) {
            deadline.setVisible(true);
            deadline.setText("Deadline: " + task.getDeadline().toString());
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
            String[] period = task.getPeriod().toString().split(" - ");
            periodFrom.setText("From : " + period[0]);
            periodTo.setText("      To : " + period[1]);
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
        completeStatus.setText(task.getComplete().toString());
        if (task.getComplete().isCompleted()) {
            completeStatus.setStyle("-fx-text-fill: #008600;");
        } else {
            if (task.getDeadline().hasDeadline()) {
                if (task.getDeadline().getDeadline().getTime() < Instant.now().toEpochMilli()) {
                    completeStatus.setText("Overdue");
                    completeStatus.setStyle("-fx-text-fill: #ff0000;");
                }
            }
            if (task.getPeriod().hasPeriod()) {
                if (task.getPeriod().getEndTime().getTime() < Instant.now().toEpochMilli()) {
                    completeStatus.setText("Overdue");
                    completeStatus.setStyle("-fx-text-fill: #ff0000;");
                }
            }
        }
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
