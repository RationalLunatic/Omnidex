package ui.features.beaconviews;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import resources.StringFormatUtility;
import resources.sqlite.SQLiteJDBC;
import ui.components.editablelabel.ScalingInputBox;
import ui.components.PaneKeys;
import ui.components.scalingcomponents.ScalingButton;
import ui.components.scalingcomponents.ScalingLabel;
import ui.components.scalingcomponents.ScalingStackPane;
import ui.components.interviewcommunications.ViewRequestHandler;
import ui.components.scalingcomponents.ViewBindingsPack;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class HourTile extends ScalingStackPane {
    private HBox mainContainer;
    private HBox hourLabelContainer;
    private HBox hourInfoContainer;
    private ScalingLabel hourLabel;
    private ScalingInputBox hourInfo;
    private LocalDateTime localDateTime;
    private LocalTime currentHour;
    private Rectangle border;
    private ViewBindingsPack viewBindings;
    private ScalingButton completeTask;
    private ScalingButton removeTask;
    private ChangeListener<String> currentListener;

    public HourTile(ViewRequestHandler commLink, ViewBindingsPack viewBindings, PaneKeys key, LocalDateTime currentDateTime) {
        super(commLink, viewBindings, key);
        this.viewBindings = viewBindings;
        this.localDateTime = currentDateTime;
        this.currentHour = currentDateTime.toLocalTime();
        this.setAlignment(Pos.CENTER);
        init();
    }

    public void updateDateTime(LocalDateTime dateTime) {
        this.localDateTime = dateTime;
        hourInfo.baseTextProperty().removeListener(currentListener);
        initHourInfo();
    }

    private void init() {
        initUIElements();
        initButtonBehavior();
        initBorder();
        initContainers();
        initHourInfo();
        addChildrenToContainers();
    }

    private void initUIElements() {
        this.hourInfo = new ScalingInputBox(viewBindings.widthProperty(), 0.6);
        this.hourLabel = new ScalingLabel(viewBindings.widthProperty(), "10:00 A.M.", 0.15);
        this.completeTask = new ScalingButton("Complete Task", viewBindings, 0.15, 1.0);
        this.removeTask = new ScalingButton("Remove Task", viewBindings, 0.15, 1.0);
    }

    private void initHourInfo() {
        System.out.println(localDateTime);
        currentListener = new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                String name = hourLabel.getText();
                String description = newValue;
                String dateTime = localDateTime.toString();
                if(SQLiteJDBC.getInstance().hasTask(localDateTime)) {
                    SQLiteJDBC.getInstance().deleteTask((localDateTime));
                }
                SQLiteJDBC.getInstance().addTask(name, description, dateTime);
            }
        };

        hourInfo.baseTextProperty().addListener(currentListener);
        if(SQLiteJDBC.getInstance().hasTask(localDateTime)) {
            hourInfo.setBaseText(SQLiteJDBC.getInstance().getTask(localDateTime).getDescription());
        }
    }

    private void initBorder() {
        border = new Rectangle(0, 0);
        border.setFill(Color.TRANSPARENT);
        border.setStroke(Color.BLACK);
        border.setStrokeWidth(1.0);
        this.getChildren().add(border);
    }

    private void initContainers() {
        mainContainer = new HBox();
        hourLabelContainer = new HBox();
        hourInfoContainer = new HBox();
        hourLabel.setText(StringFormatUtility.convertToHour(currentHour));
        hourLabelContainer.setAlignment(Pos.CENTER);
    }

    private void addChildrenToContainers() {
        hourLabelContainer.getChildren().add(hourLabel);
        hourInfoContainer.getChildren().add(hourInfo);
        mainContainer.getChildren().add(hourLabelContainer);
        mainContainer.getChildren().add(hourInfoContainer);
        mainContainer.getChildren().add(completeTask);
        mainContainer.getChildren().add(removeTask);
        this.getChildren().add(mainContainer);
    }

    private void initButtonBehavior() {
        completeTask.setOnMouseClicked(e -> deleteTask());
        removeTask.setOnMouseClicked(e -> deleteTask());
    }

    private void deleteTask() {
        if(SQLiteJDBC.getInstance().hasTask(localDateTime)) {
            SQLiteJDBC.getInstance().deleteTask(localDateTime);
            setTaskDescription("");
        }
    }

    public String getTaskDescription() {
        return hourInfo.getText();
    }

    public void setTaskDescription(String text) {
        hourInfo.setText(text);
    }
}
