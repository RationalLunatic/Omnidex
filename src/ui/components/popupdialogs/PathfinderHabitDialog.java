package ui.components.popupdialogs;

import engine.components.schedule.Habit;
import javafx.scene.control.CheckBox;

public class PathfinderHabitDialog extends PathfinderDialog {
    CheckBox goodHabit;

    public PathfinderHabitDialog(String title) {
        super(title);
        init();
    }

    private void init() {
        goodHabit = new CheckBox("Good Habit");
        goodHabit.setSelected(true);
        getGridPane().add(goodHabit, 0, 2);
    }

    @Override
    protected void setResultConverter() {
        this.setResultConverter(dialogButton -> {
            if (dialogButton == getSubmitButtonType()) {
                return new Habit(getTitleField().getText(), getDescriptionField().getText(), 0, goodHabit.isSelected(), 0);
            }
            return null;
        });
    }
}
