package ui.components;

import engine.components.schedule.Deadline;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class PathfinderDeadlineDialog extends PathfinderDailyDialog {
    private DatePicker datePicker;

    public PathfinderDeadlineDialog(String title) {
        super(title);
        init();
    }

    private void init() {
        datePicker = new DatePicker();
        getGridPane().add(new Label("Set Deadline: "), 0, 3);
        getGridPane().add(datePicker, 1, 3);
    }

    @Override
    protected void setResultConverter() {
        this.setResultConverter(dialogButton -> {
            if (dialogButton == getSubmitButtonType()) {
                return new Deadline(getTitleField().getText(), getDescriptionField().getText(), LocalDateTime.of(datePicker.getValue(), getTimeSpinner().getValue()));
            }
            return null;
        });
    }
}
