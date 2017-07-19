package ui.features;

import engine.components.schedule.ToDoListTask;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import resources.StringFormatUtility;
import resources.sqlite.SQLiteJDBC;
import ui.components.editablelabel.ScalingEditableLabel;
import ui.components.PaneKeys;
import ui.components.scalingcomponents.ScalingButton;
import ui.components.scalingcomponents.ScalingLabel;
import ui.components.scalingcomponents.ScalingStackPane;
import ui.components.interviewcommunications.ViewRequestHandler;
import ui.components.scalingcomponents.ViewBindingsPack;
import ui.custombindings.ScaledDoubleBinding;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class HourTile extends ScalingStackPane {
    private HBox mainContainer;
    private HBox hourLabelContainer;
    private HBox hourInfoContainer;
    private ScalingLabel hourLabel;
    private ScalingEditableLabel hourInfo;
    private LocalDateTime localDateTime;
    private LocalTime currentHour;
    private Rectangle border;
    private ViewBindingsPack viewBindings;
     

    public HourTile(ViewRequestHandler commLink, ViewBindingsPack viewBindings, PaneKeys key, LocalDateTime currentDateTime) {
        super(commLink, viewBindings, key);
        this.viewBindings = viewBindings;
        this.localDateTime = currentDateTime;
        hourInfo = new ScalingEditableLabel(viewBindings.widthProperty());
        hourLabel = new ScalingLabel(viewBindings.widthProperty(), "10:00 A.M.");
        this.currentHour = currentDateTime.toLocalTime();
        initStackPane();
        init();
        initHourInfo();
        this.setAlignment(Pos.CENTER);
        this.getChildren().add(mainContainer);
    }

    private void initHourInfo() {
        hourInfo.baseTextProperty().addListener(new ChangeListener<String>() {
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
        });
        if(SQLiteJDBC.getInstance().hasTask(localDateTime)) {
            hourInfo.setBaseText(SQLiteJDBC.getInstance().getTask(localDateTime).getDescription());
        }
    }

    private void initStackPane() {
        border = new Rectangle(0, 0);
        border.setFill(Color.TRANSPARENT);
        border.setStroke(Color.BLACK);
        border.setStrokeWidth(1.0);
        this.getChildren().add(border);
    }

    private void init() {
        mainContainer = new HBox();
        hourLabelContainer = new HBox();
        hourLabelContainer.setAlignment(Pos.CENTER);
        hourInfoContainer = new HBox();
        hourLabel.setText(StringFormatUtility.convertToHour(currentHour));
        hourLabelContainer.getChildren().add(hourLabel);
        hourInfoContainer.getChildren().add(hourInfo);
        mainContainer.getChildren().add(hourLabelContainer);
        mainContainer.getChildren().add(hourInfoContainer);
    }

    public String getTaskDescription() {
        return hourInfo.getText();
    }

    public void setTaskDescription(String text) {
        hourInfo.setText(text);
    }
}
