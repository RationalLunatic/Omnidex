package ui.components.popupdialogs;

import javafx.scene.control.CheckBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import ui.components.scalingcomponents.ScalingHBox;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

public class SelectEventTypeDialog extends Dialog {
    private GridPane gridPane;
    private ToggleGroup eventRegularity;
    private HBox eventRegularityContainer;
    private ToggleButton daily;
    private ToggleButton recurring;
    private ToggleButton once;
    private List<CheckBox> weekDays;
    private HBox weekDaysContainer;

    public SelectEventTypeDialog() {
        this.setTitle("Select Event Regularity");
        this.setContentText("Select Event Regularity");
        init();
    }

    private void init() {
        gridPane = new GridPane();
        eventRegularityContainer = new HBox();
        eventRegularity = new ToggleGroup();
        daily = new ToggleButton();
        recurring = new ToggleButton();
        once = new ToggleButton();
        eventRegularityContainer.getChildren().addAll(once, recurring, daily);
        List<CheckBox> weekDays = new ArrayList<>();
        weekDaysContainer = new HBox();
        for(DayOfWeek day : DayOfWeek.values()) {
            CheckBox checkBox = new CheckBox();
            checkBox.setText(day.toString());
            weekDays.add(checkBox);
            weekDaysContainer.getChildren().add(checkBox);
        }
        gridPane.add(eventRegularityContainer, 0, 1);
        gridPane.add(weekDaysContainer, 0, 2);
        this.getDialogPane().setContent(gridPane);
    }


}
